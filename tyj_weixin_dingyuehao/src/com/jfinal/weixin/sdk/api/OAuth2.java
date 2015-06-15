package com.jfinal.weixin.sdk.api;

import java.util.Map;

import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.ParaMap;

public class OAuth2 {
	// url=https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	private static String url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";

	public String oAuth2(String Code) {
		final String appId = ApiConfig.getAppId();
		final String appSecret = ApiConfig.getAppSecret();
		Map<String, String> queryParas = ParaMap.create("appid", appId)
				.put("secret", appSecret).put("code", Code).getData();
		String json = HttpKit.get(url, queryParas);
		return (json);
	}
}
