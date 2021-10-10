package com.shop.repository;

import com.shop.entity.ValueCharacteristic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueCharacteristicRepository extends JpaRepository<ValueCharacteristic, Long> {
}