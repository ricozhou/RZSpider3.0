package com.rzspider.project.common.file.utilt;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import java.util.List;
import java.util.zip.ZipEntry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.RegularExpressionConstant;
import com.rzspider.common.exception.base.BaseException;
import com.rzspider.framework.config.FilePathConfig;

//文件工具类
public class FileUtils {
	// 默认大小 100M
	public static final long DEFAULT_MAX_SIZE = 104857600;

	// 文件名校验
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255)
			return false;
		else
			return fileName.matches(RegularExpressionConstant.REGULAR_EXPRESSION_FILENAME_CHECK);
	}

	/**
	 * 文件大小校验
	 *
	 * @param file
	 *            上传的文件
	 * @return
	 * @throws FileSizeLimitExceededException
	 *             如果超出最大大小
	 */
	public static final void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException {
		long size = file.getSize();
		if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
			throw new FileSizeLimitExceededException(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_SIZE, size,
					DEFAULT_MAX_SIZE);
		}
	}

	// 创建文件夹
	public static boolean createFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}

	// 创建文件,如果文件存在则true，如果不存在则先创建文件夹在创建文件
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		try {
			if (file.exists()) {
				return true;
			} else {
				File fileParent = file.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				return file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 复制文件
	public static boolean copyFileToFolder(String oldFile, String newFile) {
		File file1 = new File(oldFile);
		File file2 = new File(newFile);
		if (!file1.exists()) {
			return false;
		}
		if (file2.exists()) {
			file2.delete();
		}
		try {
			Files.copy(file1.toPath(), file2.toPath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 复制一个文件夹下所有的文件到另一个文件夹下
	public static boolean copyAllFileToFolder(String oldFilePath, String newFilePath, boolean replace) {
		File file1 = new File(oldFilePath);
		File file2 = new File(newFilePath);
		if (!file1.exists()) {
			return true;
		}
		File file3;
		File[] files = file1.listFiles();
		try {
			for (File f : files) {
				if (f.isFile()) {
					file3 = new File(file2.toPath() + File.separator + f.getName());
					if (replace) {
						Files.copy(f.toPath(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);
					} else {
						Files.copy(f.toPath(), file3.toPath());
					}
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// 删除文件夹
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (!deleteAllFile(file)) {
				return false;
			}
		}
		return true;
	}

	// 删除文件夹但保留最后的文件夹
	public static boolean deleteAllFileWithoutFolder(String filePath) {
		if (deleteFile(filePath)) {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			return true;
		}
		return false;
	}

	// 删除所有
	private static boolean deleteAllFile(File file) {
		String[] files = file.list();
		if (files != null && files.length > 0) {
			for (String f : files) {
				boolean success = deleteAllFile(new File(file, f));
				if (!success) {
					return false;
				}
			}
		}
		return file.delete();
	}

	// 重命名
	public static boolean renameFile(String oldName, String newName) {
		File file1 = new File(oldName);
		File file2 = new File(newName);
		if (!file1.exists() || file1.isDirectory()) {
			return true;
		}
		if (oldName.equals(newName)) {
			return true;
		} else {
			return file1.renameTo(file2);
		}
	}

	// 处理乱码
	public static String getNewString(String fileName) throws UnsupportedEncodingException {
		String newFileName = URLEncoder.encode(fileName, CodingConstant.CODING_UTF_8);
		if (newFileName.length() > 150) {
			/* 根据request的locale 得出可能的编码，中文操作系统通常是gb2312 */
			String charset = CodingConstant.CODING_GB2312;
			newFileName = new String(fileName.getBytes(charset), CodingConstant.CODING_ISO);
		}
		return newFileName;
	}

	// 读取文件内容
	public static String getFileToString(String filePath) {
		StringBuilder result = new StringBuilder();
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		// 读取文件内容并返回
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CodingConstant.CODING_UTF_8));
			String s = null;
			// 整行读取
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 封装方法
	// 把此路径下所有的文件打包zip,需要打包的文件文件夹，是否带上层文件夹，上层文件夹名(重新指定的，如果不携带则无需指定)
	public static byte[] getAllFileToByte(List<String> filePathList, boolean bringFolder, String topFolderName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		if (filePathList != null && filePathList.size() > 1) {
			// 不止一个文件夹打包，打包到同一个父级目录下
			// 设置合成的文件，带一个文件夹
			File file = new File(filePathList.get(0));
			// 取第一个的上级文件夹
			String exfilePath = getFileNameFromSlash(file.getParent());
			if (topFolderName != null) {
				exfilePath = topFolderName;
			}
			File file2;
			for (String filePath : filePathList) {
				file2 = new File(filePath);
				try {
					// 打包
					unpackFile(zip, file2, exfilePath + CommonSymbolicConstant.FORWARD_SLASH
							+ getFileNameFromSlash(file2.getAbsolutePath()));
				} catch (Exception e) {
					e.printStackTrace();
					throw new BaseException(FileMessageConstant.FILE_MESSAGE_FILE_EXPORT_FAILED);
				}
			}

		} else if (filePathList != null && filePathList.size() == 1) {
			// 设置合成的文件，带一个文件夹
			String exfilePath = getFileNameFromSlash(filePathList.get(0));
			if (topFolderName != null) {
				exfilePath = topFolderName;
			}

			File file = new File(filePathList.get(0));
			// 是否携带文件夹
			if (bringFolder) {
				// 携带文件夹
				if (file.isFile()) {
					// 文件名
					exfilePath = getFileNameFromPoint(exfilePath) + CommonSymbolicConstant.FORWARD_SLASH + exfilePath;
				}
			} else {
				// 不携带文件夹
				exfilePath = CommonSymbolicConstant.EMPTY_STRING;
			}
			try {
				// 打包
				unpackFile(zip, file, exfilePath);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BaseException(FileMessageConstant.FILE_MESSAGE_FILE_EXPORT_FAILED);
			}
		}

		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	// 逐级打包
	public static void unpackFile(ZipOutputStream zip, File file, String dir) throws Exception {
		// 如果当前的是文件夹，则进行进一步处理
		if (file.isDirectory()) {
			// 得到文件列表信息
			File[] files = file.listFiles();
			// 将文件夹添加到下一级打包目录
			zip.putNextEntry(new ZipEntry(dir + CommonSymbolicConstant.FORWARD_SLASH));
			dir = dir.length() == 0 ? CommonSymbolicConstant.EMPTY_STRING : dir + CommonSymbolicConstant.FORWARD_SLASH;
			// 循环将文件夹中的文件打包
			for (int i = 0; i < files.length; i++) {
				unpackFile(zip, files[i], dir + files[i].getName());
			}
		} else { // 当前的是文件，打包处理
			// 文件输入流
			// BufferedInputStream bis = new BufferedInputStream(new
			// FileInputStream(file));
			ZipEntry entry = new ZipEntry(dir);
			zip.putNextEntry(entry);
			zip.write(readFileToByteArray(file));
			// IOUtils.closeQuietly(bis);
			zip.flush();
			zip.closeEntry();
		}
	}

	// 读取
	public static byte[] readFileToByteArray(File file) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		try {
			int length = 0;
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[100];
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

	// 取出最后一个斜杠之后的字符串
	public static String getFileNameFromSlash(String filePath) {
		String str;
		if (filePath == null) {
			return null;
		}
		str = filePath.substring(filePath.lastIndexOf(CommonSymbolicConstant.FILE_BACKSLASH) + 1, filePath.length());
		return str;
	}

	// 取出最后一个斜杠之前的字符串
	public static String getFileNameBeforeSlash(String filePath) {
		String str;
		if (filePath == null) {
			return null;
		}
		if (filePath.indexOf(CommonSymbolicConstant.FILE_BACKSLASH) < 0) {
			return filePath;
		}
		str = filePath.substring(0, filePath.lastIndexOf(CommonSymbolicConstant.FILE_BACKSLASH));
		return str;
	}

	// 取出最后一个点之后的字符串
	public static String getFileNameFromPoint(String filePath) {
		String str;
		if (filePath == null) {
			return null;
		}
		str = filePath.substring(filePath.lastIndexOf(CommonSymbolicConstant.POINT) + 1);
		return str;
	}

	// 取出最后一个点之前的字符串
	public static String getFileNameBeforePoint(String filePath) {
		String str;
		if (filePath == null) {
			return null;
		}
		if (filePath.indexOf(CommonSymbolicConstant.POINT) < 0) {
			return filePath;
		}
		str = filePath.substring(0, filePath.lastIndexOf(CommonSymbolicConstant.POINT));
		return str;
	}

	// 追加文件
	public static boolean appendFile(String filePath, String content) {
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容
			// 如果文件不存在，则创建文件
			File f = new File(filePath);
			fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 保存图片至缓存文件夹
	public static boolean imageToCachePath(BufferedImage image, String format, String fileName) {
		File file = new File(FilePathConfig.getUploadCachePath());
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			ImageIO.write(image, format, new File(FilePathConfig.getUploadCachePath() + File.separator + fileName));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 解压缩
	public static boolean unZipFiles(File zipFile, String descDir, boolean createFolder) {
		InputStream in = null;
		FileOutputStream out = null;
		ZipFile zip = null;
		try {
			zip = new ZipFile(zipFile, Charset.forName(CodingConstant.CODING_GBK));
			File pathFile = new File(descDir);
			if (createFolder) {
				pathFile = new File(descDir + File.separator + getFileNameBeforePoint(zipFile.getName()));
			}
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			ZipEntry entry;
			String zipEntryName;
			String outPath;
			File file;
			for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
				entry = (ZipEntry) entries.nextElement();
				zipEntryName = entry.getName();
				in = zip.getInputStream(entry);
				outPath = (descDir + File.separator + zipEntryName).replaceAll(CommonSymbolicConstant.OTHERCS_1,
						CommonSymbolicConstant.FORWARD_SLASH);
				// 判断路径是否存在,不存在则创建文件路径
				file = new File(outPath.substring(0, outPath.lastIndexOf(CommonSymbolicConstant.FORWARD_SLASH)));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}

				out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				in.close();
				out.close();
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
