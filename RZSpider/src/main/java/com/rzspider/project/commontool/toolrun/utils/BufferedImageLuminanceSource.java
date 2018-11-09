package com.rzspider.project.commontool.toolrun.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import com.google.zxing.LuminanceSource;

/**
 * LuminanceSource这类层次结构的目的是不同的位图实现跨平台为请求获得灰度亮度值的标准接口。该接口只提供了抽象方法，
 * 因此可以生成和旋转创建副本。这是为了确保一个读者不修改原来的亮度源，并让它在一个未知的状态，在链中的其他读者。
 * https://zxing.github.io/zxing/apidocs/com/google/zxing/LuminanceSource.html
 */
public class BufferedImageLuminanceSource extends LuminanceSource {

	private final BufferedImage image;
	private final int left;
	private final int top;

	public BufferedImageLuminanceSource(BufferedImage image) {
		this(image, 0, 0, image.getWidth(), image.getHeight());
	}

	/**
	 * 构造方法
	 * 
	 * @param image
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
		super(width, height);

		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		if (left + width > sourceWidth || top + height > sourceHeight) {
			throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		}

		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				if ((image.getRGB(x, y) & 0xFF000000) == 0) {
					image.setRGB(x, y, 0xFFFFFFFF); // = white
				}
			}
		}

		this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		this.left = left;
		this.top = top;
	}

	@Override
	public byte[] getRow(int y, byte[] row) {// 从底层平台的位图提取一行（only one row）的亮度数据值
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		image.getRaster().getDataElements(left, top + y, width, 1, row);
		return row;
	}

	@Override
	public byte[] getMatrix() {/// 从底层平台的位图提取亮度数据值
		int width = getWidth();
		int height = getHeight();
		int area = width * height;
		byte[] matrix = new byte[area];
		image.getRaster().getDataElements(left, top, width, height, matrix);
		return matrix;
	}

	@Override
	public boolean isCropSupported() {// 是否支持裁剪
		return true;
	}

	/**
	 * 返回一个新的对象与裁剪的图像数据。实现可以保存对原始数据的引用，而不是复制。
	 */
	@Override
	public LuminanceSource crop(int left, int top, int width, int height) {
		return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
	}

	@Override
	public boolean isRotateSupported() {// 是否支持旋转
		return true;
	}

	@Override
	public LuminanceSource rotateCounterClockwise() {// 逆时针旋转图像数据的90度，返回一个新的对象。
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
		BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(image, transform, null);
		g.dispose();
		int width = getWidth();
		return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
	}
}
