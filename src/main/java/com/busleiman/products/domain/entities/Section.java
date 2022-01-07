package com.busleiman.products.domain.entities;

import com.busleiman.products.domain.enums.Edibility;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sections")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "edibility")
    private Edibility edibility;

    @OneToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.REMOVE,
    mappedBy = "section")
    private List<Product> productList;
}
