<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        Your password has been successfully changed! Proceed to <a href="login">login</a>
                    </div>
                </c:when>

                <c:otherwise>

                    <c:if test="${requestScope['errors'] != null}">
                        <div class="error-message">
                                ${requestScope['errors']}
                        </div>
                    </c:if>


                    <form action="change-password" method="POST" class="form contact">

                        <label for="txtPassword">Password: </label>
                        <input type="password" id="txtPassword" name="password" maxlength="30" >

                        <label for="txtPassword2">Password(Again): </label>
                        <input type="password" id="txtPassword2" name="password2" maxlength="30" >

                        <input type="hidden" name="code" value="${requestScope['code']}">

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