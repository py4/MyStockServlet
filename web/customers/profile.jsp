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

    <div class="col-sm-4 well col-centered">
        <h3>مشخصات کاربری</h3>
        <div > کد کاربری:
            <c:out value="${customer.id}"/><br/>
        </div>
        <div>
            نام:
            <c:out value="${customer.name}"/><br/>
        </div>
        <div>
            نام خانوادگی:
            <c:out value="${customer.family}"/><br/>
        </div>
        <div>
            نام کاربری:
            <c:out value="${customer.username}"/><br/>
        </div>
        <div>
            اعتبار:
            <c:out value="${customer.deposit}"/><br/>
        </div>
    </div>
<c:if test="${customer.is_owner()}">
    <div class="col-sm-4 well col-centered">
        <h3>نماد‌ها</h3>
        <table>
            <tr>
                <th>نماد</th>
                <th>وضعیت</th>
            </tr>
            <c:forEach var="stock" items="${stock_list}">
                <tr>
                    <td> <c:out value="${stock.symbol}"/> </td>
                    <td> <c:choose>
                        <c:when test="${stock.status == 0}">
                            در انتظار
                        </c:when>
                        <c:when test="${stock.status == 1}">
                            تایید شده
                        </c:when>
                        <c:when test="${stock.status == 2}">
                            رد شده
                        </c:when>
                    </c:choose> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<c:if test="${customer.is_customer()}">
    <div class="col-sm-4 well col-centered">
        <h3>سهام‌ها</h3>
        <table>
            <tr>
                <th>نماد</th>
                <th>تعداد</th>
            </tr>
            <c:forEach var="share" items="${share_list}">
                <tr>
                        <td> <c:out value="${share.stock_symbol}"/> </td>
                        <td> <c:out value="${share.quantity}"/> </td>
                </tr>
            </c:forEach>
        </table>
    </div>


    <div class="col-sm-9 well col-centered">
        <h3>درخواست‌های در انتظار</h3>
        <table>
            <tr>
                <th>نماد</th>
                <th>خرید یا فروش</th>
                <th>نوع</th>
                <th>قیمت</th>
                <th>تعداد</th>
                <th>وضعیت</th>
            </tr>
            <c:forEach var="req" items="${request_list}">
                <tr>
                    <td><c:out value="${req.stock_symbol}"/></td>
                    <td>
                        <c:if test="${req.is_buy()}">فروش</c:if>
                        <c:if test="${!req.is_buy()}">خرید</c:if>
                    </td>
                    <td><c:out value="${req.type}"/></td>
                    <td><c:out value="${req.base_price}"/></td>
                    <td><c:out value="${req.quantity}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${req.status == 0}">
                                در انتظار
                            </c:when>
                            <c:when test="${req.status == 1}">
                            تایید شده
                            </c:when>
                            <c:when test="${req.status == 2}">
                            رد شده
                            </c:when>
                        </c:choose>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>


</c:if>

<jsp:include page="../templates/footer.jsp"></jsp:include>
