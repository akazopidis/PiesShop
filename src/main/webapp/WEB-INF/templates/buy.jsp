<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formOrder" class="com.kazopidis.piesshop.forms.form.FormOrder" scope="request" />
<jsp:useBean id="user" class="com.kazopidis.piesshop.models.model.User" scope="session" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
        <h1>The pies</h1>
        <article>
            <h2>Our pies at your place! </h2>
            <p>
                Ordering hours (18:00-22:00)
            </p>

            <c:choose>
                <c:when test="${requestScope['success']}">
                    <div class="success-message">
                        We have received your order and our pies will be with you soon!
                    </div>
                </c:when>
                <c:otherwise>

                    <c:choose>
                        <c:when test="${user.id==0}">
                            <p>
                                <a href="login?previouspage=buy">Login</a> to make an order, view your order history (or to repeat an old order)
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p>
                                Welcome ${user.fullName}! You can repeat some of your previous orders:
                            </p>
                            <ul>
                                <c:set var="cnt" value="0" scope="request"/>
                                <c:forEach var="order" items="${requestScope['previousOrders']}">
                                    <li>
                                        <a href="buy?previousorder=${cnt}">${order.stamp}: ${order.orderItems}</a>
                                        <c:set var="cnt" value="${cnt + 1 }" scope="request"/>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${requestScope['errors']!=null}">
                        <div class="error-message">${requestScope['errors']} </div>
                    </c:if>
                    <form action="buy" method="POST" class="form buy">
                        <section class="customer-info">
                            <h3>
                                Delivery Information:
                            </h3>
                            <label for="txtName">Full Name(*):</label>
                            <input type="text" id="txtName" name="name" minlength="3" maxlength="40" size="40" required
                                   value="${empty formOrder.fullName? user.fullName: formOrder.fullName}">

                            <label for="txtAddress">(Street - Number)(*):</label>
                            <input type="text" id="txtAddress" name="address" minlength="5" maxlength="40" size="40" required
                                   value="${empty formOrder.address? user.tel: formOrder.address}">

                            <label for="slArea"> Region(*):</label>
                            <select id="slArea" name="area" required>
                                <c:forEach var="item" items="${requestScope['areas']}">
                                    <option value="${item.id}" ${formOrder.areaId == item.id?"selected":""}>${item.description}</option>
                                </c:forEach>
                            </select>

                            <label for="txtEmail">E-mail(*): </label>
                            <input type="email" id="txtEmail" name="email" size="40" required
                                   value="${empty formOrder.email? user.email: formOrder.email}">

                            <label for="txtTel">Phone(*): </label>
                            <input type="tel" id="txtTel" name="tel" size="15" required
                                   value="${empty formOrder.tel? user.tel: formOrder.tel}">

                            <label for="txtMessage">Special Information for delivery: </label>
                            <textarea id="txtMessage" name="message" rows="6" cols="40">${formOrder.comments}</textarea>

                            <span>(*) The fields are required</span>
                        </section>

                        <section class="order">
                            <h3>
                                Order:
                            </h3>


                            <c:forEach var="pie" items="${requestScope['pies']}">
                                <label for="txt${pie.name}">${pie.name}: </label>
                                <input type="number" id="txt${pie.name}" name="Order${pie.name}" min="0" max="100" value="${empty formOrder.orderItems? (empty requestScope[pie.name]? 0: requestScope[pie.name]): formOrder.orderItems[pie.id-1].quantity}" >
                            </c:forEach>


                            <input type="checkbox" id="chkΟffer1" name="offer1" value="offer1-on" ${formOrder.offer==true?"checked":""}>
                            <label for="chkΟffer1">Special offer: Buy 10 pies, get a 5 euros discount on your total order</label>
                        </section>

                        <section class="payment">
                            <h3>
                                Method of Payment:
                            </h3>
                            <div class="radio-buttons">
                                <input type="radio" id="rdPayOnDelivery" name="payment" value="payOnDelivery" ${formOrder.payment=='payOnDelivery'?"checked":""}>
                                <label for="rdPayOnDelivery">Pay On Delivery</label>
                                <input type="radio" id="rdCard" name="payment" value="visa" ${formOrder.payment=='visa'?"checked":""} disabled>
                                <label for="rdCard">VISA (Not yet)</label>
                            </div>
                        </section>

                        <div class="buttons">
                            <input type="submit" value="Submit">
                            <input type="reset" value="Reset">
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </article>

        <%@ include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@ include file="/WEB-INF/segments/footer.jspf"%>
</div>
</body>


</html>
