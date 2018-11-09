package com.rzspider.implementspider.blogmove.controller;

import java.io.PrintWriter;

import com.rzspider.project.blog.blogcontent.domain.Blogmove;

/**
 * @author ricozhou
 * @date Oct 18, 2018 4:26:09 PM
 * @Desc
 */
public class BlogMoveThread implements Runnable {
	public Blogmove blogMove;
	public BlogMoveSpiderController blogMoveSpiderController;

	public BlogMoveThread(Blogmove blogMove, BlogMoveSpiderController blogMoveSpiderController) {
		this.blogMove = blogMove;
		this.blogMoveSpiderController = blogMoveSpiderController;
	}

	@Override
	public void run() {
		blogMoveSpiderController.blogMoveController(blogMove);
	}

	public Blogmove getBlogMove() {
		return blogMove;
	}

}
