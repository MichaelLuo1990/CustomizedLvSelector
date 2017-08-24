package com.michael.customizedlvdemo.qqstyle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;


import com.michael.customizedlvdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelluo on 17/8/17.
 *
 * @desc QQ风格样式列表
 */

public class QQStyleActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private QQStyleAdapter qqStyleAdapter;
    //remark：组数据与子节点数据依据实际情况更改为实体类对象解析（当前测试暂处理为String填充）
    private List<String> groupList;
    private List<List<String>> childList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_qq);
        initData();
        initView();
    }

    private void initView() {
        expandableListView = (ExpandableListView) findViewById(R.id.expendablelistview);
        expandableListView.setGroupIndicator(null);
        qqStyleAdapter = new QQStyleAdapter(QQStyleActivity.this,groupList,childList);
        expandableListView.setAdapter(qqStyleAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //组节点
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int gNum = i + 1;
            groupList.add("Group-" + gNum);
            //子节点
            List<String> childitem = new ArrayList<>();
            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            for (int j = 0; j < randomNum; j++) {
                int cNum = j + 1;
                childitem.add("Child-" + cNum);
                childList.add(childitem);
            }
        }
    }
}
