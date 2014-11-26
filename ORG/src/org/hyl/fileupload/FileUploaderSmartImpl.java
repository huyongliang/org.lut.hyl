package org.hyl.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyl.utils.FileUtils;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * FileUploader的实现类，依赖于<b>jspSmartUpload组件
 * 
 * @author HuYongliang
 * @see FileUploader
 */
public class FileUploaderSmartImpl extends FileUploader {

	private SmartUpload smartUpload;
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;

	public FileUploaderSmartImpl(ServletConfig servletConfig,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		this.smartUpload = new SmartUpload();
		try {
			smartUpload.initialize(servletConfig, this.httpServletRequest,
					this.httpServletResponse);
		} catch (ServletException e) {
			System.out.println("初始化失败");
			e.printStackTrace();
		}

	}

	/***
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#upload()
	 */
	@Override
	public int upload() throws ServletException, IOException {
		try {
			this.smartUpload.upload();
			return this.smartUpload.getFiles().getCount();
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/***
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#save()
	 */
	@Override
	public List<SavedResult> save() throws IOException {

		List<SavedResult> list = new ArrayList<SavedResult>();
		String path = new String();
		for (int i = 0; i < this.smartUpload.getFiles().getCount(); i++) {
			try {
				File f = this.smartUpload.getFiles().getFile(i);
				path = REAL_LATH + this.getDiskedFileName()
						+ FileUtils.getExtName(f.getFileName(), true);
				System.out.println("EEE:" + f.getFileExt());
				f.saveAs(path);
				System.out.println("ffff:" + path);
				SavedResult result = new SavedResult(f.getFieldName(),
						this.gbk2SpecifiedCharset(f.getFileName()), path);

				list.add(result);
			} catch (SmartUploadException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/***
	 * (non-Javadoc)
	 * 
	 * @see org.hyl.fileupload.FileUploader#getFormFieldParam(java.lang.String)
	 */
	@Override
	public String getFormFieldParam(String fieldName) {
		String s = this.smartUpload.getRequest().getParameter(fieldName);
		s = this.gbk2SpecifiedCharset(s);

		return s;
	}

	
	/*** (non-Javadoc)
	 * @see org.hyl.fileupload.FileUploader#getDiskedFileName()
	 * @return  IP+TimeStamp+Random(3)
	 */
	@Override
	public String getDiskedFileName() {
		return new FileUtils(this.httpServletRequest.getRemoteAddr())
				.getIpTimestampRandom(3);
	}
}
