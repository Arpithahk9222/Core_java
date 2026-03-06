package com.hm.UserMicroservice.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchDTO {
 

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer age;
}
