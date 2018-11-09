package com.rzspider.project.common.image.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.file.utilt.FileUtils;

/**
 * @author ricozhou
 * @date Oct 17, 2018 6:16:22 PM
 * @Desc
 */
public class WaterMarkUtils {
	/**
	 * @param markPosition
	 * @date Oct 17, 2018 6:17:10 PM
	 * @Desc 打水印
	 */
	public static boolean addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,
			Color markContentColor, Font font, int markPosition) {

		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);
			Image srcImg = ImageIO.read(srcImgFile);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			if (srcImgWidth < 200) {
				// 小图片不再打码
				return true;
			}
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.setColor(markContentColor);
			g.setFont(font);
			// 抗锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

			// 设置水印的坐标
			// 默认右下角
			// 根据宽度设置坐标
			int[] xy = getImgWidthX(srcImgWidth, srcImgHeight, waterMarkContent, g, markPosition);
			// int y = srcImgHeight - 20;
			// 画水印
			g.drawString(waterMarkContent, xy[0], xy[1]);
			g.dispose();
			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
			ImageIO.write(bufImg, FileUtils.getFileNameFromPoint(srcImgPath), outImgStream);
			outImgStream.flush();
			outImgStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @date Oct 18, 2018 10:55:13 AM
	 * @Desc 获取水印的x坐标
	 * @param srcImgWidth
	 * @param srcImgHeight
	 * @param waterMarkContent
	 * @param g
	 * @param markPosition
	 * @return
	 */
	private static int[] getImgWidthX(int srcImgWidth, int srcImgHeight, String waterMarkContent, Graphics2D g,
			int markPosition) {
		int x;
		int y;
		int waterMarkWidth = getWatermarkLength(waterMarkContent, g);
		double a = (double) srcImgWidth / (double) (2 * waterMarkWidth);
		Font font = new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (30 * a));
		g.setFont(font);
		waterMarkWidth = getWatermarkLength(waterMarkContent, g);
		// 右下角
		x = srcImgWidth - waterMarkWidth - 20;
		y = srcImgHeight - 20;
		if (markPosition == 1) {
			// 左下角
			x = 20;
		} else if (markPosition == 2) {
			// 右上角
			y = 20;
		} else if (markPosition == 3) {
			// 左上角
			x = 10;
			y = 20;
		} else if (markPosition == 4) {
			// 中间
			x = (srcImgWidth - waterMarkWidth) / 2;
			y = srcImgHeight / 2 - 10;
		}
		return new int[] { x, y };
	}

	// 获取文字的宽度
	public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
}
