package com.sep.scientificcentre.scientificcentre.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JournalDto {

    private Long id;
    private String issnNumber;
    private String name;
    private boolean openAccess;
    private double price;
    private int period;

}
