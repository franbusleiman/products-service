package com.busleiman.products.domain.dtos;

import com.busleiman.products.domain.dtos.validationsGroups.Action;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FactoryDTO {

    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    @Size(min = 2, message = "The name must have almost 2 chars", groups = {Action.Create.class, Action.Update.class})
    private String name;


    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    @Size(min = 2, message = "Must have between 6 and 12 characters long", groups = {Action.Create.class, Action.Update.class})
    private String phoneNumber;

    @Email
    private String email;

    @NotBlank(message = "The field must not be null", groups = {Action.Create.class})
    @Size(min = 2, message = "The name must have almost 2 chars", groups = {Action.Create.class, Action.Update.class})
    private String address;
}
