package com.sep.paypal.model.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubscribeDto implements Serializable {

    private String nameOfJournal;

}
