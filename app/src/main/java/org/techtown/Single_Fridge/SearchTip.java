package org.techtown.Single_Fridge;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.Single_Fridge.Requests.BlockRequest;
import org.techtown.Single_Fridge.Requests.HoneyTipRequest;
import org.techtown.Single_Fridge.Requests.Mytip_rm;
import org.techtown.Single_Fridge.R;

public class SearchTip extends AppCompatActivity {

    Integer tip_id;
    String ing_name, howto, UserId, tipuser, tipuser_name;
    TextView text_ingname, tip, text_user;
    ImageButton button_back;
    Button edit_button, remove_button;
    ImageView report_button, block_button;
    final int REQUEST_CODE = 1103;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        SharedPreferences auto = this.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        UserId = auto.getString("Id", null);
        Intent intent = getIntent();
        tip_id = intent.getIntExtra("id",0);
        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_button = findViewById(R.id.button_edit);
        edit_button.setVisibility(View.GONE);
        remove_button = findViewById(R.id.button_delete);
        remove_button.setVisibility(View.GONE);

        report_button = findViewById(R.id.button_report_3);
        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchTip.this, Report_post.class);
                intent.putExtra("post_id", Integer.toString(tip_id));
                intent.putExtra("post_user_id", tipuser);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        block_button = findViewById(R.id.button_block_3);
        block_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTip.this);
                builder.setMessage("정말로 차단하시겠습니까? 차단 해제를 원하실 경우 rememberus320@gmail.com으로 문의 부탁드립니다.").setCancelable(false);
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Response.Listener<String> responseListener = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "사용자 차단이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        setResult(RESULT_OK, intent);
                                        finish();
                                        return;
                                    } else {
                                        return;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        };
                        //서버로 요청
                        BlockRequest blockRequest = new BlockRequest(auto.getString("Id", null) , tipuser, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SearchTip.this);
                        queue.add(blockRequest);
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("사용자 차단");
                alert.show();
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchTip.this, My_tip_edit.class);
                intent.putExtra("id", tip_id);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alt_bld=new AlertDialog.Builder(view.getContext());
                alt_bld.setMessage("팁 게시물을 삭제하시겠습니까?").setCancelable(false)
                    .setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                Response.Listener<String> responseListener_mytip_rm = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");

                                            if (success) {//성공시
                                                Intent intent = new Intent();
                                                setResult(RESULT_OK, intent);
                                                finish();
                                                return;
                                            } else {//실패시
                                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                };
                                Mytip_rm mytip_rm = new Mytip_rm(tip_id.toString(),UserId,responseListener_mytip_rm);
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(mytip_rm);
                            }
                        })
                    .setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert= alt_bld.create();
                alert.setTitle("팁 삭제");
                alert.show();
            }
        });

        Response.Listener<String> responseListener_tip = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {//성공시
                        ing_name = jsonObject.getString("Tip_name");
                        howto = jsonObject.getString("Tip_detail");
                        tipuser = jsonObject.getString("Tip_userid");
                        tipuser_name = jsonObject.getString("User_name");

                        if(tipuser.equals(UserId)){
                            edit_button.setVisibility(View.VISIBLE);
                            remove_button.setVisibility(View.VISIBLE);
                            report_button.setVisibility(View.GONE);
                            block_button.setVisibility(View.GONE);
                        }
                        text_ingname=findViewById(R.id.ingname);
                        text_ingname.setText(ing_name);

                        text_user = findViewById(R.id.writer);
                        text_user.setText(tipuser_name);

                        tip = findViewById(R.id.tip);
                        tip.setText(howto);
                        tip.setMovementMethod(new ScrollingMovementMethod());

                        return;
                    } else {//실패시
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };
        HoneyTipRequest honeyTipRequest = new HoneyTipRequest(tip_id.toString(),responseListener_tip);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(honeyTipRequest);
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

            Response.Listener<String> responseListener_tip = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {//성공시
                            ing_name = jsonObject.getString("Tip_name");
                            howto= jsonObject.getString("Tip_detail");
                            tipuser= jsonObject.getString("Tip_userid");
                            if(tipuser.equals(UserId)){
                                edit_button.setVisibility(View.VISIBLE);
                                remove_button.setVisibility(View.VISIBLE);
                            }
                            text_ingname=findViewById(R.id.ingname);
                            text_ingname.setText(ing_name);

                            tip = findViewById(R.id.tip);
                            tip.setText(howto);


                            return;
                        } else {//실패시
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            };
            HoneyTipRequest honeyTipRequest = new HoneyTipRequest(tip_id.toString(),responseListener_tip);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(honeyTipRequest);

            button_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}