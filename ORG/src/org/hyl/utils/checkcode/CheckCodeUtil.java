package org.hyl.utils.checkcode;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyl.utils.checkcode.CheckCode.CODE_TYPE;
import org.hyl.utils.checkcode.impl.DefaultCheckCode;

public class CheckCodeUtil {
	public static void sendCheckCodeToClient(HttpServletRequest request,
			HttpServletResponse response, CheckCode code) {
		BufferedImage image = code.getCheckCode(request);
		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 将图像输出到输出流中
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
	 * 判断client传送的验证码是否正确
	 * 
	 * @param request
	 * @param clientAnswer
	 * @param checkCode
	 * @return
	 */
	public static boolean isValid(HttpServletRequest request,
			String clientAnswer, CheckCode checkCode) {
		return checkCode.isValid(request, clientAnswer);
	}

	public static boolean isValid(HttpServletRequest request,
			String clientAnswer) {
		return new DefaultCheckCode(CODE_TYPE.DIGIT).isValid(request,
				clientAnswer);
	}
}
