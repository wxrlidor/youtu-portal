package com.youtu.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.pojo.TbUser;
import com.youtu.portal.service.UserService;

/**
 * 用户service
 * 
 * @author:王贤锐
 * @date:2018年1月24日 下午3:44:22
 **/
@Service
public class UserServiceImpl implements UserService {
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	public String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	/**
	 * 根据token调用SSO接口换取用户信息。返回TbUser对象，没有就返回null
	 */
	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			// 把json转换成TaotaoREsult
			YouTuResult result = YouTuResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
