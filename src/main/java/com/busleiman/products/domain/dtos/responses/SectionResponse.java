package com.busleiman.products.domain.dtos.responses;

import com.busleiman.products.domain.dtos.SectionDTO;
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
public class SectionResponse extends SectionDTO {

    private Long id;
}
