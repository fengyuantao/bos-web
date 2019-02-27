package com.easy.mock.method;

import com.easy.mock.Position;

/*
 * 工资的简单实现
 * 
 * */

public class CalcMethod implements ICalcMethod {

	
	public double calc(Position position) {
		double d = 0;
		switch(position) {
		
		case BOSS:
			d = 7000.01;
			break;
		case MAMAGER:
			d = 5000.00;
			break;
		case PROGRAMMER:
			d = 3000.00;
			break;
		}
		return d;
	}

}
