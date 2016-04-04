<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 4/4/16
  Time: 3:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../templates/header.jsp"></jsp:include>


<div class="container">
    <div class="row row-centered">
        <h2>ایجاد کاربر جدید</h2>
        <div class="col-sm-7 well col-centered">
            <form id="customer" action="/customers/new" class="form-horizontal" method="post">
                <div class="form-group">
                    <label for="id" class="col-sm-3 control-label">کد کاربری</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="id" id="id" placeholder="کد کاربری">
                    </div>
                </div>

                <div class="form-group">
                    <label for="id" class="col-sm-3 control-label">نام</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="name" id="name" placeholder="نام">
                    </div>
                </div>

                <div class="form-group">
                    <label for="id" class="col-sm-3 control-label">نام خانوادگی</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="family" id="family" placeholder="نام خانوادگی">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success">ثبت نام</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<jsp:include page="../templates/footer.jsp"></jsp:include>