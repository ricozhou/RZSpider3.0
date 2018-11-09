package com.rzspider.implementspider.blogmove.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.AbstractWordUtils;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.rzspider.common.constant.CodingConstant;
import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileOtherConstant;
import com.rzspider.project.common.file.utilt.FileUtils;

import fr.opensagres.poi.xwpf.converter.core.IXWPFConverter;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;

/**
 * @author ricozhou
 * @date Oct 29, 2018 5:27:08 PM
 * @Desc
 */
public class BlogMoveWordToHtml {

	// doc全路径（包括文件名），生成的htmlPath，博客缓存路径，图片保存路径，html中图片显示路径
	public static String wordToHtml(String docAllPath, String htmlPath, String path, String imagePath,
			String htmlImgPath) throws IOException, ParserConfigurationException, TransformerException, SAXException {
		String htmlText = null;
		if (docAllPath.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOC)) {
			// doc
			htmlText=word2003ToHtml(docAllPath, imagePath, htmlImgPath);

		} else if (docAllPath.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_FILE_DOCX)) {
			// docx
			word2007ToHtml(docAllPath, htmlPath, imagePath, htmlImgPath);
			// 读取
			htmlText = FileUtils.getFileToString(htmlPath);
			// 取出body，去除divstyle样式使文章靠边界显示
			Element e = Jsoup.parse(htmlText).body();
			// 为了靠右显示，只能先去掉收个div的全局样式，正常word转html则不需要
			e.select("div").first().attr("style", "");
			htmlText = e.html();
			// 将图片集体搬运到该去的地方
			FileUtils.copyAllFileToFolder(imagePath + File.separator + htmlImgPath, imagePath, true);
			// 删除原文件夹
			FileUtils.deleteFile(imagePath + FileOtherConstant.FILE_JUMP_PATH_PREFIX3);
			// 删除html文件
			new File(htmlPath).delete();
		}
		return htmlText;
	}

	/**
	 * @date Oct 29, 2018 5:22:53 PM
	 * @Desc docx word2007
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	public static void word2007ToHtml(String docAllPath, String htmlPath, String imagePath, String htmlImgPath)
			throws IOException, ParserConfigurationException, TransformerException, SAXException {
		File sourceFile = new File(docAllPath);
		if (!sourceFile.exists()) {
			return;
		} else {
			XWPFDocument document = new XWPFDocument(new FileInputStream(docAllPath));
			// html转化器
			IXWPFConverter<XHTMLOptions> converter = XHTMLConverter.getInstance();
			// html属性器
			XHTMLOptions options = XHTMLOptions.create();
			// 图片处理，第二个参数为html文件同级目录下，否则图片找不到。
			ImageManager imageManager = new ImageManager(new File(imagePath), htmlImgPath);
			options.setImageManager(imageManager);
			converter.convert(document, new FileOutputStream(htmlPath), options);
		}
	}

	/**
	 * @date Oct 29, 2018 5:22:53 PM
	 * @Desc doc word2003
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	public static String word2003ToHtml(String docAllPath, String imagePath, String htmlImgPath)
			throws IOException, ParserConfigurationException, TransformerException {
		File file = new File(docAllPath);

		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(file));
		WordToHtmlConverter converter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

		final List<String> list = new ArrayList<String>();

		converter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
					float heightInches) {
				String path = UUID.randomUUID()
						+ suggestedName.substring(suggestedName.lastIndexOf(CommonSymbolicConstant.POINT));
				String path2 = htmlImgPath + File.separator + UUID.randomUUID()
						+ suggestedName.substring(suggestedName.lastIndexOf(CommonSymbolicConstant.POINT));
				list.add(path);
				// 返回图片路径(html)中的
				return htmlImgPath + File.separator + path;
			}
		});
		converter.processDocument(wordDocument);

		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				try {
					// 物理位置
					pics.get(i).writeImageContent(new FileOutputStream(imagePath + File.separator + list.get(i)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		StringWriter writer = new StringWriter();

		Transformer serializer = TransformerFactory.newInstance().newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, CodingConstant.CODING_UTF_8);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(new DOMSource(converter.getDocument()), new StreamResult(writer));
		writer.close();
		return writer.toString();
	}
}
