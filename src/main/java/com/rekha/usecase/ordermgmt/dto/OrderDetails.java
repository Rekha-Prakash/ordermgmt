package com.rekha.usecase.ordermgmt.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rekha.usecase.ordermgmt.model.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orderDetails")
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)

@CompoundIndexes({
        @CompoundIndex(
                name = "order_index",
                def = "{'orderId' : 1, 'customerName': 1}")
})

public class OrderDetails {

    @NotBlank
    @Indexed
    @Field("customerName")
    @JsonProperty("customerName")
    String customerName;

    @NotBlank
    @Field("address")
    @JsonProperty("address")
    String address;

    @NotBlank
    @Field("contactNo")
    @JsonProperty("contactNo")
    String contactNo;

    @NotBlank
    @Field("orderStatus")
    @JsonProperty("orderStatus")
    String orderStatus;


    @NotEmpty
    @Field("productDetails")
    @JsonProperty("productDetails")
    List<ProductDetails> productDetails;

    @Field("totalPrice")
    @JsonProperty("totalPrice")
    Double totalPrice;

    @Id
    @Indexed
    private String orderId;

    @Field("createdTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date createdTimestamp;

    @Field("updatedTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date updatedTimestamp;
}
