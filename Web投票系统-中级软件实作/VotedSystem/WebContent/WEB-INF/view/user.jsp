<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
	<footer>
		<div class="row copyright" >
			<div class="col-md-12 text-center">
				<big>用户信息</big>
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
                             <label for="" class="col-sm-3 control-label">昵称</label>
                             <div class="col-sm-9">
                                 <input type="text" name="username" class="form-control" value="${user.username}" placeholder="<未填写该信息>(必填)"  disabled>
                             </div>
                         </div>
                     </div>
                     <div class="col-md-12">
                         <div class="form-group">
                             <label for="" class="col-sm-3 control-label">真实姓名</label>
                             <div class="col-sm-9">
                                 <input type="text" name="realname" class="form-control" value="${user.realName}" placeholder="<未填写该信息>(必填)" disabled="disabled">
                             </div>
                         </div>
                     </div>
                     <div class="col-md-12">
                         <div class="form-group">
                             <label for="" class="col-sm-3 control-label">联系方式</label>
                             <div class="col-sm-9">
                                 <input type="text" name="telephone" class="form-control" value="${user.telephone}" placeholder="<未填写该信息>" disabled="disabled">
                             </div>
                         </div>
                     </div>
                     <div class="col-md-12">
                         <div class="form-group">
                             <label for="" class="col-sm-3 control-label">性别</label>
                             <div class="col-sm-9">
                                 <input type="text" name="sex" class="form-control" value="${user.sex}" placeholder="<未填写该信息>" disabled="disabled">
                             </div>
                         </div>
                     </div>
                     <div class="col-md-12">
                         <div class="form-group">
                             <label for="" class="col-sm-3 control-label">出生日期</label>
                             <div class="col-sm-9">
                                 <input type="text" name="birthday" class="form-control" value="${user.birthday}" placeholder="<未填写该信息>" disabled="disabled">
                             </div>
                         </div>
                     </div>
                     
                     <div class="col-md-12" id="oldpwd"></div>
                     <div class="col-md-12" id="newpwd"></div>
                     <div class="col-md-12" id="renewpwd"></div>
                     
                     <div class="col-md-12">
                         <div class="form-group">
                             <label for="" class="col-sm-3 control-label"></label>
                             <div class="col-sm-9">
                                 <input type="button" value="修改信息" id="update" class="btn btn-primary btn-modify col-sm-3">
								 <input type="button" value="保存修改" id="save" class="btn btn-primary btn-modify col-sm-3">
								 <input type="button" value="返回" id="back" class="btn btn-primary btn-modify col-sm-3" disabled="disabled">
                             </div>
                         </div>
                     </div>
                 </div>
            </div>
        </div>
    </div>
</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script>
	$('#update').click(function () {
		$("input[type=text]").attr("disabled",false);
		$("input[name=username]").attr("disabled",true);
		this.setAttribute("disabled",true);
		$('#back').attr("disabled",false);
		var html1="<div class='form-group'><label for='' class='col-sm-3 control-label'>";
		var html2="原密码";
		var html3="</label><div class='col-sm-9'>";
        var html4="<input type='text' name='oldpassword' class='form-control' placeholder='请输入原密码(必填)'>";
        var html5="</div></div>";
        document.getElementById("oldpwd").innerHTML = html1 + html2 + html3 + html4 + html5;
        html2="新密码";
		html4="<input type='text' name='newpassword' class='form-control' placeholder='请输入新密码(必填)'>";
		document.getElementById("newpwd").innerHTML = html1 + html2 + html3 + html4 + html5;
		html2="确认密码";
		html4="<input type='text' name='renewpassword' class='form-control' placeholder='请再次输入原密码(必填)'>";
		document.getElementById("renewpwd").innerHTML = html1 + html2 + html3 + html4 + html5;
	});
	
	$('#back').click(function () {
		window.location.href='${pageContext.request.contextPath}/admin/user/update';
	});
	
	$('#save').click(function () {
        var realname = $("input[name=realname]").val();
        var telephone = $("input[name=telephone]").val();
        var sex = $("input[name=sex]").val();
        var birthday = $("input[name=birthday]").val();
        var oldpassword = $("input[name=oldpassword]").val();
        var newpassword = $("input[name=newpassword]").val();
        var renewpassword = $("input[name=renewpassword]").val();
        if (!realname || !oldpassword || !newpassword || !renewpassword){
            alert('必填字段字段不能为空');
            return false;
        }
        if(newpassword != renewpassword){
            alert('密码不一致');
            return false;
		}
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/admin/user/update',
			dataType:'json',
			data:{
			    realname:realname,
			    telephone:telephone,
			    sex:sex,
				birthday:birthday,
				oldpassword:oldpassword,
				newpassword:newpassword,
				renewpassword:renewpassword
			},
			success:function (response) {
				if(response.status == 0){
					alert('修改失败');
				}
				if(response.status == 1){
				    alert('修改成功');
				    $("input[type=text]").attr("disabled",false);
				    window.location.href='${pageContext.request.contextPath}/admin/user/update';
				}
				if(response.status == 2){
					alert('原密码不正确');
				}
            }
		});
    });
</script>
