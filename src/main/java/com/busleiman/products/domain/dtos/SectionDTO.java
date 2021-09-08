package com.busleiman.products.domain.dtos;


import com.busleiman.products.domain.enums.Edibility;
import com.busleiman.products.domain.dtos.validationsGroups.Action;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SectionDTO {

    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    private String name;

    @NotNull(message = "The field must not be null", groups = {Action.Create.class})
    private Edibility edibility;
}
