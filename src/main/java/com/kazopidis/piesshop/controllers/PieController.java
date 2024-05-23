package com.kazopidis.piesshop.controllers;

import com.kazopidis.piesshop.models.model.Pie;
import com.kazopidis.piesshop.models.dao.PieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pie")
public class PieController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // read parameters
        int id = Integer.parseInt(request.getParameter("id"));

        // prepare bean
        Pie pieBean = PieDAO.getPieById(id);
        request.setAttribute("bean",pieBean);

        // dispatch to jsp
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/pie.jsp")
                .forward(request, response);

        }

}