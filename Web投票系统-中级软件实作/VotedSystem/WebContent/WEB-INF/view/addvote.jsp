<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<div class="container-wrap">
    <footer>
        <div class="row copyright" >
            <div class="col-md-12 text-center">
                <big>新增投票</big>
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
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">主题</label>
                                <div class="col-sm-9">
                                    <input type="text" name="theme" class="form-control" placeholder="">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">发起人</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" value="${user.realName}" placeholder="" disabled>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">发起时间</label>
                                <div class="col-sm-9">
                                    <input type="text" name="start_time" class="form-control" placeholder="如2018-10-30 15:07:02">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">结束时间</label>
                                <div class="col-sm-9">
                                    <input type="text" name="stop_time" class="form-control" placeholder="如2018-10-30 15:07:02">
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
                                        <option value="3">评分制</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        
                         <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">投票规则</label>
                                <div class="col-sm-9">
                                    <select name="is_waiver" class="form-control">
                                      <option value ="0" selected="selected">不能弃权</option>
						  <option value ="1">可以弃权</option>
						</select>
						<select name="is_oppose" class="form-control">
						  <option value ="0" selected="selected">不能反对</option>
						  <option value ="1">可以反对</option>
						</select>
						<input type="text" name="number" class="form-control" placeholder="多选或评分制的可选人数">
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label">候选人名单</label>
                                <div class="col-sm-9">
                                    <textarea rows="5" cols="7" name="option_content" class="form-control" placeholder="候选人名之间用按回车键（换行）隔开,如:&#13;&#10;陈铭海&#13;&#10;李世民"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="" class="col-sm-4 control-label"></label>
                                <input type="button" id="submit1" name="select" value="提 交" class="btn btn-primary btn-modify">
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
            var number = $('input[name=number]').val();
            var option_content = $('textarea').val();
            var type= $('select[name=type]').val();
            
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
            if(type !="1" && !number){
                alert('多选或评分制类型的投票的可选人数不能为空');
                return;
            }
            if(!option_content){
                alert('选项不能为空');
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
