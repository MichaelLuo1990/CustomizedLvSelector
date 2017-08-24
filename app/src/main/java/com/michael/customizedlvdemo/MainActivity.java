package com.michael.customizedlvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.michael.customizedlvdemo.checkboxstyle.horizontal.CBHorizontalListActivity;
import com.michael.customizedlvdemo.checkboxstyle.vertical.CBVerticalListActivity;
import com.michael.customizedlvdemo.dialogstyle.MainDialogActivity;
import com.michael.customizedlvdemo.qqstyle.QQStyleActivity;

/**
 * demo示例人口
 */
public class MainActivity extends AppCompatActivity {

    private ListView lvMain;
    private String[] stringDatas;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.lv_main);
        stringDatas = new String[]{"The template of QQ style", "Horizontal style with checkbox", "Vertical style with checkbox", "Dialog style of Checkbox and Radiobutton"};
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, stringDatas);
        lvMain.setAdapter(arrayAdapter);
        final Intent intent = new Intent();
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, QQStyleActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, CBHorizontalListActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, CBVerticalListActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, MainDialogActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
