<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
    <footer>
        <div class="row copyright" >
            <div class="col-md-12 text-center">
                <big>“${item.theme}”--候选人信息修改</big>
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
                <form action="${pageContext.request.contextPath}/admin/vote/add" method="post" id="vote_add_form">
                  <c:forEach items="${optionList}" var="option" varStatus="status">
                  	<h3 class="col-md-12 text-center">候选人：${option.content}</h3>
                  	<input type="hidden" name="option_id${status.index}" value="${option.id}"/>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">性别</label>
                                <div class="col-sm-9">
                                	<input type="text" name="sex${status.index}" class="form-control" placeholder="候选人性别">
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">出生日期</label>
                                <div class="col-sm-9">
                                	<input type="text" name="birthday${status.index}" class="form-control" placeholder="出生日期，如：1998-10-10">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">年龄</label>
                                <div class="col-sm-9">
                                	<input type="text" name="age${status.index}" class="form-control" placeholder="年龄">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">民族</label>
                                <div class="col-sm-9">
                                	<input type="text" name="nation${status.index}" class="form-control" placeholder="民族">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">政治面貌</label>
                                <div class="col-sm-9">
                                	<input type="text" name="political${status.index}" class="form-control" placeholder="政治面貌">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">家庭地址</label>
                                <div class="col-sm-9">
                                	<input type="text" name="address${status.index}" class="form-control" placeholder="家庭地址">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">联系方式</label>
                                <div class="col-sm-9">
                                	<input type="text" name="telephone${status.index}" class="form-control" placeholder="联系方式">
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">推荐意见</label>
                                <div class="col-sm-9">
                                    <textarea rows="5" cols="7" name="recommend${status.index}" class="form-control" placeholder="事迹介绍、或者是个人介绍等"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                        	<div class="form-group">
								<label for="" class="col-sm-4 control-label"></label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" id="${status.index}" name="save" value="保存" class="btn btn-primary btn-modify">
								<a href="${pageContext.request.contextPath}/index/vote/list"><input type="button" value="返 回 " class="btn btn-primary btn-modify"></a>
							</div>
                        </div>
                    </div>
                  </c:forEach>
                  <div class="col-md-12  text-center">
						<div class="form-group">
							<label for="" class="col-sm-2 control-label"></label>
							<input type="button" value="全部提交 " id="submit" class="btn btn-primary btn-modify">
						</div>
				  </div>
                </form>
            </div>
        </div>
    </div>
</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script type="text/javascript">
$("input[name=save]").click(function () {
	var id = $(this).attr('id');
    var sex = $("input[name=sex"+id+"]").val();
    var birthday = $("input[name=birthday"+id+"]").val();
    var age = $("input[name=age"+id+"]").val();
    var nation = $("input[name=nation"+id+"]").val();
    var political = $("input[name=political"+id+"]").val();
    var address = $("input[name=address"+id+"]").val();
    var telephone = $("input[name=telephone"+id+"]").val();
    var recommend = $("textarea[name=recommend"+id+"]").val();
    var option_id=$("input[name=option_id"+id+"]").val();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/admin/candidate/add',
		dataType:'json',
		data:{
			option_id:option_id,
			sex:sex,
		    birthday:birthday,
		    age:age,
		    nation:nation,
		    political:political,
		    address:address,
			telephone:telephone,
			recommend:recommend
		},
		success:function (response) {
			if(response.status == 1){
			    alert('保存成功');
			}else {
			    alert('保存失败');
			}
        }
	});
});

$('#submit').click(function () {
	var length='${fn:length(optionList)}';
	var success=1;
	for(var i=0;i<length;i++){
		
	    var sex = $("input[name=sex"+i+"]").val();
	    var birthday = $("input[name=birthday"+i+"]").val();
	    var age = $("input[name=age"+i+"]").val();
	    var nation = $("input[name=nation"+i+"]").val();
	    var political = $("input[name=political"+i+"]").val();
	    var address = $("input[name=address"+i+"]").val();
	    var telephone = $("input[name=telephone"+i+"]").val();
	    var recommend = $("textarea[name=recommend"+i+"]").val();
	    var option_id=$("input[name=option_id"+i+"]").val();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/admin/candidate/add',
			dataType:'json',
			data:{
				option_id:option_id,
				sex:sex,
			    birthday:birthday,
			    age:age,
			    nation:nation,
			    political:political,
			    address:address,
				telephone:telephone,
				recommend:recommend
			},
			success:function (response) {
				if(response.status == 1){
					success++;
					if(success==length) alert('全部保存成功');
				}else {
					alert("第"+i+"个保存失败");
				}
	        }
		});
	}
});

</script>