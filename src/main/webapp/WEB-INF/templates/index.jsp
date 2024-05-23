<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/segments/head.jspf"%>

<body>
  <div class="container">

    <%@include file="/WEB-INF/segments/header-index.jspf"%>

    <main>
      <h1>Home Page</h1>

      <article>
        <c:choose>

        <c:when test="${requestScope['success']}">
        <div class="success-message">
          Successful Connection! Continue browsing the page!
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

      <div class="slider">
        <div class="slide slide1">
          <figure>
            <img src="../../images/pies-shop.png" alt="Pies Shop Logo">
            <figcaption>Enjoy your fresh homemade pies</figcaption>
          </figure>
        </div>
        <div class="slide slide2">
          <figure>
            <img src="../../images/store1.jpg" alt="Exterior Store Photo">
            <figcaption>Enjoy your pie with a view of Lower Patisia</figcaption>
          </figure>
        </div>
        <div class="slide slide3">
          <figure>
            <img src="../../images/store2.jpg" alt="Exterior Store Photo">
            <figcaption>With plenty of space for your children to play</figcaption>
          </figure>
        </div>
        <div class="slide slide4">
          <figure>
            <img src="../../images/delivery.jpg" alt="Delivery">
            <figcaption>We are happy to make delivery on weekdays (more than 20 pies in the order)</figcaption>
          </figure>
        </div>
        <a class="previous" onclick="plusSlides(-1)">⬅</a>
        <a class="next" onclick="plusSlides(1)">➡</a>
      </div>


      </article>

      <%@include file="/WEB-INF/segments/aside.jspf"%>

    </main>

    <%@include file="/WEB-INF/segments/footer.jspf"%>

  </div>
  <script src="../../js/slider.js"></script>
</body>


</html>
