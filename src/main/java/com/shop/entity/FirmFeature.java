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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


/**
 * Характеристика продукта
 */
@Entity
@Setter
@Getter
@Table
public class FirmFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "firmFeature")
    @JsonIgnore
    private Set<ValueFirmFeature> valueFirmFeature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private FirmType firmType;


}
