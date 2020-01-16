package com.ksh.heart.common.utils;

import com.alibaba.fastjson.JSON;
import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.exception.HeartException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
            writer.close();
        } catch (IOException e) {
            throw new HeartException(RetEnum.WRITE_ERROR);
        }
    }
}
