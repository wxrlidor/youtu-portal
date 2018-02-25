package com.youtu.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.youtu.common.utils.CookieUtils;
import com.youtu.pojo.TbUser;
import com.youtu.portal.service.impl.UserServiceImpl;

/**
 * @author:王贤锐
 * @date:2018年1月24日 下午3:28:27
 **/
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;

	/**
	 * 拦截器执行之前处理，返回值决定是否执行，true表示执行，false表示不执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 在Handler执行之前处理
		// 判断用户是否登录
		// 从cookie中取token
		String token = CookieUtils.getCookieValue(request, "YOUTU_TOKEN");
		// 根据token换取用户信息，调用sso系统的接口。
		TbUser user = userService.getUserByToken(token);
		// 取不到用户信息
		if (null == user) {
			// 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
			response.sendRedirect(
					userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN + "?redirect=" + request.getRequestURL());
			// 返回false
			return false;
		}
		// 取到用户信息，放行
		//把用户信息存储到request中
		request.setAttribute("user", user);
		// 返回值决定handler是否执行。true：执行，false：不执行。
		return true;
	}

	/**
	 * 拦截器执行之后，返回ModelAndView之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 拦截器执行之后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
