//ReportRequest.java
package org.techtown.Single_Fridge.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReportRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http:ec2-3-39-106-112.ap-northeast-2.compute.amazonaws.com/Report.php"; //호스팅 주소 + php

    private Map<String, String> map;

    public ReportRequest(String comment, String reporter, String reportee, String reason ,Response.Listener<String> listener) { //문자형태로 보낸다는 뜻
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("comment", comment);
        map.put("reporter", reporter);
        map.put("reportee", reportee);
        map.put("reason", reason);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;

    }
}