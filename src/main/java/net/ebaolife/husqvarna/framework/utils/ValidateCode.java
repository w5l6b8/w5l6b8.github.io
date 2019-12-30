package net.ebaolife.husqvarna.framework.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidateCode {

	public static final int TYPE_NUM_ONLY = 0;

	public static final int TYPE_LETTER_ONLY = 1;

	public static final int TYPE_ALL_MIXED = 2;

	public static final int TYPE_NUM_UPPER = 3;

	public static final int TYPE_NUM_LOWER = 4;

	public static final int TYPE_UPPER_ONLY = 5;

	public static final int TYPE_LOWER_ONLY = 6;

	private ValidateCode() {

	}

	public static String generateTextCode(int type, int length, String exChars) {

		if (length <= 0)
			return "";

		StringBuffer code = new StringBuffer();
		int i = 0;
		Random r = new Random();

		switch (type) {

		case TYPE_NUM_ONLY:
			while (i < length) {
				int t = r.nextInt(10);
				if (exChars == null || exChars.indexOf(t + "") < 0) {
					code.append(t);
					i++;
				}
			}
			break;

		case TYPE_LETTER_ONLY:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		case TYPE_ALL_MIXED:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57))
						&& (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		case TYPE_NUM_UPPER:
			while (i < length) {
				int t = r.nextInt(91);
				if ((t >= 65 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		case TYPE_NUM_LOWER:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		case TYPE_UPPER_ONLY:
			while (i < length) {
				int t = r.nextInt(91);
				if ((t >= 65) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		case TYPE_LOWER_ONLY:
			while (i < length) {
				int t = r.nextInt(123);
				if ((t >= 97) && (exChars == null || exChars.indexOf((char) t) < 0)) {
					code.append((char) t);
					i++;
				}
			}
			break;

		}

		return code.toString();
	}

	public static BufferedImage generateImageCode(String value) {
		Random rand = new Random(System.currentTimeMillis());

		int width = 90;
		int height = 30;
		Graphics2D g = null;

		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = bimage.createGraphics();

		Color color = new Color(255, 255, 255);

		g.setColor(color.darker());
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("arial", Font.BOLD, 36));

		int w = (g.getFontMetrics()).stringWidth(value);
		int d = (g.getFontMetrics()).getDescent();
		int a = (g.getFontMetrics()).getMaxAscent();
		int x = 0, y = 0;

		for (int i = 0; i < height;) {
			i += 8 + rand.nextInt(15);
			g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			g.drawLine(x, y + i, width, y + i);
		}

		x = 0;
		y = 0;

		for (int i = 0; i < height;) {
			i += 8 + rand.nextInt(15);
			g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			g.drawLine(x, y + d - i, width + w, height + d - i);
		}

		g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)).brighter());

		x = width / 2 - w / 2;
		y = height / 2 + a / 2 - 6;

		AffineTransform fontAT = new AffineTransform();
		int xp = x - 2;

		for (int c = 0; c < value.length(); c++) {

			int rotate = rand.nextInt(25);
			fontAT.rotate(rand.nextBoolean() ? Math.toRadians(rotate) : -Math.toRadians(rotate / 2));
			Font fx = new Font(new String[] { "Times New Roman", "Verdana", "arial" }[rand.nextInt(2)], rand.nextInt(5),
					20 + rand.nextInt(16)).deriveFont(fontAT);
			g.setFont(fx);

			Random random = new Random();
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);

			g.setColor(new Color(red, green, blue));
			String ch = String.valueOf(value.charAt(c));
			int ht = rand.nextInt(3);

			g.drawString(ch, xp, y + (rand.nextBoolean() ? -ht : ht));

			xp += g.getFontMetrics().stringWidth(ch) + 2;
		}

		g.dispose();

		return bimage;
	}

	public static BufferedImage generateImageCode_1(String textCode, int width, int height, int interLine,
			boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {

		BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bim.getGraphics();

		g.setColor(backColor == null ? getRandomColor() : backColor);
		g.fillRect(0, 0, width, height);

		Random r = new Random();
		if (interLine > 0) {

			int x = 0, y = 0, x1 = width, y1 = 0;
			for (int i = 0; i < interLine; i++) {
				g.setColor(lineColor == null ? getRandomColor() : lineColor);
				y = r.nextInt(height);
				y1 = r.nextInt(height);

				g.drawLine(x, y, x1, y1);
			}
		}

		int fsize = (int) (height * 0.8);
		int fx = height - fsize;
		int fy = fsize;

		g.setFont(new Font("Default", Font.PLAIN, fsize));

		for (int i = 0; i < textCode.length(); i++) {
			fy = randomLocation ? (int) ((Math.random() * 0.3 + 0.6) * height) : fy;
			g.setColor(foreColor == null ? getRandomColor() : foreColor);
			g.drawString(textCode.charAt(i) + "", fx, fy);
			fx += fsize * 0.9;
		}

		g.dispose();

		return bim;
	}

	private static Color getRandomColor() {
		Random r = new Random();
		Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		return c;
	}

}