package com.easymock.test;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.easy.mock.IncomeCalculator;
import com.easy.mock.Position;
import com.easy.mock.exceptions.CalcMethodException;
import com.easy.mock.exceptions.PositionException;
import com.easy.mock.method.ICalcMethod;

public class PositionTest {

	private ICalcMethod calcMethodMock;
	private IncomeCalculator incomeCalc;
	
	@Before
	public void setUp() {
		
		// 根据ICalcMethod接口，创建模拟对象
		calcMethodMock = EasyMock.createMock(ICalcMethod.class);
		// new 一个计算方法
		incomeCalc = new IncomeCalculator();
	}
	
	@Test
	public void test1(){
		// expect 表明我们希望测试的是模拟对象中的calc方法，而且测试时参数为boss, 返回值是7000，运行两次
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andReturn(7000.00).times(2);
		
		EasyMock.expect(calcMethodMock.calc(Position.PROGRAMMER)).andReturn(5000.00);
		// 对模拟对象回放 
		EasyMock.replay(calcMethodMock);
		incomeCalc.setCalcMethod(calcMethodMock);
		
		try {
			incomeCalc.calc();
		} catch (Exception e) {
			
		}
		incomeCalc.setPosition(Position.BOSS);
		assertEquals(7000.0,incomeCalc.calc(),0);
		assertEquals(7000.0,incomeCalc.calc(),0);
		incomeCalc.setPosition(Position.PROGRAMMER);
		assertEquals(5000.0,incomeCalc.calc(),0);
		incomeCalc.setPosition(Position.MAMAGER);
		// 判断这个模拟对象的所有方法是不是都执行了
		EasyMock.verify(calcMethodMock);
		
		
		
	}
	// 测试两个异常
	@Test(expected = PositionException.class)
	public void test2(){
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andReturn(7000.00);
		EasyMock.replay(calcMethodMock);
		incomeCalc.setCalcMethod(calcMethodMock);
		incomeCalc.calc();
	}
	@Test(expected = CalcMethodException.class)
	public void test3(){
		incomeCalc.setPosition(Position.BOSS);
		incomeCalc.calc();
	}
	// 测试抛出异常
	@Test(expected = PositionException.class)
	public void test4(){
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andThrow(new PositionException("我现在在测试异常PositionException")).times(1);
		EasyMock.replay(calcMethodMock);
		
		incomeCalc.setCalcMethod(calcMethodMock);
		incomeCalc.setPosition(Position.BOSS);
		incomeCalc.calc();
	}
	
	@Test(expected = CalcMethodException.class)
	public void test5(){
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andThrow(new PositionException("我现在在测试异常CalcMethodException")).times(1);
		EasyMock.replay(calcMethodMock);
		
		incomeCalc.setCalcMethod(calcMethodMock);
		incomeCalc.setPosition(Position.BOSS);
		incomeCalc.calc();
	}
}
