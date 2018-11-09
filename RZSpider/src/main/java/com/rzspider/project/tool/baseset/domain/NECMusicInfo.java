package com.rzspider.project.tool.baseset.domain;

public class NECMusicInfo extends MusicInfo {
	/**
	 * 歌曲id
	 */
	private String id;

	/**
	 * 歌曲时间
	 */
	private String musicTime;

	/**
	 * 专辑名称
	 */
	private String albumName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMusicTime() {
		return musicTime;
	}

	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public String toString() {
		return "NECMusicInfo [id=" + id + ", musicTime=" + musicTime + ", albumName=" + albumName + ", getLrc()="
				+ getLrc() + ", getTitle()=" + getTitle() + ", getAuthor()=" + getAuthor() + ", getUrl()=" + getUrl()
				+ ", getPic()=" + getPic() + "]";
	}

}
