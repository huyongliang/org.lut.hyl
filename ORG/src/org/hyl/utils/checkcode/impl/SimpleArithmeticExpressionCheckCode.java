package org.hyl.utils.checkcode.impl;

import java.util.Random;

import org.hyl.utils.checkcode.CheckCode;

public class SimpleArithmeticExpressionCheckCode extends CheckCode {
	private String printString;
	private String rightAnswer;

	private int num1;
	private int num2;
	private char op;

	public SimpleArithmeticExpressionCheckCode() {
		this.initOperationParams();
		this.printString = new StringBuilder().append(num1).append(op)
				.append(num2).append("=?").toString();
		this.rightAnswer = this.executeCalculte() + "";
	}

	private void initOperationParams() {
		Random random = new Random();
		this.op = (new char[] { 'x', '-', '+', 'X' })[random.nextInt(4)];
		this.num1 = random.nextInt(10);
		this.num2 = random.nextInt(10);
	}

	private int executeCalculte() {
		switch (this.op) {
		case '+':
			return this.num1 + this.num2;
		case '-':
			return this.num1 - this.num2;
		case 'x':
		case 'X':
			return this.num1 * this.num2;
		default:
			return 0;
		}
	}

	@Override
	protected String getPrintString() {
		return this.printString;
	}

	@Override
	protected String getRightAnswer() {
		return rightAnswer;
	}

}
