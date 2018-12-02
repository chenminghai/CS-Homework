<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
	<footer>
		<div class="row copyright" >
			<div class="col-md-12 text-center">
				<big>用户注册</big>
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
									<input type="text" name="username" class="form-control" placeholder="用户名(必填)">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<input type="text" name="realname" class="form-control" placeholder="真实姓名(必填)">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<input type="password" name="password" class="form-control" placeholder="密码(必填)">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<input type="password" name="password_again" class="form-control" placeholder="确认密码(必填)">
								</div>
							</div>
							
							<div class="col-md-12">
								<div class="form-group">
									<input type="text" name="telephone" class="form-control" placeholder="联系方式">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<input type="text" name="sex" class="form-control" placeholder="性别">
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<input type="text" name="birthday" class="form-control" placeholder="出生日期">
								</div>
							</div>
							
							<div class="col-md-12">
								<div class="form-group">
									<input type="button" value="注 册" id="submit" class="btn btn-primary btn-modify">
								</div>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script>
	$('#submit').click(function () {
        var username = $("input[name=username]").val();
        var realname = $("input[name=realname]").val();
        var password = $("input[name=password]").val();
        var password_again = $("input[name=password_again]").val();
        var telephone = $("input[name=telephone]").val();
        var sex = $("input[name=sex]").val();
        var birthday = $("input[name=birthday]").val();
        if (!username || !realname || !password || !password_again){
            alert('必填字段字段不能为空');
            return false;
        }
        if(password != password_again){
            alert('密码不一致');
            return false;
		}
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/register',
			dataType:'json',
			data:{
			    username:username,
			    realname:realname,
				password:password,
				telephone:telephone,
				sex:sex,
				birthday:birthday
			},
			success:function (response) {
				if(response.status == 1){
				    alert('注册成功');
				    window.location.href='${pageContext.request.contextPath}/admin/user/login';
				}else {
				    alert('注册失败,用户名已被注册');
				}
            }
		});
    });
</script>
