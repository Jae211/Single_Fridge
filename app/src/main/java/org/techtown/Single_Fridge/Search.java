package org.techtown.Single_Fridge;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.Single_Fridge.Requests.SearchRequest;
import org.techtown.Single_Fridge.R;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    ListView listView;
    CheckBox checkBox;
    String keyword;
    ImageButton button_back;
    TextView text_keyword, nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.listView_search);
        checkBox = findViewById(R.id.CheckBox_Vegan_search);
        nothing = findViewById(R.id.textview_nothing);
        nothing.setVisibility(View.GONE);

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Intent intent = getIntent();
        keyword = getIntent().getStringExtra("keyword");
        text_keyword=findViewById(R.id.textView_searchkey);
        text_keyword.setText(keyword);

        ArrayList<Info> InfoList = new ArrayList<Info>(), NotveganInfoList = new ArrayList<Info>();
        ArrayList<String> RecipeList = new ArrayList<String>(), NotveganRecipeList = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, RecipeList);
        listView.setAdapter(adapter);
        //???????????? ?????? ?????? : ????????? ??????, ?????????, ????????????

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("INFO");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = Integer.valueOf(item.getString("Recipe_id"));
                        String name = item.getString("Recipe_name");
                        int vegan = Integer.valueOf(item.getString("Recipe_vegan"));

                        InfoList.add(new Info(id, name, vegan));
                        RecipeList.add(name);
                    }
                    adapter.notifyDataSetChanged();
                    if(jsonArray.length()==0) {
                        listView.setVisibility(View.GONE);
                        nothing.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "?????? 1", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };
        SearchRequest searchRequest_ = new SearchRequest(keyword, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Search.this);
        queue.add(searchRequest_);

        checkBox.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {   //????????? ????????? ??? ..
                    NotveganInfoList.clear();   //??????????????? ?????????
                    NotveganRecipeList.clear();
                    for (int i = 0; i < InfoList.size(); i++) {
                        if (InfoList.get(i).getVegan() == 0) {  //????????? ?????? ?????? ?????? notveganlist??? ??????
                            NotveganInfoList.add(InfoList.get(i));
                            NotveganRecipeList.add(RecipeList.get(i));
                        }
                    }
                    for (int i = 0; i < NotveganInfoList.size(); i++) {   //????????? ?????? ?????? adapter??? ????????? list?????? ??????
                        InfoList.remove(NotveganInfoList.get(i));
                        RecipeList.remove(NotveganRecipeList.get(i));
                    }
                    adapter.notifyDataSetChanged();

                } else {    //?????? ??????????????? ?????? ?????? ??? ?????? .. veganlist??? ?????? ???????????? list??? ?????? ????????????
                    for (int i = 0; i < NotveganInfoList.size(); i++) {
                        InfoList.add(NotveganInfoList.get(i));
                        RecipeList.add(NotveganRecipeList.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //list ??? ???????????? ??????????????? ?????? ????????? view?????? ??????????????? ?????????
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Search.this, Detail.class);
                int recipeid = InfoList.get(position).getId();
                intent.putExtra("id", recipeid);
                startActivityResult.launch(intent);
                overridePendingTransition(R.anim.anim_slide_up_enter, R.anim.anim_slide_maintain);
            }
            ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Intent intent = getIntent();
                            finish();
                            overridePendingTransition(R.anim.anim_slide_down_enter, R.anim.anim_slide_down_exit);
                            startActivity(intent);
                        }
                    }
            );
        });
    }


    public class Info {
        /* ???????????? ????????? ?????? ?????? ????????? */

        int id;
        String name;
        int vegan;

        public Info(int id, String name, int vegan) {
            this.id = id;
            this.name = name;
            this.vegan = vegan;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVegan() {
            return vegan;
        }

        public void setVegan(int vegan) {
            this.vegan = vegan;
        }
    }

    @Override
    public void onBackPressed() {
        //??????????????? ????????? ??????
        return;
    }

}