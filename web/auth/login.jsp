<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../templates/header.jsp"></jsp:include>


<form name="loginForm" method="post" action="j_security_check">
    <table border=0>
        <tr>
            <td>نام کاربری:</td>
            <td><input name="j_username" type="text" size="20"/></td>
        </tr>
        <tr>
            <td>کلمه‌ی عبور:</td>
            <td><input name="j_password" type="password" size="20"/></td>
        </tr>
        <tr>
            <td colspan=2 align="center">
                <input name="Submit" type="submit" value='ورود!' class="btn btn-info"/>
            </td>
        </tr>
    </table>
</form>


<jsp:include page="../templates/footer.jsp"></jsp:include>