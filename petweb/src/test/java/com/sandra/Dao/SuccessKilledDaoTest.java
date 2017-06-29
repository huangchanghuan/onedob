package com.sandra.Dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.sandra.dao.SuccessKilledDao;
import com.sandra.entity.SuccessKilled1;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
//	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() 
	{
		successKilledDao.insertSuccessKilled(1001L, 13189652012L);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled1 successKilled=successKilledDao.queryByIdWithSeckill(1000L,13189652012L);
		System.out.println("===="+successKilled);
	}

}
