package org.hyl.utils;

import java.io.UnsupportedEncodingException;
//jhkl
/**   
 * ���õı���ת���Ĺ��߷�������
 * 
 * @author HuYongliang
 *
 */
public class EncodingUtils {
	/**
	 * �����ַ���s��������destEncoding������ַ���
	 * 
	 * @param srcEncoding
	 *            s��ԭ���뷽ʽ
	 * @param destEncoding
	 *            Ŀ����뷽ʽ
	 * @param s
	 *            ��Ҫ���±�����ַ���
	 * @return ��destEncoding������ַ���
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
	 * ��gb2312���뵽destEndcoding����
	 * 
	 * @param destEndcoding
	 * @param s
	 * @return ��s��destEndcoding������ַ���
	 */
	public static String fromGb2312(String destEndcoding, String s) {
		return encoding("gb2312", destEndcoding, s);
	}

	/**
	 * ��srcEncoding��UTF-8����
	 * 
	 * @param srcEncoding
	 * @param s
	 * @return ��s��UTF-8������ַ���
	 */
	public static String toUtf(String srcEncoding, String s) {
		return encoding(srcEncoding, "UTF-8", s);
	}

	/**
	 * ��gb2312��UTF-8
	 * 
	 * @param s
	 * @return ��s��UTF-8������ַ���
	 */
	public static String toUtf(String s) {
		return encoding("gb2312", "UTF-8", s);
	}

}
