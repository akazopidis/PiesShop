package com.kazopidis.piesshop.forms.form;

import jakarta.validation.constraints.*;

public class FormContact {
    @NotNull (message = "The full name must not be null")
    @NotEmpty (message = "The full name must not be blank")
    private String fullName;

    @NotNull (message = "The e-mail must not be null")
    @NotEmpty (message = "The e-mail must not be blank")
    @Email (message = "The e-mail is not valid")
    private String email;

    @Pattern(regexp = "^[26][0-9]{9}$|^$", message = "Invalid phone number")
    private String tel;

    @Pattern(regexp = ".{5,}", message = "The message must be at least 5 characters long")
    @Pattern(regexp = ".{0,100}", message = "The message must be a maximum of 100 characters")
    private String message;

    public FormContact() {
    }

    public FormContact(String fullname, String email, String tel, String message) {
        this.fullName = fullname;
        this.email = email;
        this.tel = tel;
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FormContact{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
