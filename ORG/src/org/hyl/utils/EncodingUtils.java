package org.hyl.utils;

import java.io.UnsupportedEncodingException;
//jhkl
/**   
 * 常用的编码转换的工具方法集合
 * 
 * @author HuYongliang
 *
 */
public class EncodingUtils {
	/**
	 * 传入字符串s，返回以destEncoding编码的字符串
	 * 
	 * @param srcEncoding
	 *            s的原编码方式
	 * @param destEncoding
	 *            目标编码方式
	 * @param s
	 *            需要重新编码的字符串
	 * @return 以destEncoding编码的字符串
	 */
	public static String encoding(String srcEncoding, String destEncoding,
			String s) {
		try {
			return new String(s.getBytes(srcEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从gb2312编码到destEndcoding编码
	 * 
	 * @param destEndcoding
	 * @param s
	 * @return 对s以destEndcoding编码的字符串
	 */
	public static String fromGb2312(String destEndcoding, String s) {
		return encoding("gb2312", destEndcoding, s);
	}

	/**
	 * 从srcEncoding到UTF-8编码
	 * 
	 * @param srcEncoding
	 * @param s
	 * @return 对s以UTF-8编码的字符串
	 */
	public static String toUtf(String srcEncoding, String s) {
		return encoding(srcEncoding, "UTF-8", s);
	}

	/**
	 * 从gb2312到UTF-8
	 * 
	 * @param s
	 * @return 对s以UTF-8编码的字符串
	 */
	public static String toUtf(String s) {
		return encoding("gb2312", "UTF-8", s);
	}

}
