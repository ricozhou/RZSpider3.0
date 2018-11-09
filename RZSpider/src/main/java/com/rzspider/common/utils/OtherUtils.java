package com.rzspider.common.utils;

import java.util.Set;

import com.rzspider.common.constant.UserConstants;

//其它工具
public class OtherUtils {
	// 返回用户名
	public static boolean isManager(Set<String> rolekeyset) {
		if (rolekeyset != null && rolekeyset.size() > 0) {
			for (String key : rolekeyset) {
				if (UserConstants.USER_KEY_ADMIN.equals(key) || UserConstants.USER_KEY_SUPERADMIN.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	// 是否是测试管理员
	public static boolean isTestManager(Set<String> rolekeyset) {
		if (rolekeyset != null && rolekeyset.size() > 0) {
			for (String key : rolekeyset) {
				if (UserConstants.USER_KEY_TESTADMIN.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}
}
