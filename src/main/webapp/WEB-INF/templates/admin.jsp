<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<%@include file="/WEB-INF/segments/head.jspf"%>

<body>

<div class="container">
    <%@include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>

        <h1></h1>

        <article>

        <ul>
            <li>
                Operator actions:
                <ul>
                    <li>
                        <a href="admin?action=1" >Delete users who have not verified their registration (${requestScope['adminStats'][0]} records) </a>
                    </li>
                    <li>
                        <a href="admin?action=2" >Set all the activation codes of the verified records to NULL (${requestScope['adminStats'][1]} records) </a>
                    </li>
                </ul>
            </li>
        </ul>

        </article>

        <%@include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@include file="/WEB-INF/segments/footer.jspf"%>

</div>
</body>

</html>
