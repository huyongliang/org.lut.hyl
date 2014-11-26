package org.hyl.fileupload;

/**
 * 
 * org.hyl.fileupload.FileUploader�ļ��ϴ����浽���̺�<br>
 * ���ڱ�������������Ϣ
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
	 * @return �����fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	@Deprecated
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return �ͻ��˵��ļ���
	 */
	public String getFileName() {
		return fileName;
	}

	@Deprecated
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return �洢�ڴ����е���ʵ�ļ���
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
