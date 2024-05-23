package com.kazopidis.piesshop.controllers;

import java.io.*;
import java.util.List;

import com.kazopidis.piesshop.models.model.Pie;
import com.kazopidis.piesshop.models.dao.PieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/pies")
public class PiesController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Pie> pies = PieDAO.getPies();
        request.setAttribute("pies",pies);

        // dispatch to jsp
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/pies.jsp")
                .forward(request, response);

        }

    }