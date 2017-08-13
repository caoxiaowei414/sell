package com.ldlood.controller;

import com.ldlood.config.ProjectUrlConfig;
import com.ldlood.constant.CookieConstant;
import com.ldlood.constant.RedisConstant;
import com.ldlood.dataobject.SellerInfo;
import com.ldlood.enums.ResultEnum;
import com.ldlood.service.SellerService;
import com.ldlood.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ldlood on 2017/8/13.
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse httpServletResponse,
                              Map<String, Object> map) {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);

        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        CookieUtil.set(httpServletResponse, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
