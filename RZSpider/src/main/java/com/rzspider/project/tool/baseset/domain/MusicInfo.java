package com.rzspider.project.tool.baseset.domain;

public class MusicInfo {
	/** 歌曲名 */
	private String title;
	/** 歌手 */
	private String author;
	/** 歌曲链接 */
	private String url;
	/** 歌曲封面链接 */
	private String pic;
	/** 歌曲歌词内容 */
	private String lrc;

	public String getLrc() {
		return lrc;
	}

	public void setLrc(String lrc) {
		this.lrc = lrc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "MusicInfo [title=" + title + ", author=" + author + ", url=" + url + ", pic=" + pic + ", lrc=" + lrc
				+ "]";
	}

}
