package com.busleiman.products.domain.dtos.responses;

import com.busleiman.products.domain.dtos.ProductDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductResponse extends ProductDTO {

    private Long id;
}
