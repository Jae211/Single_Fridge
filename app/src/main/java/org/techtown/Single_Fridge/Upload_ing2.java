package org.techtown.Single_Fridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.techtown.Single_Fridge.R;

import java.util.ArrayList;

public class Upload_ing2 extends Activity {

    int count = 1;
    Button submit_button;
    ImageButton back_button;
    ImageButton add_button;
    ArrayList<LinearLayout> layouts = new ArrayList<>(7);
    ArrayList<String> Tname = new ArrayList<>(7), Tamount = new ArrayList<>(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload_ing2);

        for(int i=0; i<7; i++){
            int resID = getResources().getIdentifier("input" + (i+1), "id", getPackageName());
            layouts.add(findViewById(resID));
        }

        for(int i=1; i<7; i++){
            layouts.get(i).setVisibility(View.GONE);
        }

        back_button = findViewById(R.id.button_back);
        add_button = findViewById(R.id.imageButton);
        submit_button = findViewById(R.id.button_add_ing2);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), count, Toast.LENGTH_SHORT).show();
                count++;
                if(count==7){
                    add_button.setEnabled(false);
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(Upload_ing_2.this);
                    dialog = builder.setMessage("?????? ????????? ????????? 7???????????? ???????????????.").setPositiveButton("??????", null).create();
                    dialog.show();*/
                    Toast.makeText(getApplicationContext(), "?????? ????????? ????????? 7???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                } else {
                    layouts.get(count-1).setVisibility(View.VISIBLE);
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean reset = false;
                for(int i=1; i<count+1; ++i) {
                    int resID = getResources().getIdentifier("text_name" + i, "id", getPackageName());
                    EditText temp = findViewById(resID);
                    String tempname = temp.getText().toString();

                    int resID2 = getResources().getIdentifier("text_amount" + i, "id", getPackageName());
                    EditText temp2 = findViewById(resID2);
                    String tempamount = temp2.getText().toString();

                    //Toast.makeText(getApplicationContext(), i+" "+tempname+" "+tempamount, Toast.LENGTH_SHORT).show();

                    if(tempname.length()!=0 && tempamount.length()!=0){
                        //Toast.makeText(getApplicationContext(), i+"no null ", Toast.LENGTH_SHORT).show();
                        Tname.add(tempname);
                        Tamount.add(tempamount);
                    } else if (tempname.length()==0 && tempamount.length()==0){
                        if(count == 1)
                            Toast.makeText(getApplicationContext(), "????????? ?????? ??? ??? ?????? ???????????? ?????????.", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), i+"both null", Toast.LENGTH_SHORT).show();
                    } else {
                        //????????? reset=1 ?????? ????????? ?????????????????? reset=0?????? ?????? pair array??? ????????? ??????.
                        reset = true;
                        Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ?????? ???????????? ?????????.", Toast.LENGTH_SHORT).show();
                    }
                }

                if(reset == true) {     //?????????/?????? ??? ????????? ????????? ?????? ?????? ??????
                    Tname.clear();
                    Tamount.clear();
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("name", Tname);
                    intent.putExtra("amount", Tamount);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        //??????????????? ????????? ??????
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
