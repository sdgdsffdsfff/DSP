package com.dsp.sso.encoder.sha;

import org.jasig.cas.authentication.handler.DefaultPasswordEncoder;

public class EncoderUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DefaultPasswordEncoder encoder = new DefaultPasswordEncoder("MD5");
		System.out.print("code :" +encoder.encode("123456"));

	}

}
