<%--
  Created by IntelliJ IDEA.
  User: py4_
  Date: 4/4/16
  Time: 7:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../templates/header.jsp"></jsp:include>
This is credit/index.jsp
<h2>اعتبار فعلی</h2>
<c:if test="${credit == null}">هیچ کاربری انتخاب نشده است!</c:if>
<c:if test="${credit != null}">
    <div class="col-sm-4 well col-centered">
        <div > کاربر:
            <c:out value="${id}"/><br/>
        </div>
        <div>
            اعتبار:
            <c:out value="${credit}"/><br/>
        </div>
    </div>
</c:if>
<jsp:include page="../templates/footer.jsp"></jsp:include>
