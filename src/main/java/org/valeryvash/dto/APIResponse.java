package org.valeryvash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class APIResponse {
    private int code;
    private String message;
}
