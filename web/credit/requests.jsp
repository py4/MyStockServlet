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
        <h2>لیست تقاضا‌ها</h2>
        <div class="col-sm-7 well col-centered">
        <table>
            <tr>
                <th>شماره درخواست</th>
                <th>شماره مشتری</th>
                <th>مبلغ</th>
                <th></th>
            </tr>
            <c:forEach var="req" items="${req_list}">
                <tr>
                    <form id="request" action="/credit/credit_check" class="form-horizontal" method="post">
                        <td>
                            <c:out value="${req.id}"/>
                            <input type="hidden" name="req_id" value="${req.id}"/>
                        </td>
                        <td><c:out value="${req.customerId}"/></td>
                        <td><c:out value="${req.amount}"/></td>
                        <td>
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-success" name="action" value="accept">تأیید</button>
                                <button type="submit" class="btn btn-danger" name="action" value="deny">رد</button>
                            </div>

                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>
        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>