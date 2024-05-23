package com.kazopidis.piesshop.forms.form;

import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.validation.constraints.AssertTrue;

public class FormLogin {

    private String username;
    private String password;

    @AssertTrue(message = "The information you provided is not valid!")
    private Boolean validUser;

    public FormLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.validUser = UserDAO.login(username,password) != null;
        /*
        The validUser field is set to true if the UserDAO.login(username, password)
        method returns a non-null value, indicating that the login is successful.
        If the login fails (returns null), the validUser field is set to false.
        */
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
}

