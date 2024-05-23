package com.kazopidis.piesshop.forms.form;

import com.kazopidis.piesshop.forms.validators.EmailNotExistsConstraint;
import com.kazopidis.piesshop.forms.validators.UsernameNotExistsConstraint;
import jakarta.validation.constraints.*;

public class FormRegister {
    @NotNull(message = "The full name must not be null")
    @NotEmpty(message = "The full name must not be empty")
    private String fullName;

    @NotNull (message = "The e-mail must not be null")
    @NotEmpty (message = "The e-mail must not be blank")
    @Email(message = "The e-mail is not valid")
    @EmailNotExistsConstraint(message = "The e-mail you provided is being used by another user. Choose a new one!")
    private String email;

    @Pattern(regexp = "^[26][0-9]{9}$|^$", message = "Invalid phone number")
    private String tel;

    @Pattern(regexp = "[0-9a-zA-Z]{5,20}$", message = "Invalid username (5-20 Latin characters and/or numbers)")
    @UsernameNotExistsConstraint(message = "The username already exists. Choose a new one!")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Invalid password (8-20 characters with at least one uppercase letter, one lowercase letter, one digit, and one special character: @$!%*?&)")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Invalid password (8-20 characters with at least one uppercase letter, one lowercase letter, one digit, and one special character: @$!%*?&)")
    private String password2;

    //The passwordsMatch field is set to true if the password and confirmation password are equal.
    @AssertTrue(message = "The passwords you gave are not the same")
    private Boolean passwordsMatch;

    public FormRegister() {
    }

    public FormRegister(String fullName, String email, String tel, String username, String password, String password2) {
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.passwordsMatch = password.equals(password2);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getPasswordsMatch() {
        return passwordsMatch;
    }

    public void setPasswordsMatch(Boolean passwordsMatch) {
        this.passwordsMatch = passwordsMatch;
    }

    @Override
    public String toString() {
        return "FormRegister{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", passwordsMatch=" + passwordsMatch +
                '}';
    }
}
