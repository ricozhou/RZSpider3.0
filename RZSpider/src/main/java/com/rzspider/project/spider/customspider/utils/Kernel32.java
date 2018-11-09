package com.rzspider.project.spider.customspider.utils;

import com.rzspider.common.constant.OtherConstant;
import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Kernel32 extends Library {
	public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary(OtherConstant.OTHER_KERNEL32, Kernel32.class);

	public long GetProcessId(Long hProcess);
}
