package com.example.gary.test;

/**
 * Created by gary on 2016/7/26.
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TestAndroidDBActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private Button button_get_record;// a raw button object.

    private void findViews() {
        button_get_record = (Button)findViewById(R.id.get_record);
    }// attach the raw button object to its entity.ENTITY RETURNED BY FUNTION FINDVIEWBYID.

    private void setListeners() {
        button_get_record.setOnClickListener(getDBRecord);
    }// then set this click event listener on the button object.


    // and we tell the button what to do whenever it's clicked.
    private Button.OnClickListener getDBRecord = new Button.OnClickListener() {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            TableLayout user_list = (TableLayout)findViewById(R.id.user_list);
            user_list.setStretchAllColumns(true);
            //get the layout entity, and do some tweaks.

            TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams view_layout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


            try {
                String result = DBConnector.executeQuery("SELECT * FROM user");
                /*
                    SQL 結果有多筆資料時使用JSONArray
                    只有一筆資料時直接建立JSONObject物件
                    //所以這裡假設有超過一條結果囉
                    JSONObject jsonData = new JSONObject(result);
                */
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    TableRow tr = new TableRow(TestAndroidDBActivity.this);
                    tr.setLayoutParams(row_layout);
                    tr.setGravity(Gravity.CENTER_HORIZONTAL);

                    // create textview objects for each row of the result.
                    TextView user_acc = new TextView(TestAndroidDBActivity.this);
                    user_acc.setText(jsonData.getString("account"));
                    user_acc.setLayoutParams(view_layout);

                    TextView user_pwd = new TextView(TestAndroidDBActivity.this);
                    user_pwd.setText(jsonData.getString("passwd"));
                    user_pwd.setLayoutParams(view_layout);

                    //add the TextView objects to the TableRow
                    tr.addView(user_acc);
                    tr.addView(user_pwd);
                    //add the TableRow to our TableLayout object: user_list.
                    // so it can finally be shown on the screen ??
                    user_list.addView(tr);
                }
            } catch(Exception e) {
                Log.e("log_tag", e.toString());
            }
        }
    };
}