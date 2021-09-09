package com.busleiman.products.domain.dtos.responses;

import com.busleiman.products.domain.dtos.FactoryDTO;
import com.busleiman.products.domain.entities.Factory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FactoryResponse extends FactoryDTO {

    private Long id;
}
