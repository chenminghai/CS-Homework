<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
	<footer>
		<div class="row copyright" >
			<div class="col-md-12 text-center">
				<big>投票列表</big>
			</div>
		</div>
	</footer>
</div><!-- END container-wrap -->
	<div class="container-wrap">
		<div id="fh5co-blog">
			<div class="row">
				<c:forEach items="${itemList}" var="item" varStatus="num">
					<div class="col-md-4">
						<div class="fh5co-blog animate-box">
							<div class="blog-text">
								<span class="posted_on">时间：${item.startTime}---${item.stopTime}</span>
								<h3>主题：${item.theme}</h3>
								<p>发起人：${item.userName}</p>
								<p>类型：${item.type eq 1 ? "单选" : "多选"}</p>
								<ul class="stuff">
									<li title="总投票数"><i class="icon-eye2"></i>${item.allVoteCount}</li>
									<li>
										<c:choose>
											<c:when test="${item.status eq 1}">
												<a>还未开始<i class="icon-arrow-right22"></i></a>
											</c:when>
											<c:when test="${item.status eq 2}">
												<a href="${pageContext.request.contextPath}/index/vote?id=${item.id}">点击投票<i class="icon-arrow-right22"></i></a href="${pageContext.request.contextPath}/index/vote?id=${item.id}">
											</c:when>
											<c:otherwise>
												<a>已经截止<i class="icon-arrow-right22"></i></a>
											</c:otherwise>
										</c:choose>
									</li>
									<li><a href="${pageContext.request.contextPath}/vote/show?id=${item.id}">查看详情<i class="icon-arrow-right22"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
<%@include file="foot.jsp"%>
<script>
    if ("${msg}"){
        alert("${msg}");
        <%
        session.removeAttribute("msg");
        %>
    }
</script>

