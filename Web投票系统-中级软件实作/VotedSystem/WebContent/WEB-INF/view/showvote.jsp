<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/pie/css/style.css">
<%@include file="head.jsp"%>
    <div class="container-wrap">
        <footer>
            <div class="row copyright" >
                <div class="col-md-12 text-center">
                    <big>查看投票</big>
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
                    <form action="${pageContext.request.contextPath}/admin/vote/update?id=${item.id}" method="post" id="vote_add_form">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">主题</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.theme}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">发起人</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.userName}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">发起时间</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.startTime}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">结束时间</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.stopTime}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">投票类型</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.type eq 1 ? "单选" : "多选"}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">投票类型</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.isStop eq 1 ? "已停止" : "已启用"}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">总投票数</label>
                                    <div class="col-sm-9">
                                        <p class="form-control">${item.allVoteCount}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="">
                                    <label for="" class="col-sm-3 control-label">投票详情</label>
                                    <div class="col-sm-9">
                                        <p style="border: 2px solid rgba(0, 0, 0, 0.1)">
                                            <c:forEach items="${optionList}" var="option" varStatus="num">
                                                ${num.index + 1}.${option.content}：共有${option.resultCount}票，占${option.percentage}% <br>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="">
                                    <c:forEach items="${optionList}" var="option" varStatus="num">
                                        <div class="skillbar" data-percent="${option.percentage}%">
                                            <div class="skillbar-title" style="background: #00a8ff;"><span>${option.content}</span></div>
                                            <div class="skillbar-bar" style="background: #00a8ff;"></div>
                                            <div class="skill-bar-percent">${option.percentage}%</div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-4 control-label"></label>
                                    <a href="javascript:history.go(-1)"><input type="button" value="返 回 " class="btn btn-primary btn-modify"></a>
                                	<a href="${pageContext.request.contextPath}/index/vote?id=${item.id}"><input type="button" value="点击投票 " class="btn btn-primary btn-modify"></a>
                                </div>
                            </div>
                        </div>
                        
                 <c:forEach items="${optionList}" var="option">
                  	<h3 class="col-md-12 text-center">候选人：${option.content}</h3>
                    <div class="row">
                    	<div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">性别</label>
                                <div class="col-sm-9">
                                    <p class="form-control">${option.sex}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">出生日期</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.birthday}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">年龄</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.age}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">民族</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.nation}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">政治面貌</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.political}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">家庭地址</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.address}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">联系方式</label>
                                <div class="col-sm-9">
                                	<p class="form-control">${option.telephone}</p>
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">推荐意见</label>
                                <div class="col-sm-9">
                                    <p class="form-control">${option.recommend}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                 </c:forEach>
               </form>
                </div>
            </div>
        </div>
    </div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script src="${pageContext.request.contextPath}/public/pie/js/index.js"></script>