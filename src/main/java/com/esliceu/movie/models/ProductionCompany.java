package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class ProductionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(length = 500)
    private String companyName;
}
