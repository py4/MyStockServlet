<%--
  Created by IntelliJ IDEA.
  User: esihaj
  Date: 4/8/16
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../templates/header.jsp"></jsp:include>

<div class="container">
    <div class="row row-centered">
        <h2>لیست نماد‌ها</h2>
        <div class="col-sm-7 well col-centered">
            <form id="request" action="/stock/queues" class="form-horizontal" method="post">
                <select id="symbol" name="symbol">
                    <c:forEach var="sym" items="${symbol_list}">
                        <option value="${sym}"><c:out value="${sym}"/></option>
                    </c:forEach>
                </select>

                <button type="submit" class="btn btn-success" name="action" value="show">نمایش</button>
            </form>

            </table>
        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>