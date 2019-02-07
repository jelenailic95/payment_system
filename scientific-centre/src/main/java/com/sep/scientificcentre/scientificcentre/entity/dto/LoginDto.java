package com.sep.scientificcentre.scientificcentre.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String username;
    private String password;
    private String scName;
}
