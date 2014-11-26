package org.hyl.utils.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ��֤��Ļ���<br>
 * ʹ��ʱ��client���͵���֤���name����ͱ����{@link CheckCode.CHECK_CODE_KEY}�ֶε�ֵ���
 * 
 * @author HuYongliang
 *
 */
public abstract class CheckCode {
	public static String CHECK_CODE_KEY = "CHECK_CODE_KEY";

	protected int width = 152;
	protected int height = 40;
	protected int codeCount = 4;

	// ��֤������ĸ߶�
	protected int fontHeight = 4;

	// ��֤���еĵ����ַ�����. ������֤���еĵ����ַ�λ����֤��ͼ�����Ͻǵ� (codeX, codeY) λ�ô�
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

		// ����һ����ɫ, ʹ Graphics2D ����ĺ���ͼ��ʹ�������ɫ
		graphics.setColor(Color.WHITE);

		// ���һ��ָ���ľ���: x - Ҫ�����ε� x ����; y - Ҫ�����ε� y ����; width - Ҫ�����εĿ��; height
		// - Ҫ�����εĸ߶�
		graphics.fillRect(0, 0, width, height);

		// ����һ�� Font ����: name - ��������; style - Font ����ʽ����; size - Font �ĵ��С
		Font font = null;
		font = new Font("", Font.BOLD, fontHeight);
		// ʹ Graphics2D ����ĺ���ͼ��ʹ�ô�����
		graphics.setFont(font);

		graphics.setColor(Color.BLACK);

		// ����ָ�����εı߿�, ���Ƴ��ľ��ν��ȹ�����һ��Ҳ��һ������
		graphics.drawRect(0, 0, width - 1, height - 1);

		// ������� 15 ��������, ʹͼ���е���֤�벻�ױ���������̽�⵽
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
		// ʹ Graphics2D ����ĺ���ͼ��ʹ�ô�����
		graphics.setFont(font);
	}

	/**
	 * ����ȱʡ�ṩ����֤�������ѡ��
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
	 *            client���͵���֤��
	 * @return client���͵���֤���Ƿ���ȷ
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
