<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
    <div class="container-wrap">
        <footer>
            <div class="row copyright" >
                <div class="col-md-12 text-center">
                    <big>修改投票</big>
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
                                        <input type="text" name="theme" value="${item.theme}" class="form-control" placeholder="">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">发起人</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" value="${user.username}" placeholder="" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">发起时间</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="start_time" value="${item.startTime}" class="form-control" placeholder="如2018-10-30 15:07:02">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">结束时间</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="stop_time" value="${item.stopTime}" class="form-control" placeholder="如2018-10-30 15:07:02">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">投票类型</label>
                                    <div class="col-sm-9">
                                        <select name="type" id="" class="form-control">
                                            <option value="1">单选</option>
                                            <option value="2">多选</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-3 control-label">可选人数</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="number" value="${item.number}" class="form-control" placeholder="如：1">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="" class="col-sm-4 control-label"></label>
                                    <input type="button" id="submit1" value="提 交" class="btn btn-primary btn-modify">
                                    <a href="${pageContext.request.contextPath}/admin"><input type="button" value="返 回 " class="btn btn-primary btn-modify"></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div><!-- END container-wrap -->
<%@include file="foot.jsp"%>
<script type="text/javascript">
        $('#submit1').click(function () {
            var date = /^(\d{4})-(\d{2})-(\d{2})\s+(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
            var theme = $('input[name=theme]').val();
            var start_time = $('input[name=start_time]').val();
            var stop_time = $('input[name=stop_time]').val();
            var option_content = $('textarea').val();
            if(!theme || (theme.length > 255)){
                alert('主题不能为空且长度不能超过255个字符');
                return;
            }
            if(!start_time){
                alert('发起时间不能为空');
                return;
            }
            if(!date.test(start_time)){
                alert('发起时间要符合日期格式');
                return;
            }
            if(!stop_time){
                alert('结束时间不能为空');
                return;
            }
            if(!date.test(stop_time)){
                alert('结束时间要符合日期格式');
                return;
            }
            var start_date = new Date(start_time);
            var stop_date  = new Date(stop_time);
            if (stop_date.getTime() < start_date.getTime()){
                alert('结束时间要比开始时间大');
                return;
            }
            document.getElementById('vote_add_form').submit();
        });
</script>
