package com.rzspider.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;
import com.rzspider.common.exception.file.FileNameLengthLimitExceededException;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.config.RZSpiderConfig;

/**
 * 文件上传工具类
 * 
 * @author ricozhou
 */
public class FileUploadUtils {

	// 默认大小 10M
	public static final long DEFAULT_MAX_SIZE = 10485760;
	public static final long DEFAULT_MAX_SIZE_500M = 524288000;
	public static final long DEFAULT_MAX_SIZE_100M = 104857600;

	// 默认上传的地址
	private static String defaultBaseDir = FilePathConfig.getUploadPath();
	// 默认头像地址
	private static String defaultProfileBaseDir = RZSpiderConfig.getProfile();
	// 默认通用工具地址
	private static String defaultToolBaseDir = FilePathConfig.getUploadToolPath();

	// 默认的文件名最大长度
	public static final int DEFAULT_FILE_NAME_LENGTH = 200;

	// 默认文件类型jpg
	public static final String IMAGE_JPG_EXTENSION = ".jpg";

	private static int counter = 0;

	public static String getDefaultToolBaseDir() {
		return defaultToolBaseDir;
	}

	public static void setDefaultToolBaseDir(String defaultToolBaseDir) {
		FileUploadUtils.defaultToolBaseDir = defaultToolBaseDir;
	}

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	public static String getDefaultProfileBaseDir() {
		return defaultProfileBaseDir;
	}

	public static void setDefaultProfileBaseDir(String defaultProfileBaseDir) {
		FileUploadUtils.defaultProfileBaseDir = defaultProfileBaseDir;
	}

	public static String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * 以默认配置进行文件上传
	 *
	 * @param file
	 *            上传的文件
	 * @return 文件名称
	 * @throws Exception
	 */
	public static final String upload(MultipartFile file) throws IOException {
		try {
			return upload(getDefaultProfileBaseDir(), file, FileUploadUtils.IMAGE_JPG_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * 根据文件路径上传
	 *
	 * @param baseDir
	 *            相对应用的基目录
	 * @param file
	 *            上传的文件
	 * @return 文件名称
	 * @throws IOException
	 */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			return upload(baseDir, file, FileUploadUtils.IMAGE_JPG_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * 文件上传
	 *
	 * @param baseDir
	 *            相对应用的基目录
	 * @param file
	 *            上传的文件
	 * @param needDatePathAndRandomName
	 *            是否需要日期目录和随机文件名前缀
	 * @param extension
	 *            上传文件类型
	 * @return 返回上传成功的文件名
	 * @throws FileSizeLimitExceededException
	 *             如果超出最大大小
	 * @throws FileNameLengthLimitExceededException
	 *             文件名太长
	 * @throws IOException
	 *             比如读写文件出错时
	 */
	public static final String upload(String baseDir, MultipartFile file, String extension)
			throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException {

		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
					FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}

		assertAllowed(file);

		String fileName = encodingFilename(file.getOriginalFilename(), extension);

		File desc = getAbsoluteFile(baseDir, baseDir + File.separator + fileName);
		file.transferTo(desc);
		return fileName;
	}

	private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {
		File desc = new File(File.separator + filename);

		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}

	/**
	 * 编码文件名
	 */
	private static final String encodingFilename(String filename, String extension) {
		filename = filename.replace("_", " ");
		filename = new Md5Hash(filename + System.nanoTime() + counter++).toHex().toString() + extension;
		return filename;
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
			throw new FileSizeLimitExceededException("not allowed upload upload", size, DEFAULT_MAX_SIZE);
		}
	}

	public static final void assertAllowedSetSize(MultipartFile file, long maxSize)
			throws FileSizeLimitExceededException {
		long size = file.getSize();
		if (maxSize != -1 && size > maxSize) {
			throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
		}
	}

	// 重命名
	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 上传
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + File.separator + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	// 删除
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
