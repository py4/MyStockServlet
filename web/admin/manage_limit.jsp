<%@ page import="ir.Epy.MyStock.DAOs.ConfigDAO" %><%--
  Created by IntelliJ IDEA.
  User: py4_
  Date: 5/27/16
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../templates/header.jsp"></jsp:include>


<div class="container well">
    <h1> مدیریت محدودیت معاملات GTC </h1>
    <br><br>
    <form class="form-inline" action="/admin/update_limit" method="post">
        <label> محدودیت فعلی: <%= ConfigDAO.I().get_limit() %> </label>
        <div class="form-group">
            <input type="text" name="new_limit">
        </div>
        <div class="form-group">
            <input type="submit" value="تغییرش بده!" class="btn btn-success">
        </div>
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
</div>


<jsp:include page="../templates/footer.jsp"></jsp:include>
