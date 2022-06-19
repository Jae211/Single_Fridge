//Random_recipe.java
package org.techtown.Single_Fridge.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Random_recipe extends StringRequest {

    final static private String URL = "http:ec2-3-39-106-112.ap-northeast-2.compute.amazonaws.com/Random_recipe.php"; //호스팅 주소 + php
    private Map<String, String> map;
    public Random_recipe(String recipe_num,  Response.Listener<String> listener) { //문자형태로 보낸다는 뜻
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("recipe_num", recipe_num);
        //Log.d("My Log: ","request 보냄");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}