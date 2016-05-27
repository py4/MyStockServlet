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
            <form id="customer" action="/customers/create" class="form-horizontal" method="post">

                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label">نام</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="name" id="name" placeholder="نام">
                    </div>
                </div>

                <div class="form-group">
                    <label for="family" class="col-sm-3 control-label">نام خانوادگی</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="family" id="family" placeholder="نام خانوادگی">
                    </div>
                </div>

                <div class="form-group">
                    <label for="username" class="col-sm-3 control-label">نام کاربری</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="username" id="username" placeholder="نام کاربری">
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-3 control-label">رمز عبور</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" name="password" id="password" placeholder="رمز عبور">
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success">ثبت نام</button>
                    </div>
                </div>
                <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
            </form>
        </div>
    </div>
</div>


<jsp:include page="../templates/footer.jsp"></jsp:include>