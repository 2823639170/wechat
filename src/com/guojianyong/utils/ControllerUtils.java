package com.guojianyong.utils;


import com.alibaba.fastjson.JSON;
import com.guojianyong.model.ServiceResult;
import com.guojianyong.web.constant.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ControllerUtils {

    /**
     * 返回请求中method对应的RequestMethod枚举项
     * @param name
     * @return
     */
    public static RequestMethod valueOf(String name) {
        try {
            name = name.toUpperCase().replaceAll("\\.", "_");
            return RequestMethod.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            //此处异常表明请求参数没有匹配到任何服务,为无效请求
            e.printStackTrace();
            return RequestMethod.INVALID_REQUEST;
        }
    }


    /**
     * 用户将服务结果转换成json数据并返回客户端
     * @param resp
     * @param result
     * @throws IOException
     */
    public static void returnJsonObject(HttpServletResponse resp, ServiceResult result) throws IOException {
        JSON json = (JSON) JSON.toJSON(result);
        resp.getWriter().write(json.toJSONString());
    }
}

