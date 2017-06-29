package com.sandra.Dao;
import com.sandra.dao.SeckillDao;
import com.sandra.entity.Seckill1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
		Seckill1 seckill=seckillDao.queryById(1000);
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() 
	{
		List<Seckill1> seckillList=seckillDao.queryAll(0, 100);
		for(Seckill1 s:seckillList)
		{
			System.out.println(s);
		}
	}

}
