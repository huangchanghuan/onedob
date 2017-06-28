package com.sandra.service;

import com.sandra.dto.Exposer;
import com.sandra.dto.SeckillExecution;
import com.sandra.entity.Seckill;
import com.sandra.exception.RepeatKillException;
import com.sandra.exception.SeckillCloseException;
import com.sandra.exception.SeckillException;

import java.util.List;
/**
 * 
 * @author Peter
 *
 */
public interface SeckillService {
	
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启是输出秒杀接口地址，
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param userPhone
	 * @param md5  
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
		throws SeckillException,SeckillCloseException,RepeatKillException;

}
