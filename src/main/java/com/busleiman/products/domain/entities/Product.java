package com.busleiman.products.domain.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@EqualsAndHashCode
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            optional = false)
    @JoinColumn(name = "factory_id",
            nullable = false)
    private Factory factory;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "section_id",
            nullable = false)
    private Section section;

}
