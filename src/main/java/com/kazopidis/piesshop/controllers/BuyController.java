package com.kazopidis.piesshop.controllers;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kazopidis.piesshop.email.Email;
import com.kazopidis.piesshop.forms.form.FormOrder;
import com.kazopidis.piesshop.models.dao.AreaDAO;
import com.kazopidis.piesshop.models.dao.OrderDAO;
import com.kazopidis.piesshop.models.dao.PieDAO;
import com.kazopidis.piesshop.models.dao.UserDAO;
import com.kazopidis.piesshop.models.model.Area;
import com.kazopidis.piesshop.models.model.OrderItem;
import com.kazopidis.piesshop.models.model.Pie;
import com.kazopidis.piesshop.models.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@WebServlet("/buy")
public class BuyController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Pie> pies = PieDAO.getPies();
        request.setAttribute("pies",pies);


        List<Area> areas = AreaDAO.getAreas();
        request.setAttribute("areas",areas);

        //User's session
        User user = UserDAO.getUserBySession(request.getSession().getId());

        request.getSession().setAttribute("user", user);

        if (user != null) {
            List<OrderDAO.RecentOrderHistoryItem> recentOrders = OrderDAO.recentOrdersOfUser(user, 5);
            request.setAttribute("previousOrders", recentOrders); //Sets the retrieved orders as an attribute named "previousOrders" in the request.

            String previousOrder = request.getParameter("previousorder"); //: Retrieves the value of the parameter named "previousorder" from the request.
            if (previousOrder!=null) {
                int prev = Integer.parseInt(previousOrder); //: Parses the selected previous order index as an integer.

                // Iterate through the items in the order specified by the selected index.
                for (Map.Entry<String, Integer> entry : recentOrders.get(prev).getOrderItems().entrySet()) {
                    String itemName = entry.getKey();
                    int quantity = entry.getValue();

                    System.out.println(itemName);
                    System.out.println(quantity);

                    // Dynamically set request attributes based on item names
                    request.setAttribute(itemName, quantity);
                }
            }
        }

        //trivial response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/buy.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check if the user is logged in
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getId() == 0) {
            request.setAttribute("errors", "You have to login first in order to make an order.");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                    .forward(request, response);
            return;
        }

        boolean offerSelected = request.getParameter("offer1") != null;

        FormOrder formOrder = new FormOrder(
                request.getParameter("name"),
                request.getParameter("address"),
                Integer.parseInt(request.getParameter("area")),
                request.getParameter("email"),
                request.getParameter("tel"),
                request.getParameter("message"),
                null,
                offerSelected,
                request.getParameter("payment"),
                LocalDateTime.now()
        );

        List<Pie> pies = PieDAO.getPies();

        List<OrderItem> orderItems = new ArrayList<>();

        for(var pie: pies) {
            int quantity = Integer.parseInt(request.getParameter("Order"+pie.getName()));
            orderItems.add(new OrderItem(null,pie.getId(),null,quantity));
            //System.out.println("Pie: " + pie.getName() + ", Quantity: " + quantity);
        }

        int totalPies = 0;
        for (OrderItem orderItem: orderItems) {
            System.out.println(orderItem.getQuantity());
            totalPies += orderItem.getQuantity();
        }

        if (totalPies >= 10) {
            if (offerSelected) {
                formOrder.setOffer(true);
            } else {
                formOrder.setOffer(false);
            }
        } else {
            formOrder.setOffer(false);
        }

        formOrder.setOrderItems(orderItems);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FormOrder>> errors = validator.validate(formOrder);


        if (errors.isEmpty()) { //No errors
            // send E-mails
            Email.sendEmailToAdminOrderForm(formOrder);
            Email.sendEmailToClientOrderForm(formOrder);

            // Insert order to db
            OrderDAO.storeOrder(formOrder, (User) request.getSession().getAttribute("user"));

            // response
            request.setAttribute("areas",AreaDAO.getAreas());
            request.setAttribute("pies",PieDAO.getPies());
            request.setAttribute("success",true);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/buy.jsp")
                    .forward(request, response);

        } else { // Errors
            StringBuilder errorMessage = new StringBuilder("<ul>");

            errorMessage.append("<p>The form contains the following errors:</p>");

            for (var error: errors) {
                errorMessage.append("<li>" + error.getMessage() + "</li>");
            }

            errorMessage.append("</ul>");

            request.setAttribute("pies",PieDAO.getPies());
            request.setAttribute("areas", AreaDAO.getAreas());
            request.setAttribute("errors",errorMessage);
            request.setAttribute("formOrder",formOrder);

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/buy.jsp")
                    .forward(request, response);
        }

        System.out.println(formOrder);

    }
}














































