<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="com.kazopidis.piesshop.models.model.User" scope="session" />
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf" %>

<body>

<div class="container">

  <%@include file="/WEB-INF/segments/header-index.jspf"%>

  <main>
    <h1>Log out of the page</h1>
    <article>

      <c:choose>

        <c:when test="${requestScope['success']}">
          <div class="success-message">
            You are disconnected from the page!
          </div>
        </c:when>

        <c:otherwise>

          <c:if test="${requestScope['errors'] != null}">
            <div class="error-message">
                ${requestScope['errors']}
            </div>
          </c:if>

        </c:otherwise>

      </c:choose>

    </article>

    <%@include file="/WEB-INF/segments/aside.jspf"%>

  </main>

  <%@include file="/WEB-INF/segments/footer.jspf"%>

</div>
</body>
</html>