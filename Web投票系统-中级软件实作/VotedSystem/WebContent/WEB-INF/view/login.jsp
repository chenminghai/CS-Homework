<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
	<footer>
		<div class="row copyright" >
			<div class="col-md-12 text-center">
				<big>用户登录</big>
			</div>
		</div>
	</footer>
</div><!-- END container-wrap -->
	<div class="container-wrap">
		<div id="fh5co-contact">
			<div class="row">
				<div class="col-md-2 col-md-push-1 animate-box">
				</div>
				<div class="col-md-6 col-md-push-1 animate-box">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" name="username" class="form-control" placeholder="用户名">
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<input type="password" name="password" class="form-control" placeholder="密码">
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<img src="${pageContext.request.contextPath}/SafeCode" id="safecode" />
								<input type="text" name="code" style="height:25px;" placeholder="请输入验证码"/>
								<a href="#" id="clickSafe">看不清？</a>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<input type="button" id="submit" value="登 录" class="btn btn-primary btn-modify">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script>
	$('#safecode').click(function () {
		document.getElementById("safecode").src='${pageContext.request.contextPath}/SafeCode?id='+Math.random();
		c='${sessionScope.rand}';
	});
	$('#clickSafe').click(function () {
		document.getElementById("safecode").src='${pageContext.request.contextPath}/SafeCode?id='+Math.random();
		c='${sessionScope.rand}';
	});
    $('#submit').click(function () {
        var username = $("input[name=username]").val();
        var password = $("input[name=password]").val();
        var code = $("input[name=code]").val();
        if(!username || !password || !code){
            alert('所有字段不能为空');
            return false;
        }
        $.ajax({
            type:'POST',
            url:'${pageContext.request.contextPath}/admin/user/login',
            dataType:'json',
            data:{username:username,password:password,code:code},
            success:function (response) {
                if(response.status == 1){
                    window.location.href = '${pageContext.request.contextPath}/index';
                }else {
                    alert('登录失败,请仔细检查账号,密码以及验证码');
                    return false;
                }
            },
            error:function (response) {
                alert('发生未知错误，请稍后再尝试');
            }
        });
    });
</script>