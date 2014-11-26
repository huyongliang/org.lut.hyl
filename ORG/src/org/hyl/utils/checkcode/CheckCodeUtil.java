package org.hyl.utils.checkcode;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �����{@link CheckCode}��ʵ����Ĺ�����
 * 
 * @author HuYongliang
 *
 */
public class CheckCodeUtil {
	/**
	 * ���Ͳ���code��������֤�뵽response��Ӧ�Ŀͻ��ˣ�������֤�����session��
	 * 
	 * @param request
	 * @param response
	 * @param code
	 */
	public static void sendCheckCodeToClient(HttpServletRequest request,
			HttpServletResponse response, CheckCode code) {
		BufferedImage image = code.getCheckCode(request);
		// ��ֹͼ�񻺴�
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// ��ͼ��������������
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ж�client���͵���֤���Ƿ���ȷ
	 * 
	 * @param request
	 * @param clientAnswer
	 * @param checkCode
	 * @return
	 */
	public static boolean isValid(HttpServletRequest request,
			String clientAnswer, CheckCode checkCode) {
		return CheckCode.isValid(request, clientAnswer);
	}

	/**
	 * �ж�client���͵���֤���Ƿ���ȷ
	 * 
	 * @param request
	 * @param clientAnswer
	 * @return
	 */
	public static boolean isValid(HttpServletRequest request,
			String clientAnswer) {
		return CheckCode.isValid(request, clientAnswer);
	}
}
