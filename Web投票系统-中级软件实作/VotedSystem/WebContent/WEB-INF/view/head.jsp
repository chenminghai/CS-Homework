<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>在线投票网站</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Free HTML5 Website Template by freehtml5.co" />
    <meta name="keywords" content="free website templates, free html5, free template, free bootstrap, free website template, html5, css3, mobile first, responsive" />

    <!-- Facebook and Twitter integration -->
    <meta property="og:title" content=""/>
    <meta property="og:image" content=""/>
    <meta property="og:url" content=""/>
    <meta property="og:site_name" content=""/>
    <meta property="og:description" content=""/>
    <meta name="twitter:title" content="" />
    <meta name="twitter:image" content="" />
    <meta name="twitter:url" content="" />
    <meta name="twitter:card" content="" />

    <link href="https://fonts.googleapis.com/css?family=Oxygen:300,400" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700" rel="stylesheet">

    <!-- Animate.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/icomoon.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/bootstrap.css">
	<!-- BootstrapValidator  -->
	<script src="${pageContext.request.contextPath}/public/js/bootstrapValidator.min.js"></script>
	<link href="${pageContext.request.contextPath}/public/css/bootstrapValidator.min.css" rel="stylesheet" />
	
    <!-- Magnific Popup -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/magnific-popup.css">

    <!-- Flexslider  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/flexslider.css">

    <!-- Theme style  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/style.css">

    <!-- Modernizr JS -->
    <script src="${pageContext.request.contextPath}/public/js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/public/js/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<div class="fh5co-loader"></div>

<div id="page">
    <nav class="fh5co-nav" role="navigation">
        <div class="container-wrap">
            <div class="top-menu">
                <div class="row">
                    <div class="col-xs-3">
                        <div id="fh5co-logo"><a href="${pageContext.request.contextPath}index.jsp">SCNU在线投票网站</a></div>
                    </div>
                    <div class="col-xs-9 text-right menu-1">
                        <ul style="">
                            <form action="${pageContext.request.contextPath}/index/vote/search" method="post">
                                <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
                                <li><a href="${pageContext.request.contextPath}/index/vote/list">投票</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/user/login">登录</a></li>
                                <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin">后台</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/user/update">我的</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/user/logout" onclick="if (!confirm('确定退出？')) return false;">退出</a></li>
                                
                                <li>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</li>
                                <li>
                                    <button class="pull-right btn-primary  btn-sm" type="submit">搜索</button>
                                    <input type="text" name="content" class="pull-right" style="height: 30px">
                                </li>
                            </form>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </nav>