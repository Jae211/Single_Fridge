package org.techtown.Single_Fridge.Requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentlistRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http:ec2-3-39-106-112.ap-northeast-2.compute.amazonaws.com/Comment_list.php"; //호스팅 주소 + php

    private Map<String, String> map;

    public CommentlistRequest (String recipe_id, String user_id, Response.Listener<String> listener) { //문자형태로 보낸다는 뜻
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("recipe_id", recipe_id);
        map.put("user_id", user_id);
        Log.d("MyLog: ", "request 받음");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;

    }
}