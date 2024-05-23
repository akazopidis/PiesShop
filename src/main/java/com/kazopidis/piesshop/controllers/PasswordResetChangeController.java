package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.forms.form.FormResetPassword;
import com.kazopidis.piesshop.models.model.User;
import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.Set;

@WebServlet("/change-password")
public class PasswordResetChangeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        request.setAttribute("code",code);

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);


        // Check if the password reset is initiated
        if (request.getSession().getAttribute("passwordResetInitiated") == null) {
            request.setAttribute("errors", "Password reset not initiated. Please go through the password reset process first.");
            getServletContext().getRequestDispatcher("/WEB-INF/templates/password-reset.jsp").forward(request, response);
            return;
        }

        if (user != null) {
            request.setAttribute("errors", "You cant reset your password while you are logged in.");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/index.jsp")
                    .forward(request, response);
        }

        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/password-change.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String code = request.getParameter("code");

        FormResetPassword formResetPassword = new FormResetPassword(password,password2);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormResetPassword>> errors = validator.validate(formResetPassword);

        if (errors.isEmpty()) { //No errors

            if (code != null && !code.isEmpty()) {
                UserDAO.updateUserNewPassword(password, Integer.parseInt(code));
            } else {
                request.setAttribute("errors", "Invalid or missing verification code.");
                getServletContext().getRequestDispatcher("/WEB-INF/templates/password-change.jsp").forward(request, response);
            }

            // response
            request.setAttribute("success",true);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/password-change.jsp")
                    .forward(request, response);

        } else { // Errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }

            errorMessage.append("</ul>");

            request.setAttribute("errors",errorMessage);
            request.setAttribute("formResetPassword",formResetPassword);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/password-change.jsp")
                    .forward(request, response);
        }


    }
}
