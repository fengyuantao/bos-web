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
		
		// ����ICalcMethod�ӿڣ�����ģ�����
		calcMethodMock = EasyMock.createMock(ICalcMethod.class);
		// new һ�����㷽��
		incomeCalc = new IncomeCalculator();
	}
	
	@Test
	public void test1(){
		// expect ��������ϣ�����Ե���ģ������е�calc���������Ҳ���ʱ����Ϊboss, ����ֵ��7000����������
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andReturn(7000.00).times(2);
		
		EasyMock.expect(calcMethodMock.calc(Position.PROGRAMMER)).andReturn(5000.00);
		// ��ģ�����ط� 
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
		// �ж����ģ���������з����ǲ��Ƕ�ִ����
		EasyMock.verify(calcMethodMock);
		
		
		
	}
	// ���������쳣
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
	// �����׳��쳣
	@Test(expected = PositionException.class)
	public void test4(){
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andThrow(new PositionException("�������ڲ����쳣PositionException")).times(1);
		EasyMock.replay(calcMethodMock);
		
		incomeCalc.setCalcMethod(calcMethodMock);
		incomeCalc.setPosition(Position.BOSS);
		incomeCalc.calc();
	}
	
	@Test(expected = CalcMethodException.class)
	public void test5(){
		EasyMock.expect(calcMethodMock.calc(Position.BOSS)).andThrow(new PositionException("�������ڲ����쳣CalcMethodException")).times(1);
		EasyMock.replay(calcMethodMock);
		
		incomeCalc.setCalcMethod(calcMethodMock);
		incomeCalc.setPosition(Position.BOSS);
		incomeCalc.calc();
	}
}
