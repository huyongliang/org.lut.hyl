package org.hyl.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import org.hyl.utils.FileUtils;

/**
 * 需要提供properties配置文件<b>file-uploader.properties</b>
 * 
 * @author HuYongliang
 * @see FileUploaderApacheImpl
 * @see FileUploaderSmartImpl
 */
public abstract class FileUploader {
	protected static String TEMP_DIR;// 临时文件夹
	protected static String REAL_LATH;// 将上传的文件存储到哪里
	protected static String FILE_EXTS;// 扩展名
	protected static int FILE_MAX_SIZE;// 单个文件大小
	protected static int TOTAL_FILE_MAX_SIZE;// 文件总大小
	protected static int THRESHOLD;// 阈值
	protected static String CHARACTER_ENCODING;// 字符编码
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
	 * 上传文件
	 * 
	 * @return 上传成功的文件的数量
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract int upload() throws ServletException, IOException;

	/**
	 * 保存文件到磁盘
	 * 
	 * @return 返回SavedResult对象的链表
	 * @throws IOException
	 */
	public abstract List<SavedResult> save() throws IOException;

	/**
	 * 获取上传文件的表单的表单域的属性的值
	 * 
	 * @param fieldName
	 * @return 表单域的属性的值
	 */
	public abstract String getFormFieldParam(String fieldName);

	/**
	 * @return 每个上传文件的命名方式，默认是时间戳+三位随机数
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
