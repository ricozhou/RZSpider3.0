package com.rzspider.project.commontool.toolrun.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Hashtable;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.project.ToolrunConstant;
import com.rzspider.common.utils.ImageUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.commontool.toolrun.domain.ORCode;

import de.hunsicker.jalopy.Jalopy;

//运行工具类
public class ToolrunUtils {
	// 插入二维码logo图片
	public static BufferedImage insertORLogoImage(BufferedImage image, ORCode orCode, boolean b) throws IOException {
		Image src;
		try {
			if (orCode.getLogoImageUrl().startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
				// 拼接出url
				String filePath = FilePathConfig.getUploadCachePath() + File.separator
						+ orCode.getLogoImageUrl().substring(12);
				File file = new File(filePath);
				if (file.exists()) {
					src = ImageIO.read(file);
				} else {
					return image;
				}
			} else {
				URL url = new URL(orCode.getLogoImageUrl());
				src = ImageIO.read(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 读取文件失败则直接返回，不再加入logo
			return image;
		}

		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (width > orCode.getOrCodeSizeW() / 5) {
			width = orCode.getOrCodeSizeW() / 5;
		}
		if (height > orCode.getOrCodeSizeH() / 5) {
			height = orCode.getOrCodeSizeH() / 5;
		}
		Image image2 = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image2, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		src = image2;
		// 插入LOGO
		Graphics2D graph = image.createGraphics();
		int x = (orCode.getOrCodeSizeW() - width) / 2;
		int y = (orCode.getOrCodeSizeH() - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
		return image;
	}

	// 解析二维码
	public static String analyQR(BufferedImage image) {
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result = null;
		Hashtable hints = new Hashtable();
		hints.put(DecodeHintType.CHARACTER_SET, CodingConstant.CODING_UTF_8);
		// 优化精度
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		// 复杂模式，开启PURE_BARCODE模式
		hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		try {
			result = new MultiFormatReader().decode(bitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		}
		String resultStr = result.getText();
		System.out.println(resultStr);
		return resultStr;
	}

	// 创建字符画
	public static BufferedImage createAsciiPic(BufferedImage image, BufferedImage createImg, String charText,
			int fontSize, int imgIntensity) {
		StringBuilder sb = new StringBuilder();
		// 字符串由复杂到简单
		String base = ToolrunConstant.TOOL_BASESET_MESSAGE_RUN_IMGTOCHAR_DEFAULT_CHARSTRING;
		if (StringUtils.notNullNotEmpty(charText)) {
			base = charText;
		}
		try {
			int w = image.getWidth();
			int h = image.getHeight();
			int ww = createImg.getWidth();
			int hh = createImg.getHeight();
			double xc = (double) (ww / w);
			double yc = (double) (hh / h);
			Graphics g2 = ImageUtils.createGraphics(createImg);
			// 设置字体
			g2.setFont(new Font(CommonSymbolicConstant.FONT_1, Font.PLAIN, fontSize));
			int pixel;
			int r;
			int g;
			int b;
			float gray;
			int index;

			for (int y = 0; y < image.getHeight(); y += imgIntensity) {
				for (int x = 0; x < image.getWidth(); x += imgIntensity) {
					pixel = image.getRGB(x, y);
					r = (pixel & 0xff0000) >> 16;
					g = (pixel & 0xff00) >> 8;
					b = pixel & 0xff;
					gray = 0.299f * r + 0.578f * g + 0.114f * b;
					index = Math.round(gray * (base.length() + 1) / 255);
					sb.append(
							index >= base.length() ? CommonSymbolicConstant.SPACE : String.valueOf(base.charAt(index)));
					g2.drawString(
							index >= base.length() ? CommonSymbolicConstant.SPACE : String.valueOf(base.charAt(index)),
							(int) ((x + 1) * xc), (int) ((y + 1) * yc));
				}
				sb.append(CommonSymbolicConstant.LINEBREAK);
			}
			return createImg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 返回小方块边长,处理后的图片宽高
	public static int[] proCubeImag(BufferedImage image, int cobeNum) {
		int size = 1;
		// 图片宽高
		int w = image.getWidth();
		int h = image.getHeight();
		// 横竖魔方个数，加起来不超过预定的
		int cubeWNum = (int) Math.sqrt(Double.valueOf((w * Integer.valueOf(cobeNum) / h)));
		int cubeHNum = (int) Math.sqrt(Double.valueOf((h * Integer.valueOf(cobeNum) / w)));
		if (cubeWNum < 1) {
			cubeWNum = 1;
		}
		if (cubeHNum < 1) {
			cubeHNum = 1;
		}
		// 横向小方块边长
		int ws = (int) Math.ceil(Double.valueOf((double) w / (double) (3 * cubeWNum)));
		// 竖向小方块边长
		int hs = (int) Math.ceil(Double.valueOf((double) h / (double) (3 * cubeHNum)));
		// 取最大
		size = ws > hs ? ws : hs;
		if (size < 1) {
			size = 1;
		}

		return new int[] { size, size * cubeWNum * 3, size * cubeHNum * 3 };
	}

	// 生成魔方拼图
	public static BufferedImage createCobeImage(BufferedImage image, int[] sizeWH) {
		BufferedImage bi = new BufferedImage(sizeWH[1], sizeWH[2], BufferedImage.TYPE_INT_RGB);

		int xcount = 0; // x方向绘制个数
		int ycount = 0; // y方向绘制个数
		xcount = sizeWH[1] / sizeWH[0];
		ycount = sizeWH[2] / sizeWH[0];
		int x = 0; // x坐标
		int y = 0;
		// y坐标
		// 绘制马赛克(绘制矩形并填充颜色)
		Graphics2D g = bi.createGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		for (int i = 0; i < xcount; i++) {
			for (int j = 0; j < ycount; j++) {
				// 马赛克矩形格大小
				int mwidth = sizeWH[0];
				int mheight = sizeWH[0];
				// 矩形颜色取中心像素点RGB值
				int centerX = x;
				int centerY = y;
				if (mwidth % 2 == 0) {
					centerX += mwidth / 2;
				} else {
					centerX += (mwidth - 1) / 2;
				}
				if (mheight % 2 == 0) {
					centerY += mheight / 2;
				} else {
					centerY += (mheight - 1) / 2;
				}
				// 获取中心点的rgb
				int p = bi.getRGB(centerX, centerY);
				// 获取rgb分量
				int[] rgb = new int[3];
				// Color color = new Color(img.getRGB(centerX, centerY));
				rgb[0] = (p & 0xff0000) >> 16;
				rgb[1] = (p & 0xff00) >> 16;
				rgb[2] = (p & 0xff);
				// rgb转为hsb
				float[] f = ImageUtils.rgb2hsb(rgb[0], rgb[1], rgb[2]);
				// 判断视觉颜色
				Color c;
				int ff = ImageUtils.PicToColor(f[0], f[1], f[2]);
				// 1红，2橙，3绿，4蓝，5黄，6白
				if (ff == 1) {
					c = Color.RED;
				} else if (ff == 2) {
					c = Color.ORANGE;
				} else if (ff == 3) {
					c = Color.GREEN;
				} else if (ff == 4) {
					c = Color.BLUE;
				} else if (ff == 5) {
					c = Color.YELLOW;
				} else if (ff == 6) {
					c = Color.WHITE;
				} else {
					c = Color.BLUE;
				}
				g.setColor(c);
				g.fillRect(x, y, mwidth, mheight);
				// 加框
				g.setColor(Color.BLACK);// 画笔颜色
				g.setStroke(new BasicStroke(1.0f));
				g.drawRect(x, y, mwidth, mheight);// 矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)

				y = y + sizeWH[0];// 计算下一个矩形的y坐标
			}
			y = 0;// 还原y坐标
			x = x + sizeWH[0];// 计算x坐标
		}
		g.dispose();
		return bi;
	}

	// 格式化java代码
	public static String formatJavaCode(String javaCode) {
		Jalopy jalopy = new Jalopy();
		jalopy.setEncoding(CodingConstant.CODING_UTF_8);
		StringBuffer output = new StringBuffer();
		// 缓存路径
		String path = FilePathConfig.getUploadCachePath() + File.separator + UUID.randomUUID()
				+ FileExtensionConstant.FILE_EXTENSION_POINT_CODEFILE_JAVA;
		jalopy.setInput(javaCode, path);
		jalopy.setOutput(output);
		jalopy.format();
		return output.toString();

	}

	// 格式化sql
	public static String formatSqlCode(String sqlCode, Integer sqlType) {
		if (sqlCode == null || CommonSymbolicConstant.EMPTY_STRING.equals(sqlCode) || sqlType == null) {
			return null;
		}
		// 默认mysql
		String dbType = JdbcConstants.MYSQL;
		if (sqlType == 0) {
			// mysql
			dbType = JdbcConstants.MYSQL;
		} else if (sqlType == 1) {
			// sqlserver
			dbType = JdbcConstants.SQL_SERVER;
		} else if (sqlType == 2) {
			// oracle
			dbType = JdbcConstants.ORACLE;
		} else if (sqlType == 3) {
			// postgresql
			dbType = JdbcConstants.POSTGRESQL;
		} else {
			return null;
		}
		try {
			// 无法拦截错误
			String result = SQLUtils.format(sqlCode, dbType);
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	// 格式化html,使用jsoup
	public static String formatHtmlCode(String content) {
		Document doc = Jsoup.parse(content);
		String html = doc.html();
		return html;
	}

	public static void main(String[] args) {
		// formatHtmlCode("苦涩吧v健康和设备的健康VB索达吉堪布长时间看吧十几块都不V领即使打包");
	}
}
