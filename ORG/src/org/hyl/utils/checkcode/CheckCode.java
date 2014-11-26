package org.hyl.utils.checkcode; 


//fdsakfjkadsk

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class CheckCode {
	protected String CHECK_CODE_KEY = "CHECK_CODE_KEY";

	protected int width = 152;
	protected int height = 40;
	protected int codeCount = 4;

	// 验证码字体的高度
	protected int fontHeight = 4;

	// 验证码中的单个字符基线. 即：验证码中的单个字符位于验证码图形左上角的 (codeX, codeY) 位置处
	protected int codeX = 0;
	protected int codeY = 0;

	public CheckCode(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.init();
	}

	public CheckCode() {
		this(152, 40);
		this.init();
	}

	public CheckCode(int width, int height, int codeCount, int fontHeight,
			int codeX, int codeY) {
		super();
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.fontHeight = fontHeight;
		this.codeX = codeX;
		this.codeY = codeY;

		this.init();
	}

	private void init() {
		fontHeight = height - 2;
		codeX = width / (codeCount + 2);
		codeY = height - 4;
	}

	public boolean isValid(HttpServletRequest request, String result) {
		if (result == null || result == "")
			return false;
		HttpSession sessioin = request.getSession(false);
		if(sessioin==null)
			return false;

		String code = (String) sessioin.getAttribute(CHECK_CODE_KEY);
		if (code == null || code == "")
			return false;
		if (code.equalsIgnoreCase(result)) {
			sessioin.removeAttribute(CHECK_CODE_KEY);
			return true;
		}
		return false;
	};

	protected abstract String getPrintString();

	protected abstract String getRightAnswer();


	public BufferedImage getCheckCode(HttpServletRequest request) {
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

	public enum CODE_TYPE {
		DIGIT, LETTER, DIGIT_LETTER
	}

}
