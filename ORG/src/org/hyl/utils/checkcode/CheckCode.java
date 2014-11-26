package org.hyl.utils.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码的基类<br>
 * 使用时，client发送的验证码的name必须和本类的{@link CheckCode.CHECK_CODE_KEY}字段的值相符
 * 
 * @author HuYongliang
 *
 */
public abstract class CheckCode {
	public static String CHECK_CODE_KEY = "CHECK_CODE_KEY";

	protected int width = 152;
	protected int height = 40;
	protected int codeCount = 4;

	// 验证码字体的高度
	protected int fontHeight = 4;

	// 验证码中的单个字符基线. 即：验证码中的单个字符位于验证码图形左上角的 (codeX, codeY) 位置处
	protected int codeX = 0;
	protected int codeY = 0;

	public CheckCode() {
		this.width = 152;
		this.height = 40;
		this.init();
	}

	private void init() {
		fontHeight = height - 2;
		codeX = width / (codeCount + 2);
		codeY = height - 4;
	}

	protected abstract String getPrintString();

	protected abstract String getRightAnswer();

	public BufferedImage getCheckCode(HttpServletRequest request) {
		this.init();
		char[] codeSequence = this.getPrintString().toCharArray();
		int len = codeSequence.length;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);

		this.renderImg(image);

		Graphics2D graphics = image.createGraphics();
		this.setDefaultStyleOfCode(graphics);

		for (int i = 0; i < len; i++) {
			graphics.setColor(getRandomColor());
			graphics.drawString(String.valueOf(codeSequence[i]), (i + 1)
					* codeX, codeY);
		}
		String answer = this.getRightAnswer();

		request.getSession().setAttribute(CHECK_CODE_KEY, answer);

		return image;
	}

	protected Color getRandomColor() {
		Random random = new Random();

		Color c = new Color(random.nextInt(154) + 50, random.nextInt(154) + 50,
				random.nextInt(154) + 50);
		return c;
	}

	protected void renderImg(BufferedImage image) {
		Graphics2D graphics = image.createGraphics();

		// 设置一个颜色, 使 Graphics2D 对象的后续图形使用这个颜色
		graphics.setColor(Color.WHITE);

		// 填充一个指定的矩形: x - 要填充矩形的 x 坐标; y - 要填充矩形的 y 坐标; width - 要填充矩形的宽度; height
		// - 要填充矩形的高度
		graphics.fillRect(0, 0, width, height);

		// 创建一个 Font 对象: name - 字体名称; style - Font 的样式常量; size - Font 的点大小
		Font font = null;
		font = new Font("", Font.BOLD, fontHeight);
		// 使 Graphics2D 对象的后续图形使用此字体
		graphics.setFont(font);

		graphics.setColor(Color.BLACK);

		// 绘制指定矩形的边框, 绘制出的矩形将比构件宽一个也高一个像素
		graphics.drawRect(0, 0, width - 1, height - 1);

		// 随机产生 15 条干扰线, 使图像中的认证码不易被其它程序探测到
		Random random = null;
		random = new Random();
		graphics.setColor(Color.GREEN);
		for (int i = 0; i < 15; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(20);
			int y1 = random.nextInt(20);
			graphics.drawLine(x, y, x + x1, y + y1);
		}
	}

	protected void setDefaultStyleOfCode(Graphics2D graphics) {
		Font font = null;
		font = new Font("", Font.BOLD, fontHeight);
		// 使 Graphics2D 对象的后续图形使用此字体
		graphics.setFont(font);
	}

	/**
	 * 基类缺省提供的验证码的类型选项
	 * 
	 * @author HuYongliang
	 *
	 */
	public enum CODE_TYPE {
		DIGIT, LETTER, DIGIT_LETTER
	}

	/**
	 * @param request
	 * @param clientAnswer
	 *            client发送的验证码
	 * @return client发送的验证码是否正确
	 */
	public static boolean isValid(HttpServletRequest request,
			String clientAnswer) {
		if (clientAnswer == null || clientAnswer.equals(""))
			return false;
		HttpSession sessioin = request.getSession(false);
		if (sessioin == null)
			return false;

		String code = (String) sessioin.getAttribute(CHECK_CODE_KEY);
		if (code == null || code == "")
			return false;
		if (code.equalsIgnoreCase(clientAnswer)) {
			sessioin.removeAttribute(CHECK_CODE_KEY);
			return true;
		}
		return false;
	}
}
