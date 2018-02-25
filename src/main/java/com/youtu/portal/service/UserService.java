package com.youtu.portal.service;
/**
 *@author:王贤锐
 *@date:2018年1月24日  下午3:43:39
**/

import com.youtu.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);
}
