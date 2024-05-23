package com.kazopidis.piesshop.forms.form;

import com.kazopidis.piesshop.forms.validators.AtLeastOneItemInOrderConstraint;
import com.kazopidis.piesshop.forms.validators.OrderItemValuesConstraint;
import com.kazopidis.piesshop.forms.validators.OrderTimestampConstraint;
import com.kazopidis.piesshop.models.model.OrderItem;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class FormOrder {
    @NotNull (message = "The full name must not be null")
    @NotEmpty (message = "The full name must not be blank")
    String fullName;

    @NotNull (message = "The address must not be null")
    @NotEmpty (message = "The address must not be blank")
    String address;

    @NotNull (message = "Select Area")
    Integer areaId;

    @NotNull (message = "The e-mail must not be null")
    @Email (message = "The e-mail is not valid")
    String email;

    @Pattern(regexp = "^[26][0-9]{9}$", message = "Invalid phone number")
    String tel;
    String comments;

    @AtLeastOneItemInOrderConstraint(message="You must order at least one pie")
    @OrderItemValuesConstraint(message="Each quantity should be between 0 and 100")
    List<OrderItem> orderItems;

    @NotNull(message = "A price must be selected for the offer")
    boolean offer;

    @NotNull(message = "You must choose a payment method")
    String payment;

    @OrderTimestampConstraint(message = "Orders can only be placed between 18:00 and 22:00.")
    LocalDateTime timestamp;

    public FormOrder() {
    }

    public FormOrder(String fullName, String address, Integer areaId, String email, String tel, String comments, List<OrderItem> orderItems, boolean offer, String payment, LocalDateTime timestamp) {
        this.fullName = fullName;
        this.address = address;
        this.areaId = areaId;
        this.email = email;
        this.tel = tel;
        this.comments = comments;
        this.orderItems = orderItems;
        this.offer = offer;
        this.payment = payment;
        this.timestamp = timestamp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FormOrder{" +
                "\nfullname='" + fullName + '\'' +
                ", \naddress='" + address + '\'' +
                ", \nareaId=" + areaId +
                ", \nemail='" + email + '\'' +
                ", \ntel='" + tel + '\'' +
                ", \ncomments='" + comments + '\'' +
                ", \norderItems=" + orderItems +
                ", \noffer=" + offer +
                ", \npayment='" + payment + '\'' +
                ", \ntimestamp=" + timestamp +
                '}';
    }
}
