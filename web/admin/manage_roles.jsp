<%@ page import="ir.Epy.MyStock.DAOs.CustomerDAO" %>
<%@ page import="ir.Epy.MyStock.models.Customer" %><%--
  Created by IntelliJ IDEA.
  User: py4_
  Date: 5/27/16
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../templates/header.jsp"></jsp:include>


<div class="container well">
    <h1> مدیریت نقش‌ها </h1>
    <br><br>
<form class="form-inline" action="/admin/update_role" method="post">
    <div class="form-group">
        <select name="username">
            <% for(Customer c : CustomerDAO.I().getAll()) { %>
                <option value="<%= c.username %>"> <%= c.username %> : <%= c.get_persian_role_name() %> </option>
            <% } %>
        </select>
    </div>
    <div class="form-group">
        <select name="role">
            <option value="admin">مدیر سیستم</option>
            <option value="owner">صاب شرکت</option>
            <option value="accountant">حسابدار</option>
            <option value="customer">آدم معمولی</option>
        </select>
    </div>
    <div class="form-group">
        <input type="submit" value="تغییرش بده!" class="btn btn-success">
    </div>
</form>
</div>


<jsp:include page="../templates/footer.jsp"></jsp:include>
