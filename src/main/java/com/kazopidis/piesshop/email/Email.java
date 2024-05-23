package com.kazopidis.piesshop.email;

import com.kazopidis.piesshop.forms.form.FormContact;
import com.kazopidis.piesshop.forms.form.FormOrder;
import com.kazopidis.piesshop.models.dao.AreaDAO;
import com.kazopidis.piesshop.models.dao.PieDAO;
import com.kazopidis.piesshop.models.model.OrderItem;
import com.kazopidis.piesshop.models.model.Pie;
import com.kazopidis.piesshop.models.model.User;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

public class Email {
    private static final int PRICE_DISCOUNT = 5;

    public static void sendTextEmail(String recipientToEmail, String subject, String body) {
        // read properties
        Properties properties = null;
        try (InputStream is = Email.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create session
        Properties finalProperties = properties;
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                finalProperties.getProperty("username"),
                                finalProperties.getProperty("password")
                        );
                    }
                }
        );

        // send e-mail
        Message mimeMessage = new MimeMessage(session);
        try {
            // prepare message
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);

            // declare recipients
            Address from = new InternetAddress(properties.getProperty("mail.admin"));
            Address to = new InternetAddress(recipientToEmail);

            mimeMessage.setFrom(from);
            mimeMessage.setRecipient(Message.RecipientType.TO, to);

            // send the message
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendHtmlEmail(String recipientToEmail, String subject, String body) {
        // read properties
        Properties properties = null;
        try (InputStream is = Email.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create session
        Properties finalProperties = properties;
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                finalProperties.getProperty("username"),
                                finalProperties.getProperty("password")
                        );
                    }
                }
        );

        // send e-mail
        Message mimeMessage = new MimeMessage(session);
        try {
            // prepare message
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(body, "text/html; charset=UTF-8");

            // declare recipients
            Address from = new InternetAddress(properties.getProperty("mail.admin"));
            Address to = new InternetAddress(recipientToEmail);

            mimeMessage.setFrom(from);
            mimeMessage.setRecipient(Message.RecipientType.TO, to);

            // send the message
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmailToClientContactForm(FormContact formData) {
        String text =
                "We received your message with the following information: \n" +
                        "\tFull Name: " + formData.getFullName() + "\n" +
                        "\tE-mail: " + formData.getEmail() + "\n" +
                        "\tPhone: " + formData.getTel() + "\n" +
                        "\tMessage: " + formData.getMessage() + "\n\n" +
                        "and we will contact you soon!";

        sendTextEmail(formData.getEmail(), "Communication Update", text);
    }

    public static void sendEmailToAdminContactForm(FormContact formData) {
        // read properties
        Properties properties = null;
        try (InputStream is = Email.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text =
                "New message from the contact form on our website: \n" +
                        "\tFull Name: " + formData.getFullName() + "\n" +
                        "\tE-mail: " + formData.getEmail() + "\n" +
                        "\tPhone: " + formData.getTel() + "\n" +
                        "\tMessage: " + formData.getMessage() + "\n";

        sendTextEmail(properties.getProperty("mail.admin"), "Incoming message from the contact form", text);
    }

    public static void sendEmailToAdminOrderForm(FormOrder formOrder) {
        // read properties
        Properties properties = null;
        try (InputStream is = Email.class.getClassLoader().getResourceAsStream("mail.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

                List<Pie> pies = PieDAO.getPies();

                String text =
                "New Order: \n" +
                        "\tFull Name: " + formOrder.getFullName() + "\n" +
                        "\tAddress: " + formOrder.getAddress() + "\n" +
                        "\tArea: " + AreaDAO.getAreaById(formOrder.getAreaId()).getDescription() + "\n" +
                        "\tE-mail: " + formOrder.getEmail() + "\n" +
                        "\tPhone: " + formOrder.getTel() + "\n" +
                        "\tMessage: " + formOrder.getComments() + "\n" +
                        "\tOrder: \n";

                for(int i=0; i<pies.size(); i++) {
                    Pie pie = pies.get(i);
                    System.out.println("Test: "+pie);
                    OrderItem orderItem = formOrder.getOrderItems().get(i);
                    text += "\t\t"+pie.getName()+": "+orderItem.getQuantity() + "\n";
                }

                text += "\tOffer: " + formOrder.isOffer() + "\n" +
                "\tPayment Method: " + formOrder.getPayment() + "\n";


        sendTextEmail(properties.getProperty("mail.admin"), "New order", text);
    }

    public static void sendEmailToClientOrderForm(FormOrder formOrder) {

        List<Pie> pies = PieDAO.getPies();
        List<OrderItem> orderItems = formOrder.getOrderItems();

        String text = "<header style=\"padding: 30px;text-align: center;font-size: 20px;font-weight: bold;\">Order on the way</header>\n" +
                "<table class=\"table-pies\" style=\"border-collapse: collapse;vertical-align: center;caption-side: bottom;margin: 0 auto;box-shadow: 0 0 4px 1px #483C46;width: 90%;\">\n" +
                "  <thead>\n" +
                "    <tr style=\"background-image: linear-gradient(to bottom, #483C46, #675664);font-size: 1em;margin-bottom: 10px;color: #BEEE62;\">\n" +
                "      <th style=\"padding: 10px 20px;border: 0;\">Pie</th>\n" +
                "      <th style=\"padding: 10px 20px;border: 0;\">Quantity</th>\n" +
                "      <th style=\"padding: 10px 20px;border: 0;\">Price</th>\n" +
                "    </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n";

        double grandTotal = 0.0;

        for(int i=0; i<pies.size(); i++) {
            Pie pie = pies.get(i);
            OrderItem orderItem = orderItems.get(i);

            // Determine row style (even or odd)
            String rowStyle = i % 2 == 0 ? "" : "background-color: #ded8dd;";

            double itemTotal = orderItem.getQuantity() * pie.getPrice();
            grandTotal += itemTotal;

            text += "    <tr style=\"" + rowStyle + "\">\n" +
                    "      <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + pie.getName() + "</td>\n" +
                    "      <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + orderItem.getQuantity() + "</td>\n" +
                    "      <td style=\"text-align: center;border: 0;padding: 30px;font-weight: normal;\">" + String.format("%.2f", itemTotal) + "€</td>\n" +
                    "    </tr>\n";
        }

        if (formOrder.isOffer()) {
            text += "  </tbody>\n" +
                    "  <tfoot>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"3\" style=\"padding: 10px;font-weight: bold;font-size: 20px; text-decoration: line-through;\">Grand Total (before discount): " + String.format("%.2f", grandTotal) + "€</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"3\" style=\"padding: 10px;font-weight: bold;font-size: 20px;\">Grand Total (with discount): " + String.format("%.2f", grandTotal-PRICE_DISCOUNT) + "€</td>\n" +
                    "    </tr>\n" +
                    "  </tfoot>\n" +
                    "</table>\n";
        } else {
            text += "  </tbody>\n" +
                    "  <tfoot>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"3\" style=\"padding: 10px;font-weight: bold;font-size: 20px;\">Grand Total: " + String.format("%.2f", grandTotal) + "€</td>\n" +
                    "    </tr>\n" +
                    "  </tfoot>\n" +
                    "</table>\n";
        }

        LocalDateTime timestamp = formOrder.getTimestamp();
        LocalDateTime timestampUntil = timestamp.plus(30, ChronoUnit.MINUTES);


        text += "<p style=\"padding: 30px;text-align: center;font-size: 20px;font-weight: bold;\">Estimated Delivery Time: " +
                timestampUntil.format(DateTimeFormatter.ofPattern("d/M/u (kk:mm:ss)")) +
                "</p>";

        sendHtmlEmail(formOrder.getEmail(), "Your Order!", text);

    }

    public static void sendEmailCompleteRegister(User user) {
        String text =
                "<div>Follow the link below to complete your registration: " +
                        "<a href=\"http://localhost:8080/register?code=" + user.getCode() +"\">" +
                        "http://localhost:8080/register?code=" + user.getCode() + "</a>" +
                        "</div>";

        sendHtmlEmail(user.getEmail(),"Registration verification", text);
    }

    public static void sendEmailPasswordReset(String email, String code) {
        String text =
                "<div>Follow the link below to reset your password: " +
                        "<a href=\"http://localhost:8080/change-password?code=" + code +"\">" +
                        "http://localhost:8080/change-password?code=" + code+ "</a>" +
                        "</div>";

        sendHtmlEmail(email,"Reset password on the page", text);
    }
}
