package com.onedob.service;

import com.onedob.dto.Exposer;
import com.onedob.dto.SeckillExecution;
import com.onedob.entity.Seckill1;
import com.onedob.exception.RepeatKillException;
import com.onedob.exception.SeckillCloseException;
import com.onedob.exception.SeckillException;

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
	List<Seckill1> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill1 getById(long seckillId);
	
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
