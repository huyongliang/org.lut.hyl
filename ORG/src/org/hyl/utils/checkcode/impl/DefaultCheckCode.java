package org.hyl.utils.checkcode.impl;

import java.util.Random;

import org.hyl.utils.checkcode.CheckCode;

public class DefaultCheckCode extends CheckCode {

	private char[] codeSequence;
	private String rightAnswer;

	public DefaultCheckCode(CODE_TYPE codeType) {
		switch (codeType) {
		case DIGIT:
			this.codeSequence = "0123456789".toCharArray();
			break;
		case LETTER:
			this.codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
					.toCharArray();
			break;
		case DIGIT_LETTER:
			this.codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz23456789"
					.toCharArray();
			break;
		default:
			break;
		}
	}

	public DefaultCheckCode(String userCodeSequence) {
		if (userCodeSequence == null || userCodeSequence.equals(""))
			throw new RuntimeException("用于生产验证码的序列不合法");
		this.codeSequence = userCodeSequence.toCharArray();
	}

	public DefaultCheckCode(char[] codeSequence) {
		this.codeSequence = codeSequence;
	}

	@Override
	protected String getPrintString() {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		int len = this.codeSequence.length;
		for (int i = 0; i < this.codeCount; i++) {
			builder.append(this.codeSequence[random.nextInt(len)]);
		}
		this.rightAnswer = builder.toString();
		return builder.toString();
	}

	@Override
	protected String getRightAnswer() {
		if (this.rightAnswer == null || this.rightAnswer.equals(""))
			this.getPrintString();
		return this.rightAnswer;
	}

}
