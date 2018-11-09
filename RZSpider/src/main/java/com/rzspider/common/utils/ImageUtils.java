package com.rzspider.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.constant.project.ToolrunConstant;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.project.common.checkutils.CheckStringUtils;
import com.rzspider.project.common.image.gifdecoder.AnimatedGifEncoder;
import com.rzspider.project.common.image.gifdecoder.GifDecoder;
import com.rzspider.project.commontool.toolrun.domain.GifMsg;

//图片工具类
public class ImageUtils {
	// 缩放gif
	public static BufferedImage zoomImage(BufferedImage bufImg, int jpwidth, int jpheight) {
		return zoomImage(null, jpwidth, jpheight, bufImg);
	}

	// 缩放图片
	public static BufferedImage zoomImage(String absolutePath, int jpwidth, int jpheight, BufferedImage bufImg) {
		double wr = 0, hr = 0;
		if (absolutePath != null) {
			File srcFile = new File(absolutePath);
			bufImg = null;
			try {
				bufImg = ImageIO.read(srcFile);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		int w = bufImg.getWidth();
		int h = bufImg.getHeight();
		int[] wh = proImage(w, h, jpwidth, jpheight);

		Image Itemp = bufImg.getScaledInstance(wh[0], wh[1], bufImg.SCALE_SMOOTH);
		wr = wh[0] * 1.0 / bufImg.getWidth();
		hr = wh[1] * 1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		return (BufferedImage) Itemp;
	}

	private static int[] proImage(int w, int h, int jpwidth, int jpheight) {
		// 判断大小
		// 先以宽为缩放准则
		if (jpwidth * h / w <= jpheight) {
			// 如果高不超过
			return new int[] { jpwidth, jpwidth * h / w };
		} else {
			// 如果高超过了
			// 则以高为准则
			if (w * jpheight / h <= jpwidth) {
				return new int[] { w * jpheight / h, jpheight };
			} else {
				return null;
			}
		}
	}

	// 获取图片
	public static BufferedImage getLocalAndUrlImage(String filePath) {
		BufferedImage image = null;
		try {
			if (filePath.startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
				// 拼接出url
				filePath = FilePathConfig.getUploadCachePath() + File.separator + filePath.substring(12);
				File file = new File(filePath);
				if (file.exists()) {
					image = ImageIO.read(file);
				}
			} else {
				URL url = new URL(filePath);
				image = ImageIO.read(url);
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 另外一种压缩
	public BufferedImage zoom(BufferedImage bimg, int w, int h) throws IOException {
		if (w < 1 || h < 1) {
			return bimg;
		}
		int height = bimg.getHeight();
		int width = bimg.getWidth();
		BufferedImage bi = new BufferedImage(w, height * h / width, BufferedImage.TYPE_BYTE_GRAY);

		Graphics g = bi.getGraphics();
		g.drawImage(bimg, 0, 0, w, height * h / width, null);
		g.dispose();

		return bi;
	}

	// 缩放图片
	public static BufferedImage zoom2(BufferedImage bufImg, int jpwidth, int jpheight) {
		if (jpwidth < 1 || jpheight < 1) {
			return bufImg;
		}
		double wr = 0, hr = 0;
		Image Itemp = bufImg.getScaledInstance(jpwidth, jpheight, bufImg.SCALE_SMOOTH);
		wr = jpwidth * 1.0 / bufImg.getWidth();
		hr = jpheight * 1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		return (BufferedImage) Itemp;
	}

	// 创建一个缓冲图片
	public static BufferedImage createBufferedImage(int w, int h) {
		BufferedImage image2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		Graphics g = image2.createGraphics();
		// 背景色
		g.setColor(Color.WHITE);
		// 背景
		g.fillRect(0, 0, w, h);
		// 前景色
		g.setColor(Color.BLACK);
		return image2;
	}

	// 获取画笔
	public static Graphics createGraphics(BufferedImage createImg) {
		Graphics g = createImg.createGraphics();
		g.setColor(Color.WHITE); // 设置背景色
		g.fillRect(0, 0, createImg.getWidth(), createImg.getHeight());// 绘制背景
		g.setColor(Color.BLACK); // 设置前景色
		return g;
	}

	// 拆分gif
	public static GifMsg splitGif(String gifName) {
		GifMsg gm = new GifMsg();
		List<BufferedImage> list = new ArrayList<BufferedImage>();
		List<Integer> list2 = new ArrayList<Integer>();
		try {
			GifDecoder decoder = new GifDecoder();
			decoder.read(gifName);
			// 个数
			int n = decoder.getFrameCount();

			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i);
				list2.add(decoder.getDelay(i));
				list.add(frame);
			}
			gm.setList(list);
			gm.setList2(list2);
			return gm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gm;
	}

	// 合成gif,并写入
	public static boolean jpgToGif(List<BufferedImage> list, List<Integer> list2, String newPicPath) {
		File file = new File(newPicPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			// 实例化处理对象
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			e.setRepeat(0);
			e.start(newPicPath);
			for (int i = 0; i < list.size(); i++) {
				// 设置播放的延迟时间
				e.setDelay(list2.get(i));
				// 添加到帧中
				e.addFrame(list.get(i));
			}
			e.finish();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 不根据文件后缀判断图片格式
	public static String getImageFormat(String name) {
		name = name.trim().toLowerCase();
		BufferedInputStream in;
		try {
			if ((name.indexOf("file:") >= 0) || (name.indexOf(":/") > 0)) {
				// 网络
				URL url = new URL(name);
				in = new BufferedInputStream(url.openStream());
			} else {
				// 本地
				String filePath = "";
				if (name.startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX2)) {
					// 拼接出url
					filePath = FilePathConfig.getUploadCachePath() + File.separator + name.substring(12);
				} else if (name.startsWith(FileOtherConstant.FILE_JUMP_PATH_PREFIX)) {
					filePath = FilePathConfig.getUploadPath() + File.separator + name.substring(7);
				} else {
					return null;
				}
				in = new BufferedInputStream(new FileInputStream(new File(filePath)));
			}
			return getPicType(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 根据文件流判断图片类型
	 * 
	 * @return jpg/png/gif/bmp
	 */
	public static String getPicType(BufferedInputStream fis) {
		// 读取文件的前几个字节来判断图片格式
		byte[] b = new byte[4];
		try {
			fis.read(b, 0, b.length);
			String type = bytesToHexString(b).toUpperCase();
			if (type.contains("FFD8FF")) {
				return FileExtensionConstant.FILE_EXTENSION_IMAGE_JPG;
			} else if (type.contains("89504E47")) {
				return FileExtensionConstant.FILE_EXTENSION_IMAGE_PNG;
			} else if (type.contains("47494638")) {
				return FileExtensionConstant.FILE_EXTENSION_IMAGE_GIF;
			} else if (type.contains("424D")) {
				return FileExtensionConstant.FILE_EXTENSION_IMAGE_BMP;
			} else {
				return FileExtensionConstant.FILE_EXTENSION_IMAGE_PNG;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * byte数组转换成16进制字符串
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	// rgb转hsb
	public static float[] rgb2hsb(int rgbR, int rgbG, int rgbB) {
		assert 0 <= rgbR && rgbR <= 255;
		assert 0 <= rgbG && rgbG <= 255;
		assert 0 <= rgbB && rgbB <= 255;
		int[] rgb = new int[] { rgbR, rgbG, rgbB };
		Arrays.sort(rgb);
		int max = rgb[2];
		int min = rgb[0];

		float hsbB = max / 255.0f;
		float hsbS = max == 0 ? 0 : (max - min) / (float) max;

		float hsbH = 0;
		if (max == rgbR && rgbG >= rgbB) {
			hsbH = (rgbG - rgbB) * 60f / (max - min) + 0;
		} else if (max == rgbR && rgbG < rgbB) {
			hsbH = (rgbG - rgbB) * 60f / (max - min) + 360;
		} else if (max == rgbG) {
			hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
		} else if (max == rgbB) {
			hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
		}

		return new float[] { hsbH, hsbS, hsbB };
	}

	// 16进制转rgb
	public static int[] hexToRgb(String hex) {
		if (!isHEX(hex)) {
			return new int[] { 255, 255, 255 };
		}

		String c = StringUtils.replaceString(hex.toUpperCase(), CommonSymbolicConstant.POUND,
				CommonSymbolicConstant.EMPTY_STRING);

		int r = Integer.parseInt((c.length() == 3 ? c.substring(0, 1) + c.substring(0, 1) : c.substring(0, 2)), 16);
		int g = Integer.parseInt((c.length() == 3 ? c.substring(1, 2) + c.substring(1, 2) : c.substring(2, 4)), 16);
		int b = Integer.parseInt((c.length() == 3 ? c.substring(2, 3) + c.substring(2, 3) : c.substring(4, 6)), 16);
		return new int[] { r, g, b };
	}

	// 判断是不是16进制
	public static boolean isHEX(String hex) {
		return CheckStringUtils.checkStringByRegularExpression(hex, RegularExpressionConstant.REGULAR_EXPRESSION_HEX);
	}

	// 根据hsb判断属于什么颜色
	public static int PicToColor(float h, float s, float b) {
		// 1红，2橙，3绿，4蓝，5黄，6白
		if (b >= 0 && b < 0.4) {
			if (s >= 0 && s < 0.4) {
				if (h >= 0 && h < 359) {
					// 蓝色
					return 4;
				}
			} else if (s >= 0.4 && s < 0.7) {
				if (h >= 0 && h < 35) {
					// 红
					return 1;
				} else if (h >= 35 && h < 90) {
					// 绿
					return 3;
				} else if (h >= 90 && h < 310) {
					// 蓝
					return 4;
				} else if (h >= 310 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.7 && s <= 1) {
				if (h >= 0 && h < 15) {
					// 红
					return 1;
				} else if (h >= 15 && h < 30) {
					// 橙
					return 2;
				} else if (h >= 30 && h < 150) {
					// 绿
					return 3;
				} else if (h >= 150 && h < 310) {
					// 蓝
					return 4;
				} else if (h >= 310 && h < 359) {
					// 红
					return 1;
				}
			}

		} else if (b >= 0.4 && b < 0.7) {
			if (s >= 0 && s < 0.1) {
				if (h >= 0 && h < 15) {
					// 红
					return 1;
				} else if (h >= 15 && h < 150) {
					// 绿
					return 3;
				} else if (h >= 150 && h < 300) {
					// 蓝
					return 4;
				} else if (h >= 300 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.1 && s < 0.4) {
				if (h >= 0 && h < 20) {
					// 红
					return 1;
				} else if (h >= 20 && h < 35) {
					// 橙
					return 2;
				} else if (h >= 35 && h < 130) {
					// 绿
					return 3;
				} else if (h >= 130 && h < 330) {
					// 蓝
					return 4;
				} else if (h >= 330 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.4 && s < 0.7) {
				if (h >= 0 && h < 15) {
					// 红
					return 1;
				} else if (h >= 15 && h < 40) {
					// 橙
					return 2;
				} else if (h >= 40 && h < 150) {
					// 绿
					return 3;
				} else if (h >= 150 && h < 300) {
					// 蓝
					return 4;
				} else if (h >= 300 && h < 340) {
					// 橙
					return 2;
				} else if (h >= 340 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.7 && s <= 1) {
				if (h >= 0 && h < 15) {
					// 红
					return 1;
				} else if (h >= 15 && h < 35) {
					// 橙
					return 2;
				} else if (h >= 35 && h < 60) {
					// 黄
					return 5;
				} else if (h >= 60 && h < 150) {
					// 绿
					return 3;
				} else if (h >= 150 && h < 290) {
					// 蓝
					return 4;
				} else if (h >= 290 && h < 320) {
					// 橙
					return 2;
				} else if (h >= 320 && h < 359) {
					// 红
					return 1;
				}
			}
		} else if (b >= 0.7 && b <= 1) {
			if (s >= 0 && s < 0.2) {
				// 白
				return 6;
			} else if (s >= 0.2 && s < 0.4) {
				if (h >= 0 && h < 20) {
					// 红
					return 1;
				} else if (h >= 20 && h < 60) {
					// 黄
					return 5;
				} else if (h >= 60 && h < 140) {
					// 绿
					return 3;
				} else if (h >= 140 && h < 270) {
					// 蓝
					return 4;
				} else if (h >= 270 && h < 300) {
					// 橙
					return 2;
				} else if (h >= 300 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.4 && s < 0.7) {
				if (h >= 0 && h < 15) {
					// 红
					return 1;
				} else if (h >= 15 && h < 35) {
					// 橙
					return 2;
				} else if (h >= 35 && h < 70) {
					// 黄
					return 5;
				} else if (h >= 70 && h < 120) {
					// 绿
					return 3;
				} else if (h >= 120 && h < 290) {
					// 蓝
					return 4;
				} else if (h >= 290 && h < 359) {
					// 红
					return 1;
				}
			} else if (s >= 0.7 && s <= 1) {
				if (h >= 0 && h < 10) {
					// 红
					return 1;
				} else if (h >= 10 && h < 40) {
					// 橙
					return 2;
				} else if (h >= 40 && h < 75) {
					// 黄
					return 5;
				} else if (h >= 75 && h < 150) {
					// 绿
					return 3;
				} else if (h >= 150 && h < 290) {
					// 蓝
					return 4;
				} else if (h >= 290 && h < 359) {
					// 红
					return 1;
				}
			}
		}

		return 0;
	}
}
