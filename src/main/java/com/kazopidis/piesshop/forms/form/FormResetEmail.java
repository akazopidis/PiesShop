package com.kazopidis.piesshop.forms.form;

import com.kazopidis.piesshop.forms.validators.EmailExistsConstraint;
import com.kazopidis.piesshop.forms.validators.EmailNotExistsConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FormResetEmail {
    @NotNull(message = "The e-mail must not be null")
    @NotEmpty(message = "The e-mail must not be blank")
    @Email(message = "The e-mail is not valid")
    @EmailExistsConstraint(message = "The email you provided is not being used by a user!")
    private String email;

    public FormResetEmail() {
    }

    public FormResetEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
