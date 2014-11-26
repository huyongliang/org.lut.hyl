package org.hyl.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import org.hyl.utils.FileUtils;

/**
 * ��Ҫ�ṩproperties�����ļ�<b>file-uploader.properties</b>
 * 
 * @author HuYongliang
 * @see FileUploaderApacheImpl
 * @see FileUploaderSmartImpl
 */
public abstract class FileUploader {
	protected static String TEMP_DIR;// ��ʱ�ļ���
	protected static String REAL_LATH;// ���ϴ����ļ��洢������
	protected static String FILE_EXTS;// ��չ��
	protected static int FILE_MAX_SIZE;// �����ļ���С
	protected static int TOTAL_FILE_MAX_SIZE;// �ļ��ܴ�С
	protected static int THRESHOLD;// ��ֵ
	protected static String CHARACTER_ENCODING;// �ַ�����
	static {
		Properties properties = new Properties();
		InputStream in = FileUploader.class.getClassLoader()
				.getResourceAsStream("file-uploader.properties");
		try {
			properties.load(in);

			TEMP_DIR = properties.getProperty("file.temDirpath");
			REAL_LATH = properties.getProperty("file.absoluteDiskPath");
			FileUtils.makeDir(TEMP_DIR);
			FileUtils.makeDir(REAL_LATH);
			FILE_EXTS = properties.getProperty("file.exts");
			FILE_MAX_SIZE = Integer.parseInt(properties
					.getProperty("file.max.size"));
			TOTAL_FILE_MAX_SIZE = Integer.parseInt(properties
					.getProperty("file.total.max.size"));
			THRESHOLD = Integer.parseInt(properties.getProperty("threshold"));
			CHARACTER_ENCODING = properties.getProperty(
					"form.field.characterEncoding", "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @return �ϴ��ɹ����ļ�������
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract int upload() throws ServletException, IOException;

	/**
	 * �����ļ�������
	 * 
	 * @return ����SavedResult���������
	 * @throws IOException
	 */
	public abstract List<SavedResult> save() throws IOException;

	/**
	 * ��ȡ�ϴ��ļ��ı��ı�������Ե�ֵ
	 * 
	 * @param fieldName
	 * @return ��������Ե�ֵ
	 */
	public abstract String getFormFieldParam(String fieldName);

	/**
	 * @return ÿ���ϴ��ļ���������ʽ��Ĭ����ʱ���+��λ�����
	 */
	protected String getDiskedFileName() {
		return new FileUtils("").getIpTimestampRandom(3);
	};

	protected String gbk2SpecifiedCharset(String s) {
		try {
			return new String(s.getBytes("gbk"), CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
}
