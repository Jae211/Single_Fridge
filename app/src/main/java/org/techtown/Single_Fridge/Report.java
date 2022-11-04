//Report.java

package org.techtown.Single_Fridge;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.Single_Fridge.R;
import org.techtown.Single_Fridge.Requests.HomeRequest_ing;
import org.techtown.Single_Fridge.Requests.Myrecipe_delete;
import org.techtown.Single_Fridge.Requests.RegisterRequest;
import org.techtown.Single_Fridge.Requests.ReportRequest;

import java.util.ArrayList;

public class Report extends Activity {
    Button submit_button;
    ImageView back_button;
    RadioGroup radioGroup;
    Integer reason, reporter, reportee, comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        SharedPreferences auto = this.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        reporter = Integer.parseInt(auto.getString("Id", null));
        comment = intent.getIntExtra("comment_id", 0);
        reportee = intent.getIntExtra("comment_user_id", 0);

        back_button = findViewById(R.id.button_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //라디오 그룹 설정
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        submit_button = findViewById(R.id.button_report_submit);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {//성공시
                                Toast.makeText(getApplicationContext(), "신고 성공", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            } else {//실패시
                                Toast.makeText(getApplicationContext(), "신고 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "신고 예외 1", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                };
                //서버로 요청
                ReportRequest reportRequest = new ReportRequest(comment.toString(), reporter.toString(), reportee.toString(), reason.toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(Report.this);
                queue.add(reportRequest);

                Toast.makeText(Report.this, "신고 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.r_btn1){
                reason=1;
            }
            else if(i == R.id.r_btn2){
                reason=2;
            }
            else if(i == R.id.r_btn3){
                reason=3;
            }
            else if(i == R.id.r_btn4){
                reason=4;
            }
            else if(i == R.id.r_btn5){
                reason=5;
            }
            else if(i == R.id.r_btn6){
                reason=6;
            }
            else if(i == R.id.r_btn7){
                reason=7;
            }
            else if(i == R.id.r_btn8){
                reason=8;
            }
        }
    };

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }
}