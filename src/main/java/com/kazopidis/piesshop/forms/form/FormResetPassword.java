package com.kazopidis.piesshop.forms.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;

public class FormResetPassword {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Invalid password (8-20 characters with at least one uppercase letter, one lowercase letter, one digit, and one special character: @$!%*?&)")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Invalid password (8-20 characters with at least one uppercase letter, one lowercase letter, one digit, and one special character: @$!%*?&)")
    private String password2;
    @AssertTrue(message = "The passwords you gave are not the same")
    private Boolean passwordsMatch;

    public FormResetPassword() {
    }

    public FormResetPassword(String password, String password2) {
        this.password = password;
        this.password2 = password2;
        this.passwordsMatch = password.equals(password2);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
