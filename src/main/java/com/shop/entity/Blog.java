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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Setter
@Getter
@Table
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String author;

    private String date;

    private String header;

    private String subtitle;

    private String picture;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "blog")
    @JsonIgnore
    private Set<BlogComment> blogComment;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "blog")
    @JsonIgnore
    private Set<BlogDetails> blogDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private BlogCategory blogCategory;

}
