<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formRegister" scope="request" class="com.kazopidis.piesshop.forms.form.FormRegister"/>
<jsp:useBean id="user" class="com.kazopidis.piesshop.models.model.User" scope="session" />
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf" %>

<body>

<div class="container">

    <%@include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
        <h1>Registration on the page</h1>
        <article>

            <c:choose>

                <c:when test="${requestScope['success']}">
                    <div class="success-message">
                        Successful connection! Sign up to your email, for the necessary verification! (You cannot log in if you have not verified your email)
                    </div>
                </c:when>

                <c:when test="${requestScope['registerComplete'] == true}">
                    <div class="success-message">
                        Your account has been verified! Proceed to <a href="login">login</a>
                    </div>
                </c:when>

                <c:when test="${requestScope['registerComplete'] == false}">
                    <div class="error-message">
                        Failure to verify the registration, please go back to <a href="register">register</a>
                    </div>
                </c:when>

                <c:otherwise>

                    <c:if test="${requestScope['errors'] != null}">
                        <div class="error-message">
                            ${requestScope['errors']}
                        </div>
                    </c:if>


            <form action="register" method="POST" class="form contact">
                <label for="txtName">Full Name(*):</label>
                <input type="text" id="txtName" name="fullName"  size="40" value="${formRegister.fullName}" >

                <label for="txtEmail">E-mail(*): </label>
                <input type="email" id="txtEmail" name="email" size="40" value="${formRegister.email}" >

                <label for="txtTel">Phone: </label>
                <input type="tel" id="txtTel" name="tel" size="10" value="${formRegister.tel}">

                <label for="txtUsername">Username(*): </label>
                <input type="text" id="txtUsername" name="username" size="10" value="${formRegister.username}">

                <label for="txtPassword">Password(*): </label>
                <input type="password" id="txtPassword" name="password" maxlength="30">

                <label for="txtPassword2">Password(Again)(*): </label>
                <input type="password"  id="txtPassword2" name="password2" maxlength="30">

                <span>(*) The fields are required</span>
                <input type="submit" value="Submit">
            </form>


                </c:otherwise>

            </c:choose>

        </article>

        <%@include file="/WEB-INF/segments/aside.jspf"%>

    </main>

    <%@include file="/WEB-INF/segments/footer.jspf"%>

</div>
</body>
</html>