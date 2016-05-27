<%@ page import="ir.Epy.MyStock.models.StockRequest" %>
<%@ page import="ir.Epy.MyStock.DAOs.GTCDAO" %>
<%@ page import="ir.Epy.MyStock.Constants" %>
<%@ page import="ir.Epy.MyStock.DAOs.CustomerDAO" %><%--
  Created by IntelliJ IDEA.
  User: py4_
  Date: 5/27/16
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../templates/header.jsp"></jsp:include>


<div class="container">
    <div class="row row-centered">
        <h2>تراکنش‌های نیازمند تایید</h2>
        <div class="col-sm-7 well col-centered">
            <table>
                <tr>
                    <th>کد تراکنش</th>
                    <th>نام مشتری</th>
                    <th>نام سهام</th>
                    <th>تعداد</th>
                    <th>قیمت پایه</th>
                    <th>نوع تراکنش</th>
                    <th></th>
                    <th></th>
                </tr>
                <% for(StockRequest req : GTCDAO.I().getAll(Constants.PendingStatus)) { %>
                    <tr>
                        <form id="request" action="/accountant/update_gtc_status" class="form-horizontal" method="post">
                            <td>
                                <%= req.id %>
                                <input type="hidden" name="req_id" value="<%= req.id%>"/>
                            </td>
                            <td><%= CustomerDAO.I().find(req.customer_id).username %></td>
                            <td><%= req.stock_symbol %></td>
                            <td><%= req.quantity %></td>
                            <td><%= req.base_price%></td>
                            <td><%= req.type%></td>
                            <td>
                                <button type="submit" class="btn btn-success" name="action" value="accept">تأیید</button>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-danger" name="action" value="deny">رد</button>
                            </td>
                            <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
                        </form>
                    </tr>
                <% } %>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../templates/footer.jsp"></jsp:include>
