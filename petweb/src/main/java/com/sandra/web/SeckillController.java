package com.sandra.web;

import java.util.Date;
import java.util.List;

import com.sandra.dto.Exposer;
import com.sandra.entity.Seckill;
import com.sandra.enums.SeckillStatEnum;
import com.sandra.exception.SeckillCloseException;
import com.sandra.exception.SeckillException;
import com.sandra.service.SeckillService;
import com.sandra.dto.SeckillExecution;
import com.sandra.dto.SeckillResult;
import com.sandra.exception.RepeatKillException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Peter  2016-9-3下午5:02:48
 *
 */
@Controller
@RequestMapping("/")//url:/项目/模块/资源/{id}/细分  seckill/list
public class SeckillController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String List(Model model){
		//获取列表页
		List<Seckill> list=seckillService.getSeckillList();
		model.addAttribute("list",list);
		//List.jsp+model=ModelAndView
		return "list";//  /WEB-INF/jsp/"list".jsp
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		Seckill seckill=seckillService.getById(seckillId);
		if(seckill==null){
			return "forward:seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	//ajax json
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.GET,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer=seckillService.exportSeckillUrl(seckillId);
			result=new SeckillResult<Exposer>(true,exposer);
		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
			result=new SeckillResult<Exposer>(false,e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/{seckillId}/{md5}/execution",
			method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
		@PathVariable("seckillId")Long seckillId,
		@PathVariable("md5")String md5,
		@CookieValue(value="killPhone",required=false)Long phone){
		if(phone==null){
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
//		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution=seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e1) {
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);
		}catch(SeckillCloseException e2){
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		}catch(SeckillException e){
			SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(){
		Date now=new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
	
	
}
