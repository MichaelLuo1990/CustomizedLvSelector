package com.michael.customizedlvdemo.checkboxstyle.horizontal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.michael.customizedlvdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by michaelluo on 17/8/07.
 *
 * @desc 横向层级菜单列表（美团、饿了么点餐）样式-双listview
 */

public class CBHorizontalListActivity extends Activity {

    private ListView lvLeftReceiver;
    private CBHorizontalLeftAdapter leftAdapter;
    private ListView lvRightReceiver;
    private CBHorizontalRightAdapter rightAdapter;
    private int currentSelectIndex = 0;//当前选中的父级位置(默认选中显示第一项)
    private CheckBox cbAll;
    private RelativeLayout rlAllOption;//全选
    private List<CBHorizontalGroupEntity> cbHorizontalGroupEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmd_receiver_one);
        initData();
        initView();
    }

    /**
     * 初始化测试数据
     */
    private void initData() {
        cbHorizontalGroupEntities = new ArrayList<>();
        int GroupNum = new Random().nextInt(10) + 1;
        for (int i = 0; i < GroupNum; i++) {
            CBHorizontalGroupEntity cbHorizontalGroupEntity = new CBHorizontalGroupEntity();
            cbHorizontalGroupEntity.setGroupName("Group-" + (i+1));
            List<CBHorizontalChildEntity> cbHorizontalChildEntities = new ArrayList<>();
            //子节点
            int childNum = new Random().nextInt(10) + 1;
            for (int j = 0; j < childNum; j++) {
                CBHorizontalChildEntity cbHorizontalChildEntity = new CBHorizontalChildEntity();
                cbHorizontalChildEntity.setChildName("Child-" + (j+1));
                cbHorizontalChildEntity.setChildDesc("childDesc-" + (j+1));
                cbHorizontalChildEntities.add(cbHorizontalChildEntity);
            }
            cbHorizontalGroupEntity.setCbHorizontalChildEntities(cbHorizontalChildEntities);
            cbHorizontalGroupEntities.add(cbHorizontalGroupEntity);
        }
    }

    private void initView() {
        lvLeftReceiver = (ListView) findViewById(R.id.lv_left_receiver);
        lvRightReceiver = (ListView) findViewById(R.id.lv_right_receiver);
        cbAll = (CheckBox) findViewById(R.id.cbAll);
        rlAllOption = (RelativeLayout) findViewById(R.id.rl_all_option);
        //加载适配
        leftAdapter = new CBHorizontalLeftAdapter(CBHorizontalListActivity.this, cbHorizontalGroupEntities);
        lvLeftReceiver.setAdapter(leftAdapter);
        //初始化显示子集列表（默认选中显示第一项）
        rightAdapter = new CBHorizontalRightAdapter(CBHorizontalListActivity.this, cbHorizontalGroupEntities, currentSelectIndex);
        lvRightReceiver.setAdapter(rightAdapter);
        initCheckedAllClickListener();
        initLeftLvItemClickListener();
    }

    /**
     * 全选事件监听
     */
    private void initCheckedAllClickListener() {
        //判定是否初始化全选
        cbAll.setChecked(rightAdapter.itemCheckIsCheckAll());
        //全选点击事件监听
        rlAllOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbAll.isChecked()) {
                    cbAll.setChecked(false);
                } else {
                    cbAll.setChecked(true);
                }
                for (int i = 0; i < cbHorizontalGroupEntities.size(); i++) {
                    cbHorizontalGroupEntities.get(i).setCheck(cbAll.isChecked());
                    for (int j = 0; j < cbHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().size(); j++) {
                        cbHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().get(j).setCheck(cbAll.isChecked());
                    }
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
        //是否全选监听事件
        rightAdapter.setOnClickCheckAllListener(new CBHorizontalRightAdapter.IClickCheckAllCallback() {

            @Override
            public void checkAll(boolean checkAll) {
                cbAll.setChecked(checkAll);
            }
        });
    }

    /**
     * 左边父级item点击事件监听
     */
    private void initLeftLvItemClickListener() {
        lvLeftReceiver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightAdapter.setCurrentSelectIndex(position);
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

}
