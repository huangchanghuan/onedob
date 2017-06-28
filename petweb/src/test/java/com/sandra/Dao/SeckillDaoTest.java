package com.sandra.Dao;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.sandra.dao.SeckillDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.sandra.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckillDao;

	@Test
	public void testReduceNumber() 
	{
		Date killTime=new Date();
		int reduceInt=seckillDao.reduceNumber(1000, killTime);
		System.out.println(reduceInt);
	}

	@Test
	public void testQueryById() {
		Seckill seckill=seckillDao.queryById(1000);
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() 
	{
		List<Seckill> seckillList=seckillDao.queryAll(0, 100);
		for(Seckill s:seckillList)
		{
			System.out.println(s);
		}
	}

}
