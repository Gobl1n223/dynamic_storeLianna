package com.shop.repository;

import com.shop.entity.FirmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<FirmType, Long> {

}
