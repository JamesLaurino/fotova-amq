package com.fotova.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDtoAmq {
    private String nom;
    private String email;
    private String sujet;
    private String message;
}
