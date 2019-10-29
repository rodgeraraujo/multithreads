package com.rogerio.multithreads.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String fabricante;
    @NotNull
    @Column(nullable = false)
    private String modelo;
    @NotNull
    @Column(nullable = false)
    private String tipo;

}
