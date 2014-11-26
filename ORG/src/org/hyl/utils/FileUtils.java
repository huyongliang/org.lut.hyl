package org.hyl.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @author HuYongliang
 * @version 1.0
 */
public class FileUtils {

	private String ip;

	public FileUtils() {
		super();
	}

	public FileUtils(String ip) {
		super();
		this.ip = ip;
	}

	/**
	 * ����ָ����datePattern����ʱ���
	 * 
	 * @param datePattern
	 *            SimpleDateFormatʹ�õ�������ʽ
	 * @return ʱ���
	 */
	public static String getTimeStamp(String datePattern) {
		String temp = null;
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		temp = sdf.format(new java.util.Date());
		return temp;
	}

	/**
	 * @see #getTimeStamp(String)
	 * @return ʱ���:getTimeStamp("yyyyMMddHHmmssSSS");
	 */
	public static String getTimeStamp() {
		return getTimeStamp("yyyyMMddHHmmssSSS");
	}

	/**
	 * ����IP��ַ+ʱ���+randomCountλ�������ɵ��ַ���
	 * 
	 * @param randomCount
	 *            �������λ�� ��randomCount<0����randomCount=3 ��randomCount==0�����������������
	 * @return IP��ַ+ʱ���+randomCountλ�������ɵ��ַ���
	 */
	public String getIpTimestampRandom(int randomCount) {
		StringBuilder sb = new StringBuilder();
		if (this.ip != null) {
			if (this.ip.contains(".")) {
				String str[] = this.ip.split("\\.");
				for (String s : str) {
					sb.append(fillZero(s, 3));
				}
			}
		}
		sb.append(getTimeStamp());
		Random r = new Random();
		int count = randomCount >= 0 ? randomCount : 3;

		for (int i = 0; i < count; i++)
			sb.append(r.nextInt(10));
		return sb.toString();
	}

	public static String fillZero(String str, int len) {
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		while (sb.length() < len)
			sb.insert(0, '0');
		return sb.toString();
	}

	/**
	 * ɾ���ļ���
	 * 
	 * @param folderPath
	 *            ָ�����ļ�����·��
	 */
	public static void deleteFolder(String folderPath) {
		deleteAllFiles(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * 
	 * ɾ��ָ���ļ����������ļ����ļ��� <br>
	 * 
	 * @param folderPath
	 *            ָ�����ļ��е���������·��
	 * @return false---�����쳣��<code>folderPath</code>��û���ļ�(�ļ���)<br>
	 *         true---ɾ���ɹ�
	 */
	public static boolean deleteAllFiles(String folderPath) {

		boolean flag = false;
		java.io.File file = new File(folderPath);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}

		String[] tempList = file.list();
		java.io.File tempFile = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(java.io.File.separator))
				tempFile = new File(folderPath + tempList[i]);
			else
				tempFile = new File(folderPath + File.separator + tempList[i]);

			if (tempFile.isFile())
				tempFile.delete();

			if (tempFile.isDirectory()) {
				deleteAllFiles(folderPath + File.separator + tempList[i]);
				deleteFolder(folderPath + File.separator + tempList[i]);
				flag = true;
			}

		}
		return flag;
	}

	/**
	 * ��ȡ��չ�� <br>
	 * ����:
	 * 
	 * <pre>
	 * FileHelper.getExtName(&quot;data.txt&quot;, false);// txt
	 * FileHelper.getExtName(&quot;data.txt&quot;, true);// .txt
	 * FileHelper.getExtName(&quot;data&quot;, true);// null
	 * FileHelper.getExtName(&quot;&quot;, true);// null
	 * FileHelper.getExtName(null, true);// null
	 * </pre>
	 * 
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param withDot
	 *            �Ƿ񷵻���չ��ǰ�ĵ�
	 * @return �ļ�����չ��<br>
	 *         ����<code>null<code> ���fileNameΪnull��""����fileName������(.)
	 */
	public static String getExtName(String fileName, boolean withDot) {
		if (fileName == null || "".equals(fileName))
			return null;
		if (fileName.indexOf(".") == -1)
			return null;
		if (withDot)
			return fileName.substring(fileName.lastIndexOf("."));
		else
			return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * ����ָ����dirName�����ļ���
	 * 
	 * @param dirName
	 *            �ļ���·��
	 */
	public static void makeDir(String dirName) {
		File file = new File(dirName);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}
}
