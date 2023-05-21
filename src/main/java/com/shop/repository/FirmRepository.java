package com.shop.repository;

import com.shop.entity.Firm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FirmRepository extends JpaRepository<Firm, Long> {

    @Query(value = "select distinct p from Firm p where lower(p.name) like lower(CONCAT('%', :name, '%'))")
    List<Firm> findByName(@Param("name") String name);


    Firm getById(Long id);

}
