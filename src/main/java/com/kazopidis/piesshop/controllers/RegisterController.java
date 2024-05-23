package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.email.Email;
import com.kazopidis.piesshop.forms.form.FormRegister;
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

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);

        if (user != null) {
            // If the user is already logged in, redirect to a different page
            request.setAttribute("errors", "You are logged in so you cannot register for now.");
            getServletContext().getRequestDispatcher("/WEB-INF/templates/index.jsp")
                    .forward(request, response);
        } else {
            String code = request.getParameter("code");

            if (code != null) {
                boolean success = UserDAO.verifyUser(code);
                request.setAttribute("registerComplete", success);
                getServletContext().getRequestDispatcher("/WEB-INF/templates/register.jsp")
                        .forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/WEB-INF/templates/register.jsp")
                        .forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        FormRegister formRegister = new FormRegister(fullName,email,tel,username,password,password2);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormRegister>> errors = validator.validate(formRegister);

        if (errors.isEmpty()) { //No errors

            int id = UserDAO.storeUserUnverified(formRegister);
            User user = UserDAO.getUserById(id);
            Email.sendEmailCompleteRegister(user);

            System.out.println(formRegister);

            // response
            request.setAttribute("success",true);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/register.jsp")
                    .forward(request, response);
        } else { // Errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }

            errorMessage.append("</ul>");

            request.setAttribute("errors",errorMessage);
            request.setAttribute("formRegister",formRegister);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/register.jsp")
                    .forward(request, response);
        }

    }
}
