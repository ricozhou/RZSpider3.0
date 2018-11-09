package com.rzspider.project.blog.blogcontent.utils;

import java.io.PrintWriter;

/**
 * @author ricozhou
 * @date Oct 17, 2018 5:46:16 PM
 * @Desc 
 */
public class BlogUtil {
	 public static void writerToHtml(PrintWriter writer, String... msgs) {
	        if (null == writer) {
	            return;
	        }
	        for (String msg : msgs) {
	            writer.print("<script>printMessage('" + msg + "');</script>");
//	            writer.print( msg);
	            System.out.println(writer);
	            writer.flush();
	        }
	    }

	    public static void shutdownWriter(PrintWriter writer) {
	    	writerToHtml(writer, "爬取结束...", "shutdown");
	        if (null != writer) {
	            writer.close();
	        }
	    }
}
