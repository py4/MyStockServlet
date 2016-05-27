<%--
  Created by IntelliJ IDEA.
  User: py4_
  Date: 5/27/16
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.invalidate(); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:redirect url="/customers/home.jsp"/>