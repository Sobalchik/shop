package com.market.shop.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema
@Validated
public class Users {

    @NotNull
    @Schema(description = "Name", required = true)
    @JsonProperty("user_list")
    private List<User> userList;

}
