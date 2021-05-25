package com.guojianyong.utils;


import com.alibaba.fastjson.JSON;
import com.guojianyong.model.ServiceResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ControllerUtils {


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

