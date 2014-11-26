package org.hyl.utils.checkcode.impl;

import java.util.Random;

import org.hyl.utils.checkcode.CheckCode;

/**
 * CheckCode的简单实现类，提供三种选项{DIGIT,LETTER,DIGIT_LETTER}
 * 
 * @author HuYongliang
 * @see CheckCode
 */
public class DefaultCheckCode extends CheckCode {

	private char[] codeSequence;
	private String rightAnswer;

	/**
	 * @param codeType
	 *            {@link CODE_TYPE}类型的验证码类型
	 */
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

	/**
	 * @param userCodeSequence
	 *            用户自定义的用于产生验证码的字符序列
	 */
	public DefaultCheckCode(String userCodeSequence) {
		if (userCodeSequence == null || userCodeSequence.equals(""))
			throw new RuntimeException("用于生产验证码的序列不合法");
		this.codeSequence = userCodeSequence.toCharArray();
	}

	/**
	 * @param codeSequence
	 *            用户自定义的用于产生验证码的字符序列
	 */
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
