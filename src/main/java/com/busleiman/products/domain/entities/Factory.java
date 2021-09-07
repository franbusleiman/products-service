package com.busleiman.products.domain.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Table(name = "factories")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phones_numbers", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "factory",
    cascade = CascadeType.ALL)
    @Column(name = "products_list")
    private List<Product> productList;
}
