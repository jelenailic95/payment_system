package com.sep.scientificcentre.scientificcentre.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JournalsDto {

    private List<JournalDto> journals;

}
