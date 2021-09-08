package com.busleiman.products.domain.dtos;


import com.busleiman.products.domain.dtos.validationsGroups.Action;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductDTO {

    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    @Size(min = 2, message = "The name must have almost 2 chars", groups = {Action.Create.class, Action.Update.class})
    private String name;

    @NotNull(message = "The field must not be null", groups = {Action.Create.class})
    @Min(value = 1, message = "The price should not be less than 1", groups = {Action.Create.class, Action.Update.class})
    private Double price;

    @NotNull(message = "The field must not be null", groups = {Action.Create.class})
    private Long factoryId;

    @NotNull(message = "The field must not be null", groups = {Action.Create.class})
    private Long sectionId;
}
