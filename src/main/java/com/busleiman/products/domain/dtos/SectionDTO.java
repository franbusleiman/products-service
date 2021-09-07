package com.busleiman.products.domain.dtos;


import com.busleiman.products.domain.Edibility;
import com.busleiman.products.domain.dtos.validationsGroups.Action;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.EnumMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionDTO {

    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    private String name;

    @NotNull(message = "The field must not be null", groups = {Action.Create.class})
    private Edibility edibility;
}
