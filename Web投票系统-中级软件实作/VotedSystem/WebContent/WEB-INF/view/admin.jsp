<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="head.jsp"%>
    <div class="container-wrap">
        <footer>
            <div class="row copyright" >
                <div class="col-md-12 text-center">
                    <big>我发起的投票</big>
                    <a href="${pageContext.request.contextPath}/admin/user/logout">
                        <button class="btn-sm btn-success" onclick="if (!confirm('确定退出？')) return false;">退出</button>
                    </a>
                </div>
            </div>
        </footer>
    </div><!-- END container-wrap -->
    <div class="container-wrap">
        <div id="fh5co-blog">
            <a href="${pageContext.request.contextPath}/admin/vote/add"><button class="btn btn-primary btn-demo"> 新增投票</button></a>当前投票个数：${length }个
            <div class="row">
                <C:forEach items="${itemList}" var="item">
                    <div class="col-md-4">
                        <div class="fh5co-blog animate-box">
                            <!--<a href="${pageContext.request.contextPath}#" class="blog-bg" style="background-image: url(images/blog-2.jpg);"></a>-->
                            <div class="blog-text">
                                <div class="">
                                    <span style="width:32%"><a href="${pageContext.request.contextPath}/admin/vote/update?id=${item.id}">修改</a></span>
                                    <c:choose>
                                       <c:when test="${item.isStop eq 1}">
                                           <span style="width:32%"><a href="${pageContext.request.contextPath}/admin/vote/start?id=${item.id}" onclick="if(!confirm('确定启用？')) return false;">启用</a></span>
                                       </c:when>
                                        <c:otherwise>
                                            <span style="width:32%"><a href="${pageContext.request.contextPath}/admin/vote/stop?id=${item.id}" onclick="if(!confirm('确定停用？')) return false;">停用</a></span>
                                        </c:otherwise>
                                    </c:choose>
                                    <span style="width:32%"><a href="${pageContext.request.contextPath}/admin/vote/delete?id=${item.id}" onclick="if(!confirm('确定删除？')) return false;">删除</a></span>
                                </div>
                                <span class="posted_on">时间：${item.startTime} --- ${item.stopTime}</span>
                                <h3><a href="${pageContext.request.contextPath}#">${item.theme}</a></h3>
                                <p>类型：${item.type eq 1 ? "单选" : "多选"}</p>
                                <ul class="stuff">
                                    <li title="总投票数"><i class="icon-heart3"></i>${item.allVoteCount}</li>
                                    <li><a href="${pageContext.request.contextPath}/vote/show?id=${item.id}">查看详情<i class="icon-arrow-right22"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </C:forEach>
            </div>
        </div>
    </div>
<%@include file="foot.jsp"%>
<script type="text/javascript">

</script>
