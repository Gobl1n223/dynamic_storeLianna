package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Конкретная характеристика
 */
@Getter
@Setter
@Entity
@Table
public class ValueFirmFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Firm firm;

    @ManyToOne(fetch = FetchType.EAGER)
    private FirmFeature firmFeature;
}
