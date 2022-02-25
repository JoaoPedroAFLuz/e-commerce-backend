package com.joaopedroluz.cursomc.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class EmailDTO {

    @NotEmpty(message = "Preenchimento Obrigatório")
    @Length(message = "Email inválido")
    private String email;

    public EmailDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
