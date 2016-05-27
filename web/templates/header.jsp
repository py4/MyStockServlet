<%@ page import="java.util.ArrayList" %>
<%@ page import="ir.Epy.MyStock.DAOs.CustomerDAO" %>
<%@ page import="ir.Epy.MyStock.models.Customer" %><%--
  Created by IntelliJ IDEA.
  User: root
  Date: 4/4/16
  Time: 12:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Generation of Stock Core Solutions</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel=" stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <link rel="stylesheet" href="//cdn.rawgit.com/morteza/bootstrap-rtl/v3.3.4/dist/css/bootstrap-rtl.min.css">
    <link rel="stylesheet" href="../css/custom.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">

            <ul class="nav navbar-nav">
                <li><a class="navbar-brand" href="/customers/home.jsp"><i class="fa fa-newspaper-o"></i>خانه</a></li>
                <% Customer customer = CustomerDAO.I().findByUsername(request.getRemoteUser()); %>
                <% if(customer != null) { %>
                    <% if(customer.is_customer()) { %>

                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/credit/new"><i class="fa fa-credit-card"></i>درخواست اعتبار</a></li>
                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/requests/new"><i class="fa fa-newspaper-o"></i>درخواست جدید</a></li>
                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/stock/index.jsp"><i class="fa fa-newspaper-o"></i>مشاهده‌ی وضعیت بازار</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-newspaper-o"></i>مشاهده‌ی صفحه‌ی پروفایل خود</a></li>

                    <% } else if(customer.is_accountant()) { %>

                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/admin/requests"><i class="fa fa-money"></i>مدیریت درخواست‌های مالی</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>مدیریت درخواست‌های تراکنش</a></li>
                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/stock/index.jsp"><i class="fa fa-newspaper-o"></i>مشاهده‌ی وضعیت بازار</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-newspaper-o"></i>مشاهده‌ی صفحه‌ی پروفایل خود</a></li>

                    <% } else if(customer.is_owner()) { %>

                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>اضافه کردن نماد جدید</a></li>
                        <li><a class="navbar-brand" href="${pageContext.request.contextPath}/stock/index.jsp"><i class="fa fa-newspaper-o"></i>مشاهده‌ی وضعیت بازار</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-newspaper-o"></i>مشاهده‌ی صفحه‌ی پروفایل خود</a></li>

                    <% } else if(customer.is_admin()) { %>

                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>مشخص کردن حد مجاز برای معاملات</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>تایید نماد جدید اضافه شده</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-file"></i>مشاهده‌ی گزارش</a></li>
                        <li><a class="navbar-brand" href="/admin/manage_roles.jsp"><i class="fa fa-money"></i>مدیریت نقش‌ها</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>مشاهده‌ی پروفایل دیگران</a></li>
                        <li><a class="navbar-brand" href="#"><i class="fa fa-money"></i>گرفتن بکآپ از پروژه</a></li>

                    <% } %>

                        <li><a class="navbar-brand" href="/auth/logout.jsp"><i class="fa fa-money"></i>خروج</a></li>

                <% } else { %>

                <li><a class="navbar-brand" href="${pageContext.request.contextPath}/customers/new.jsp"><i class="fa fa-user"></i>ایجاد کاربر</a></li>
                <% } %>

            </ul>
        </div>
    </div>
</nav>


    <% if(request.getSession() != null && request.getSession().getAttribute("success_message") != null) { %>
<div class="alert alert-success">
    <%=  request.getSession().getAttribute("success_message") %>
    <% request.getSession().removeAttribute("success_message"); %>
</div>
    <% } %>

    <% if(request.getAttribute("errors") != null) { %>
<div class="alert alert-danger">
    <ul>
        <% for(String error : (ArrayList<String>)request.getAttribute("errors")) { %>
        <li> <%= error %> </li>
        <% } %>
    </ul>
</div>
<% } %>