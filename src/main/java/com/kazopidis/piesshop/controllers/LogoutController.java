package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.models.model.User;
import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);

        if (user != null) {
            if (UserDAO.logout(request.getSession().getId())) {
                request.getSession().invalidate();
                request.setAttribute("success", true);
            } else {
                request.setAttribute("errors", "No connection has been established!");
            }
        } else {
            request.setAttribute("errors", "You have to login first in order to logout.");

            getServletContext().getRequestDispatcher("/WEB-INF/templates/login.jsp")
                    .forward(request, response);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/templates/logout.jsp")
                .forward(request, response);
    }
}
