package com.shop;

import com.shop.entity.AutUser;
import com.shop.entity.MyUserDetails;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Getter
    private String userName1;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);


        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        userName1 = userName;
        return user.map(MyUserDetails::new).get();
    }
}
