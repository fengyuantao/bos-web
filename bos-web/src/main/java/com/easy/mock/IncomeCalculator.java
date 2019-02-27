package com.easy.mock;

import com.easy.mock.exceptions.CalcMethodException;
import com.easy.mock.exceptions.PositionException;
import com.easy.mock.method.ICalcMethod;

public class IncomeCalculator {
	private ICalcMethod calcMethod;
	private Position position;
	public void setCalcMethod(ICalcMethod calcMethod) {
		this.calcMethod = calcMethod;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public double calc() {
		if(calcMethod == null) {
			throw new CalcMethodException("CalcMethodException Ϊ��");
		}
		if(position == null) {
			throw new PositionException("PositionException Ϊ��");
		}
		return calcMethod.calc(position);
	}

}
