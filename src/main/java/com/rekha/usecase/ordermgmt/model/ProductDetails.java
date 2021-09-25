package com.rekha.usecase.ordermgmt.model;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    @Getter
    @Setter
    @NotBlank
    private String productId;

    @Getter
    @Setter
    @NotBlank
    private String category;

    @Getter
    @Setter
    @NotNull
    private Double price;
}
