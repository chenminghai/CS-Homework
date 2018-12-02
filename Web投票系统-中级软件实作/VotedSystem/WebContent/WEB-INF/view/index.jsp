<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
    <aside id="fh5co-hero">
      <div class="flexslider">
        <ul class="slides">
        <c:choose>
          <c:when test="${itemList!=NULL && fn:length(itemList) > 0}">
          <c:forEach items="${itemList}" var="item" varStatus="num">
            <li style="background-image: url(${pageContext.request.contextPath}/public/images/img_bg_${num.index + 1}.jpg);">
              <div class="overlay-gradient"></div>
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-6 col-md-offset-3 col-md-pull-3 slider-text">
                    <div class="slider-text-inner">
                      <h1>主题：${item.theme}</h1>
                      <h2>Get More
                        <a href="${pageContext.request.contextPath}/index/vote/list" title="投票">更多投票</a>
                      </h2>
                      <p>时间：${item.startTime}---${item.stopTime}</p>
                      <p>发起人：${item.userName}</p>
                      <p>
                        <c:choose>
                          <c:when test="${item.status eq 1}">
                            <span class="btn btn-primary btn-demo"></i>还未开始</span>
                          </c:when>
                          <c:when test="${item.status eq 2}">
                            <a class="btn btn-primary btn-demo" href="${pageContext.request.contextPath}/index/vote?id=${item.id}"></i> 点击投票</a>
                          </c:when>
                          <c:otherwise>
                            <span class="btn btn-primary btn-demo"></i>已经截止</span>
                          </c:otherwise>
                        </c:choose>
                        <a class="btn btn-primary btn-learn" href="${pageContext.request.contextPath}/vote/show?id=${item.id}">查看详情</a>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </c:forEach>
          </c:when>
          
          <c:otherwise>
            <li style="background-image: url(${pageContext.request.contextPath}/public/images/img_bg_${num.index + 1}.jpg);">
              <div class="overlay-gradient"></div>
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-6 col-md-offset-3 col-md-pull-3 slider-text">
                    <div class="slider-text-inner"><p>暂时还没有投票活动哦！</p></div>
                  </div>
                </div>
              </div>
            </li>
          </c:otherwise>
        </c:choose>
        </ul>
      </div>
    </aside>
</div><!-- END container-wrap -->
<%@include file="foot.jsp"%>

