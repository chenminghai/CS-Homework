<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
    <footer>
        <div class="row copyright" >
            <div class="col-md-12 text-center">
                <big>进行投票</big>
            </div>
        </div>
    </footer>
</div><!-- END container-wrap -->
	<div class="container-wrap">
		<div id="fh5co-contact">
			<form action="${pageContext.request.contextPath}/index/vote?id=${item.id}" method="post" id="form">
				<div class="row">
					<div class="col-md-10 col-md-push-4 animate-box">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="" class="col-sm-4 control-label"></label>
									<div class="col-sm-9">
										<font size="6" color="black">${item.theme}</font> 
										<span>${type}</span>
                                	</div>
								</div>
							</div>
						</div>
						
						<ul class="contact-info">
							<c:forEach items="${optionList}" var="option">
								<c:choose>
									<c:when test="${item.type eq 1}">
										<li><input type="radio" name="answer" value="${option.id}">${option.content}</li>
									</c:when>
									<c:otherwise>
										<li><input type="checkbox" name="answer" value="${option.id}">${option.content}</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<label for="" class="col-sm-4 control-label"></label>
								<input type="button" id="submit1" value="提 交" class="btn btn-primary btn-modify">
								<a href="${pageContext.request.contextPath}/index/vote/list"><input type="button" value="返 回 " class="btn btn-primary btn-modify"></a>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script type="text/javascript">
	$("#submit1").click(function () {
        var val=$('input[name="answer"]:checked');
        var len=${item.number};
        if (val.length <= 0){
            alert("请投票后再提交");
            return;
		}
        if(val.length!=len){
        	alert("不能选多于或少于"+len+"个");
        	return;
        }
		$("form").submit();
    });
	if ("${msg}"){
        alert("${msg}");
        <%
        session.removeAttribute("msg");
        %>
	}
</script>