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

  @Query(value = "select distinct p from Firm p where lower(p.name) like lower(CONCAT('%', :name, '%')) order by p.price")
  List<Firm> findByName(@Param("name") String name);


  @Query(value = "select distinct p from Firm p " +
          "inner join p.firmType pt where lower(pt.nameCategory) = lower(:category) order by p.price")
  List<Firm> findFirmsByCategory(@Param("category") String category, Pageable page);

  @Query(value = "select distinct p from Firm p " +
          " where p.priceWithoutDiscount is not NULL")
  List<Firm> findFirmsByDiscount(Pageable page);


  @Query(value = "select p from Firm p " +
          "left join fetch p.valueFirmFeature vpf " +
          "left join fetch vpf.firmFeature " +
          "left join fetch p.firmType " +
          "left join fetch p.firmPictures " +
          "left join fetch p.firmComment " +
          "where  p.id = :id")
  Optional<Firm> findFirmById(@Param("id") Long id);

}
