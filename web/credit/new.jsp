<%--
  Created by IntelliJ IDEA.
  User: esihaj
  Date: 4/8/16
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../templates/header.jsp"></jsp:include>

<div class="container">
    <div class="row row-centered">
        <h2>انتقال اعتبار</h2>
        <div class="col-sm-7 well col-centered">
            <form id="request" action="/credit/new" class="form-horizontal" method="post">
                <div class="form-group">
                    <label for="transaction_type" class="col-sm-3 control-label">نوع درخواست</label>
                    <div class="col-sm-9">
                        <select class="form-control" id="transaction_type" name="transaction_type">
                            <option value="deposit">واریز</option>
                            <option value="withdraw">برداشت</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="credit_value" class="col-sm-3 control-label">مبلغ</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="credit_value" placeholder="مبلغ" name="credit_value">
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