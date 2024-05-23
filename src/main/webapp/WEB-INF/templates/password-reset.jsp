<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formResetEmail" class="com.kazopidis.piesshop.forms.form.FormResetEmail" scope="request"/>
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf" %>

<body>

<div class="container">

    <%@include file="/WEB-INF/segments/header-index.jspf"%>

    <main>
        <h1>Password reset:</h1>
        <article>

            <c:choose>

                <c:when test="${requestScope['success']}">
                    <div class="success-message">
                        A message about the continuation of the password reset process was sent to your e-mail.
                    </div>
                </c:when>

                <c:otherwise>

                    <c:if test="${requestScope['errors'] != null}">
                        <div class="error-message">
                                ${requestScope['errors']}
                        </div>
                    </c:if>


                    <form action="password-reset" method="POST" class="form contact">

                        <label for="txtEmail">Email: </label>
                        <input type="email" id="txtEmail" name="email" size="40" value="${formResetEmail.email}" >

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