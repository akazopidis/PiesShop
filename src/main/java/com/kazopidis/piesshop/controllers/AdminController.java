package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // demand an admin
        String sessionId = request.getSession().getId();

        System.out.println(sessionId);

        String role = UserDAO.getRole(sessionId);

        System.out.println(role);

        if (role == null) {
            request.setAttribute("errors","You must login to access this resource!");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                    .forward(request,response);
        } else if (!role.equals("admin")) {
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/error/unauthorized.jsp")
                    .forward(request,response);
        } else {
            String action = request.getParameter("action");
            if (action!=null) {
                if (request.getParameter("action").equals("1")) {
                    UserDAO.deleteUnverifiedUsers();
                } else if (request.getParameter("action").equals("2")) {
                    UserDAO.updateUnverifiedUsersCode();
                }
            }

            List<Integer> adminStats = UserDAO.adminStats();
            request.setAttribute("adminStats",adminStats);
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/admin.jsp")
                    .forward(request,response);
        }
    }
}
