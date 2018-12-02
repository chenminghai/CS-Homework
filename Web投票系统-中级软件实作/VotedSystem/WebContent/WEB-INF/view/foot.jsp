<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-wrap">
    <footer>
        <div class="row copyright" >
            <div class="col-md-12 text-center">
                <p>
                    <big class="block">&copy; 2018 SCNU 西三318 </big>
                </p>
            </div>
        </div>
    </footer>
</div><!-- END container-wrap -->
</div>

<div class="gototop js-top">
    <a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
</div>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/public/js/jquery.min.js"></script>
<!-- jQuery Easing -->
<script src="${pageContext.request.contextPath}/public/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/public/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="${pageContext.request.contextPath}/public/js/jquery.waypoints.min.js"></script>
<!-- Flexslider -->
<script src="${pageContext.request.contextPath}/public/js/jquery.flexslider-min.js"></script>
<!-- Magnific Popup -->
<script src="${pageContext.request.contextPath}/public/js/jquery.magnific-popup.min.js"></script>
<script src="${pageContext.request.contextPath}/public/js/magnific-popup-options.js"></script>
<!-- Counters -->
<script src="${pageContext.request.contextPath}/public/js/jquery.countTo.js"></script>
<!-- Main -->
<script src="${pageContext.request.contextPath}/public/js/main.js"></script>

</body>
</html>
<script>
	$(".top-menu li").eq(${active - 1}).addClass("active");
</script>