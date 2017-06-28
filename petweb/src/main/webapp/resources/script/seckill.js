//存放主要交互逻辑js代码
var seckill={
	//封装秒杀相关ajax的url
	url:{
		now:function(){
			return '/petweb/time/now';
		},
		exposer:function(seckillId){
			return '/petweb/'+seckillId+'/exposer';
		},
		execution:function(seckillId,md5){
			return '/petweb/'+seckillId+'/'+md5+'/execution';
		}
	},
	//验证手机号
	validatePhone:function(phone){
		if(phone&&phone.length==11&&!isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//显示秒杀按钮
	handlerSeckill:function(seckillId,node){
		//获取秒杀地址，控制显示逻辑，执行秒杀操作
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
		$.get(seckill.url.exposer(seckillId),{},function(result){
			if(result&&result['success']){
				var exposer=result['data'];
				if(exposer['exposed']){
					//开启秒杀
					var md5=exposer['md5'];
					var killUrl=seckill.url.execution(seckillId,md5);
					$('#killBtn').one('click',function(){
						//执行秒杀请求操作
						//1.先禁用按钮
						$(this).addClass('disabled');
						//2.发送请求
						$.post(killUrl,{},function(result){
							if(result&&result['success']){
								var killResult=result['data'];
								var state=killResult['state'];
								var stateInfo=killResult['stateInfo'];
								//3.显示秒杀结果
								node.html('<span class="label label-success">'+stateInfo+'</span>');
								alert(stateInfo);
							}
						});
					});
					node.show();
				}else{
					//未开启秒杀
					var now=exposer['now'];
					var start=exposer['start'];
					var end=exposer['end'];
					//重新进入计算时间逻辑
					seckill.countdown(seckillId,now,start,end);
				}
			}else{
				console.log('result:'+result);
			}


		});
	},
	//判断时间，是否进行秒杀
	countdown:function(seckillId,nowTime,startTime,endTime){
		var seckillBox=$('#seckill-box');
		if(nowTime>endTime){
			//秒杀结束
			seckillBox.html('秒杀结束！');
		}else if(nowTime<startTime){
			//秒杀没有开始
			var killTime=new Date(startTime+1000);
			seckillBox.countdown(killTime,function(event){
				//时间格式
				var format=event.strftime('秒杀倒计时：%d天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on('finish.countdown',function(){
				//获取秒杀地址，控制显示逻辑，执行秒杀操作
				seckill.handlerSeckill(seckillId,seckillBox);
			});
		}else{
			//秒杀进行中
			seckill.handlerSeckill(seckillId,seckillBox);
		}
	},
	//详情页秒杀逻辑
	detail:{
		//详情页初始化
		init:function(params){
			//手机验证和登陆，计算时间交互
			//在cookie中查找手机号
			var killPhone=$.cookie('killPhone');
			var startTime=parseInt(params['startTime']);
			var endTime=parseInt(params['endTime']);
			var seckillId=parseInt(params['seckillId']);
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//调出输入手机号窗口（登陆）
				var killPhoneModal=$('#killPhoneModal');
				killPhoneModal.modal({
					show:true,//显示窗口
					backdrop:'static',//禁止位置关闭
					keyboard:false//关闭键盘事件
				});
				$('#killPhoneBtn').click(function(){
					var inputPhone=$('#killPhoneKey').val();
					if(seckill.validatePhone(inputPhone)){
						//电话写入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/petweb'});
						//刷新页面
						window.location.reload();
					}else{
						//提示信息
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}
			//已经登陆
			//计时交互
			$.get(seckill.url.now(),{},function(result){
				if(result&&result['success']){
					var nowTime=result['data'];
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}else{
					console.log("result:"+result);
				}
			});
		}
	}
	
};


	