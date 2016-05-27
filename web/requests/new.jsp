<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 4/4/16
  Time: 12:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../templates/header.jsp"></jsp:include>

<div class="container">
    <div class="row row-centered">
        <h2>معامله‌ی سهام</h2>
        <div class="col-sm-7 well col-centered">
            <form id="request" action="/requests/new" class="form-horizontal" method="post">
                <div class="form-group">
                    <label for="buy_or_sell" class="col-sm-3 control-label">نوع درخواست</label>
                    <div class="col-sm-9">
                        <select class="form-control" id="buy_or_sell" name="buy_or_sell">
                            <option value="buy">خرید</option>
                            <option value="sell">فروش</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="instrument" class="col-sm-3 control-label">سهام</label>
                    <div class="col-sm-9">
                        <select class="form-control" id="instrument" name="instrument">
                            <c:forEach var="sym" items="${symbol_list}">
                                <option value="${sym}"><c:out value="${sym}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="col-sm-3 control-label">قیمت هر سهم</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="price" placeholder="قیمت هر سهم" name="price">
                    </div>

                    <label for="quantity" class="col-sm-3 control-label">تعداد سهم</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="quantity" placeholder="تعداد سهم" name="quantity">
                    </div>
                </div>

                <div class="form-group">
                    <label for="type" class="col-sm-3 control-label">نوع درخواست</label>
                    <div class="col-sm-9">
                        <select class="form-control" id="type" name="type">
                            <option>GTC</option>
                            <option>IOC</option>
                            <option>MPO</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success">ثبت درخواست</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>