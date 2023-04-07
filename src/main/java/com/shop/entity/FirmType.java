package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Тип продукта
 */
@Entity
@Table
@Getter
@Setter
public class FirmType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nameCategory;

    private String picture;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "firmType")
    private Set<Firm> firms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "firmType")
    @JsonIgnore
    private Set<FirmFeature> firmFeatures;


}
