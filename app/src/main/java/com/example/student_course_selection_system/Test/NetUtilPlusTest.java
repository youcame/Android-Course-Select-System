package com.example.student_course_selection_system.Test;

import com.example.student_course_selection_system.Util.NetUtilPlus;

import java.util.HashMap;
import java.util.Map;

public class NetUtilPlusTest {
    public static void main(String[] args) {
        String url = "login";
        String method = "POST";

        // 构建请求参数
        Map<String, String> params = new HashMap<>();
        params.put("username", "zhangsan");
        params.put("password", "yyyzzz");

        try {
            // 发送请求并获取响应
            String response = NetUtilPlus.net(url, params, method);

            // 输出响应内容
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}