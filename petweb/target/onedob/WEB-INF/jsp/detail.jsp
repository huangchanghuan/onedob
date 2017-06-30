<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
   <head>
    <title>秒杀详细页</title>
	<%@include file="common/head.jsp" %>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 //cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js-->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script src="<%=basePath%>resources/script/seckill.js"></script>
   </head>
   <body>
       <div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>${seckill.name }</h2>
			</div>
			<div class="panel-body text-center">
				<h2 class="text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time" id="seckill-time"></span>
					<!-- 展示倒计时 -->
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>      
      </div>
	  <!-- 登陆弹出层，输入电话 -->
	  <div id="killPhoneModal" class="modal fade">
	  	<div class="modal-dialog">
	  		<div class="modal-content">
	  			<div class="modal-header">
	  				<h3 class="modal-title text-center">
	  					<span class="glyphicon glyphicon-phone"></span>秒杀电话：
	  				</h3>
	  			</div>
	  			<div class="modal-body">
	  				<div class="row">
	  					<div class="col-xs-8 col-xs-offset-2">
	  						<input type="text" name="killPhone" id="killPhoneKey"
	  						placeholder="填写手机号" class="form-control">
	  					</div>
	  				</div>
	  			</div>
	  			<div class="modal-footer">
	  			<!-- 验证信息 -->
	  			<span id="killPhoneMessage" class="glyphicon"></span>
	  			<button type="button" id="killPhoneBtn" class="btn btn-success">
	  				<span class="glyphicon glyphicon-phone"></span>
	  				Submit
	  			</button>
	  			</div>
	  		</div>
	  	</div>
	  </div>
   </body>
   <script type="text/javascript">
		$(function(){
   		//使用el表达式传入参数
   		seckill.detail.init({
   			seckillId:'${seckill.seckillId}',
   			startTime:'${seckill.startTime.time}',
   			endTime:'${seckill.endTime.time}'
   		});
   	});
   
   
   </script>
</html>