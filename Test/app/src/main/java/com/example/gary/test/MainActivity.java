package com.example.gary.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends Activity {

    private Button button01;

    private TextView textView01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/

        button01 = (Button)findViewById(R.id.get_record);

        textView01 = (TextView)findViewById(R.id.TextView01);

        button01.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                TableLayout user_list = (TableLayout) findViewById(R.id.user_list);
                user_list.setStretchAllColumns(true);
                TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams view_layout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                try {
                    String result = DBConnector.executeQuery("SELECT * FROM sdn");

                /*
                    SQL 結果有多筆資料時使用JSONArray
                    只有一筆資料時直接建立JSONObject物件
                    JSONObject jsonData = new JSONObject(result);
                */

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        TableRow tr = new TableRow(MainActivity.this);
                        tr.setLayoutParams(row_layout);
                        tr.setGravity(Gravity.CENTER_HORIZONTAL);

                        TextView user_acc = new TextView(MainActivity.this);
                        user_acc.setText("1");
                        user_acc.setLayoutParams(view_layout);

                    textView01.setText("Stay Hungry, Stay Foolish. ");

                        TextView user_pwd = new TextView(MainActivity.this);
                        user_pwd.setText("2");
                        user_pwd.setLayoutParams(view_layout);

                        tr.addView(user_acc);
                        tr.addView(user_pwd);
                        user_list.addView(tr);
                    }
                } catch (Exception e) {
                    // Log.e("log_tag", e.toString());
                }
            }
        });

    }
}
