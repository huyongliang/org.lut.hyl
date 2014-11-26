package org.hyl.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hyl.utils.FileUtils;

/**
 * FileUploader的实现类，依赖于Apache的<b>commons-fileupload.jar</b>和<b>commons-io.jar</b
 * >组件
 * 
 * @author HuYongliang
 * @see FileUploader
 */
public class FileUploaderApacheImpl extends FileUploader {

	private ServletFileUpload servletFileUpload;
	private HttpServletRequest httpServletRequest;
	private List<FileItem> fileItems;
	private Map<String, String> params = new HashMap<String, String>();

	public FileUploaderApacheImpl(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
		this.initServletFileUpload();
	}

	private void initFormFields() throws UnsupportedEncodingException {
		Iterator<FileItem> it = this.fileItems.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();
			if (item.isFormField())
				this.params.put(item.getFieldName(),
						item.getString(CHARACTER_ENCODING));
		}
	}

	private void initServletFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD);
		File file = new File(TEMP_DIR);
		factory.setRepository(file);

		this.servletFileUpload = new ServletFileUpload(factory);
		this.servletFileUpload.setSizeMax(FILE_MAX_SIZE);
		this.servletFileUpload.setFileSizeMax(TOTAL_FILE_MAX_SIZE);
		this.servletFileUpload.setHeaderEncoding(CHARACTER_ENCODING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#upload()
	 */
	@Override
	public int upload() throws ServletException, IOException {
		try {
			this.fileItems = this.servletFileUpload
					.parseRequest(this.httpServletRequest);
			this.initFormFields();
			return this.fileItems.size() - this.params.size();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#save()
	 */
	@Override
	public List<SavedResult> save() throws IOException {
		List<SavedResult> list = new ArrayList<SavedResult>();
		String path = new String();
		Iterator<FileItem> it = this.fileItems.iterator();

		while (it.hasNext()) {
			FileItem item = it.next();
			path = REAL_LATH + this.getDiskedFileName()
					+ FileUtils.getExtName(item.getName(), true);
			if (!item.isFormField()) {
				this.writeToDisk(item, path);
				SavedResult result = new SavedResult(item.getFieldName(),
						item.getName(), path);
				list.add(result);
			}
		}
		FileUtils.deleteAllFiles(TEMP_DIR);
		return list;
	}

	protected void writeToDisk(FileItem item, String path) {
		OutputStream out = null;
		InputStream in = null;
		try {
			in = item.getInputStream();
			out = new FileOutputStream(path);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#getFormFieldParam(java.lang.String)
	 */
	@Override
	public String getFormFieldParam(String fieldName) {

		return this.params.get(fieldName);
	}

	/***
	 * @see org.hyl.fileupload.FileUploader#getDiskedFileName()
	 * @return IP+TimeStamp+Random(3)
	 */
	@Override
	public String getDiskedFileName() {
		String fileName = new FileUtils(this.httpServletRequest.getRemoteAddr())
				.getIpTimestampRandom(3);
		return fileName;
	}

}
