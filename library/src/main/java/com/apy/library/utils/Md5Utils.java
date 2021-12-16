package com.apy.library.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Md5Utils {

	public static String md5(String original) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(original.getBytes(StandardCharsets.UTF_8));
		return byte2hex(md5.digest());
	}

	public static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp ;
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
			if (n < b.length - 1)
				hs.append("");
		}
		return hs.toString();
	}
}
