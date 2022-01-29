package com.market.shop.rest.dto.cake;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Data
@Schema(description = "Short info about cakes" )
@Validated
public class Cake {

    @Null
    @Schema(description = "ID")
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(description = "Name", required = true)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Schema(description = "Calories", required = true)
    @JsonProperty("calories")
    private BigDecimal calories;

    @NotNull
    @Schema(description = "Image", required = true)
    @JsonProperty("image")
    private String image;

    @NotNull
    @Schema(description = "Price", required = true)
    @JsonProperty("price")
    private Integer price;

    @NotNull
    @Schema(description = "Weight", required = true)
    @JsonProperty("weight")
    private BigDecimal weight;

    Calendar c;
    Date d;

}
