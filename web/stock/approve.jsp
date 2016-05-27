<%@ page import="ir.Epy.MyStock.Constants" %><%--
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
            <form id="request" action="/stock/approve" class="form-horizontal" method="post">
                <select id="symbol" name="symbol">
                    <c:forEach var="sym" items="${symbol_list}">
                        <option value="${sym}"><c:out value="${sym}"/></option>
                    </c:forEach>
                </select>
                <select id="status" name="status">
                    <option value=<%=Constants.AcceptStatus%>> تایید </option>
                    <option value=<%=Constants.RejectStatus%>> رد </option>
                </select>
                <button type="submit" class="btn btn-success" name="action" value="show">تایید</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>