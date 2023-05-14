package com.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Setter
@Getter
@Table
@ToString
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String phonenumber;

    private String description;

    private String picture;

    private String url;

    @Max(5)
    @Min(1)
    private Double rating;

    private Long firm_type_id;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "firm")
    @JsonIgnore
    private Set<ValueFirmFeature> valueFirmFeature;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "firm")
    @JsonIgnore
    private Set<FirmPicture> firmPictures;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "firm")
    @JsonIgnore
    private Set<FirmComment> firmComment;

}