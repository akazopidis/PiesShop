package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.forms.form.FormLogin;
import com.kazopidis.piesshop.models.model.User;
import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.Set;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);

        if (user != null) {
            // Store user's session
            // We want to know where the user came from
            request.getSession().setAttribute("previouspage", request.getParameter("previouspage"));

            request.setAttribute("errors", "You are already logged in to the application.");

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/index.jsp")
                    .forward(request, response);
        }
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        FormLogin formLogin = new FormLogin(username,password);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormLogin>> errors = validator.validate(formLogin);

        if (errors.isEmpty()) { //No errors

            // response
            request.setAttribute("success",true);
            HttpSession session = request.getSession();

            UserDAO.storeSession(username,session.getId());

            User user = UserDAO.getUserByUsername(username);
            session.setAttribute("user", user);

            String previousPage = (String) request.getSession().getAttribute("previouspage");

            if (previousPage == null) {
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                        .forward(request, response);
            } else {
                System.out.println(previousPage);

                response.sendRedirect("/"+ previousPage);
            }


        } else { // Errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }

            errorMessage.append("</ul>");

            request.setAttribute("errors",errorMessage);
            request.setAttribute("formLogin",formLogin);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                    .forward(request, response);
        }
    }
}
