<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/segments/head.jspf"%>

<body>
  <div class="container">

    <%@include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
      <h1>The pies</h1>
      <article>
        <table class="table-pies">
          <caption>Our pies</caption>
          <thead>
            <tr>
              <th></th>
              <th>Pie</th>
              <th>Price/slice</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="pie" items="${requestScope['pies']}">
            <tr>
              <td><img src="${pageContext.request.contextPath}${pie.fileName}" alt="${pie.name}"><span>${pie.name}</span></td>
              <td><a href="${pageContext.request.contextPath}/pie?id=${pie.id}">${pie.name}</a></td>
              <td>${pie.price} €</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </article>

      <%@include file="/WEB-INF/segments/aside.jspf"%>

    </main>

    <%@include file="/WEB-INF/segments/footer.jspf"%>

  </div>
</body>


</html>
