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
        <h2>لیست عرضه و تقاضای نماد
            <mark><c:out value="${symbol}"/></mark>
        </h2>
        <div class="col-sm-9 well col-centered">
            <div class="col-sm-4">
                <h3>لیست فروش</h3>
            <table>
                <tr>
                    <th>شماره مشتری</th>
                    <th>نوع</th>
                    <th>قیمت</th>
                    <th>تعداد</th>
                </tr>
                <c:forEach var="buy" items="${buy_queue}">
                    <tr>
                        <td><c:out value="${buy.customerId}"/></td>
                        <td><c:out value="${buy.type}"/></td>
                        <td><c:out value="${buy.basePrice}"/></td>
                        <td><c:out value="${buy.quantity}"/></td>
                    </tr>
                </c:forEach>
            </table>
            </div>

            <div class="col-sm-4">
                <h3>لیست خرید</h3>
                <table>
                    <tr>
                        <th>شماره مشتری</th>
                        <th>نوع</th>
                        <th>قیمت</th>
                        <th>تعداد</th>
                    </tr>
                    <c:forEach var="sell" items="${sell_queue}">
                        <tr>
                            <td><c:out value="${sell.customer_id}"/></td>
                            <td><c:out value="${sell.type}"/></td>
                            <td><c:out value="${sell.base_price}"/></td>
                            <td><c:out value="${sell.quantity}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>