<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/pie/css/style.css">
<%@include file="head.jsp"%>
    <div class="container-wrap">
        <footer>
            <div class="row copyright" >
                <div class="col-md-12 text-center">
                    <big>温馨提示</big>
                </div>
            </div>
        </footer>
    </div><!-- END container-wrap -->
    <div class="container-wrap">
        <div id="fh5co-contact">
            <div class="row">
                <div class="col-md-2 col-md-push-1 animate-box">
                </div>
                <div class="col-md-12 text-center">
                    <h1 style="color:red">${prompt }</h1>
                </div>
                <div class="col-md-12 text-center">
                    <p>
                    	将在&nbsp<span id="trunTo" style="color:red">5</span>&nbsp秒后跳转到<span>${page}</span>页面,您也可以点击&nbsp
                    	<a style="color:red" href="#" onclick="cancel()">取消</a>&nbsp跳转
                    </p>
                </div>
            </div>
        </div>
    </div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script src="${pageContext.request.contextPath}/public/pie/js/index.js"></script>
<script type="text/javascript">
	var time;
	onload=function(){
		time=setInterval(go, 1000);
	};
	var p='${page}';
	var mylink = '${pageContext.request.contextPath}/index';
	if(p=="登录"){
		mylink ='${pageContext.request.contextPath}/admin/user/login';
	}
	var x=document.getElementById("trunTo").innerHTML; //利用了全局变量来执行
	function cancel() 
	{ 
		mylink='#';
		window.clearInterval(time); 
	} 
	function go(){
		x--;
		if(x>=0){
		document.getElementById("trunTo").innerHTML=x;  //每次设置的x的值都不一样了。
		}else{
			window.location.href = mylink;
		}
	} 
</script>