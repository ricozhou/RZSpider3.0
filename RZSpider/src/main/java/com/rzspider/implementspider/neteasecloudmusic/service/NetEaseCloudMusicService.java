package com.rzspider.implementspider.neteasecloudmusic.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rzspider.implementspider.neteasecloudmusic.utils.NetEaseCloudMusicUtils;

import sun.misc.BASE64Encoder;

public class NetEaseCloudMusicService {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.10 Safari/537.36";

	// 根据歌曲名搜索
	public String getNECMDataByMusicName(String musicName, int pageNum) throws Exception {
		if (musicName == null) {
			return null;
		}
		if (pageNum < 2) {
			pageNum = 2;
		}
		if (pageNum > 1000) {
			pageNum = 1000;
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String first_param = "{\"s\":\"" + musicName
				+ "\",\"type\":\"1\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"" + pageNum
				+ "\",\"csrf_token\":\"\"}";
		String secKey = "FFFFFFFFFFFFFFFF";
		// 两遍ASE加密
		String encText = aesEncrypt(aesEncrypt(first_param, "0CoJUm6Qyw8W8jud"), secKey);
		String encSecKey = rsaEncrypt();
		HttpPost httpPost = new HttpPost("https://music.163.com/weapi/cloudsearch/get/web?csrf_token=");
		httpPost.addHeader("Referer", "http://music.163.com/");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0");
		List<NameValuePair> ls = new ArrayList<NameValuePair>();
		ls.add(new BasicNameValuePair("params", encText));
		ls.add(new BasicNameValuePair("encSecKey", encSecKey));
		UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(ls, "utf-8");
		httpPost.setEntity(paramEntity);
		response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String json = null;
		if (entity != null) {
			json = EntityUtils.toString(entity, "utf-8").toString();
		}
		response.close();
		httpclient.close();
		return json;
	}

	// 参考网络
	/**
	 * ASE-128-CBC加密模式可以需要16位
	 *
	 * @param src
	 *            加密内容
	 * @param key
	 *            密钥
	 * @return
	 */
	public static String aesEncrypt(String src, String key) throws Exception {
		String encodingFormat = "UTF-8";
		String iv = "0102030405060708";
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		// 使用CBC模式，需要一个向量vi，增加加密算法强度
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(src.getBytes(encodingFormat));
		return new BASE64Encoder().encode(encrypted);
	}

	public static String rsaEncrypt() {
		String secKey = "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";
		return secKey;
	}

	// url
	public String getNECMusicUrlById(int id) {
		return "http://music.163.com/song/media/outer/url?id=" + id + ".mp3";
	}

	// 歌词
	// 参考zb代码
	public String getNECMusicLyricById(int id) {
		String url = "https://music.163.com/api/song/lyric?os=pc&id=" + id + "&lv=-1&kv=-1&tv=-1";
		String lyricJson = null;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		HttpURLConnection connection;
		try {
			connection = NetEaseCloudMusicUtils.openConnection(url);

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setDoOutput(true);

			if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				lyricJson = NetEaseCloudMusicUtils.readInput(connection.getInputStream(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return NetEaseCloudMusicUtils.formatLyricJson(lyricJson);

	}

}
