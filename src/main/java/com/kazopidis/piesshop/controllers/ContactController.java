package com.kazopidis.piesshop.controllers;

import java.io.*;
import java.util.Set;

import com.kazopidis.piesshop.forms.form.FormContact;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import com.kazopidis.piesshop.email.Email;

@WebServlet("/contact")
public class ContactController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/contact.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String telephone = request.getParameter("tel");
        String description = request.getParameter("message");

        FormContact formData = new FormContact(
                fullName,email,telephone,description
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormContact>> errors = validator.validate(formData);


        if (errors.isEmpty()) { //no errors
            // response
            Email.sendEmailToAdminContactForm(formData);
            Email.sendEmailToClientContactForm(formData);

            request.setAttribute("success", true);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/contact.jsp")
                    .forward(request, response);
        }
        else { //errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }
            errorMessage.append("</ul>");

            request.setAttribute("errors", errorMessage);
            request.setAttribute("formData", formData);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/contact.jsp")
                    .forward(request, response);
        }

    }

}











































