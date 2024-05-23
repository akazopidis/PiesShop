package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.email.Email;
import com.kazopidis.piesshop.forms.form.FormResetEmail;
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
import java.util.Random;
import java.util.Set;

@WebServlet("/password-reset")
public class PasswordResetEmailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);

        if (user != null) {
            request.setAttribute("errors", "You cant change your password through email while you are logged in.");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/index.jsp")
                    .forward(request, response);
        }

        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/password-reset.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        FormResetEmail formResetEmail = new FormResetEmail(email);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormResetEmail>> errors = validator.validate(formResetEmail);

        if (errors.isEmpty()) { //No errors

            Random r = new Random();

            int code = r.nextInt(100000);
            Email.sendEmailPasswordReset(email, String.valueOf(code));
            UserDAO.updateUserResetPassword(email, code);

            // Set an attribute in the session to indicate that password reset is initiated
            request.getSession().setAttribute("passwordResetInitiated", true);

            // response
            request.setAttribute("success",true);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/password-reset.jsp")
                    .forward(request, response);

        } else { // Errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }

            errorMessage.append("</ul>");

            request.setAttribute("errors",errorMessage);
            request.setAttribute("formResetEmail",formResetEmail);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/password-reset.jsp")
                    .forward(request, response);
        }

    }
}
