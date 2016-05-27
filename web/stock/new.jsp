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
        <h2>افزودن نماد</h2>
        <div class="col-sm-7 well col-centered">
            <form id="request" action="/stock/new" class="form-horizontal" method="post">
                <div class="form-group">
                    <label for="symbol" class="col-sm-3 control-label">نام نماد</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="symbol" id="symbol" placeholder="نام نماد">
                    </div>
                </div>

                <button type="submit" class="btn btn-success" name="action" value="show">اضافه کن!</button>
            </form>

            </table>
        </div>
    </div>
</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>