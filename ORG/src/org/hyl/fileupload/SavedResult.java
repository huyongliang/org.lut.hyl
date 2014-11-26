package org.hyl.fileupload;

/**
 * 
 * org.hyl.fileupload.FileUploader文件上传保存到磁盘后<br>
 * 对于保存结果的描述信息
 * 
 * @author HuYongliang
 *
 */
public class SavedResult {
	private String fieldName;
	private String fileName;
	private String diskedFilePath;

	public SavedResult() {
		super();
	}

	public SavedResult(String fieldName, String fileName, String diskedFilePath) {
		super();
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.diskedFilePath = diskedFilePath;
	}

	/**
	 * @return 表单域的fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	@Deprecated
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return 客户端的文件名
	 */
	public String getFileName() {
		return fileName;
	}

	@Deprecated
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return 存储于磁盘中的真实文件名
	 */
	public String getDiskedFilePath() {
		return diskedFilePath;
	}

	@Deprecated
	public void setDiskedFilePath(String diskedFilePath) {
		this.diskedFilePath = diskedFilePath;
	}

	@Override
	public String toString() {
		return "SaveResult [fieldName=" + fieldName + ", fileName=" + fileName
				+ ", diskedFilePath=" + diskedFilePath + "]";
	}

}
