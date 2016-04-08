<%@ page import="java.util.ArrayList" %><%--
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
                <li><a class="navbar-brand" href="/customers/new"><i class="fa fa-user"></i>ایجاد کاربر</a></li>
                <li><a class="navbar-brand" href="/requests/new"><i class="fa fa-newspaper-o"></i>
                    درخواست جدید</a></li>
                <li><a class="navbar-brand" href="/credit/new"><i class="fa fa-credit-card"></i>
                   درخواست اعتبار</a></li>
                <li><a class="navbar-brand" href="/admin/requests"><i class="fa fa-money"></i>
                    مدیریت درخواست‌های مالی</a></li>
                <li><a class="navbar-brand" href="/stock"><i class="fa fa-shopping-basket"></i>
                    لیست سهام</a></li>

            </ul>
        </div>
    </div>
</nav>

<% if(request.getAttribute("success_message") != null) { %>
<div class="alert alert-success">
    <%= request.getAttribute("success_message") %>
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