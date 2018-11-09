package com.rzspider.project.commontool.toolrun.domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GifMsg {
	public List<BufferedImage> list = new ArrayList<BufferedImage>();
	public List<Integer> list2 = new ArrayList<Integer>();

	public List<BufferedImage> getList() {
		return list;
	}

	public void setList(List<BufferedImage> list) {
		this.list = list;
	}

	public List<Integer> getList2() {
		return list2;
	}

	public void setList2(List<Integer> list2) {
		this.list2 = list2;
	}

	@Override
	public String toString() {
		return "GifMsg [list=" + list + ", list2=" + list2 + "]";
	}
}
