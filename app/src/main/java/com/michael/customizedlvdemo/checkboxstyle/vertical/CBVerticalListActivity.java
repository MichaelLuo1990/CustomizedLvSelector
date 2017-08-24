package com.michael.customizedlvdemo.checkboxstyle.vertical;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.michael.customizedlvdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelluo on 17/7/14.
 *
 * @desc CheckBox样式（问卷or选项卡）-expanableListview
 */

public class CBVerticalListActivity extends AppCompatActivity implements View.OnClickListener {
    private ExpandableListView elv;
    private RelativeLayout rlAll;
    private CheckBox cbAll;
    private CBVerticalListAdapter adapter;
    private List<CBVerticalGroupEntity> cbVerticalGroupEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cb_vertical);
        initData();
        initView();
    }

    /**
     * 初始化测试数据
     */
    private void initData() {
        cbVerticalGroupEntities = new ArrayList<>();
        int randomGroup = new Random().nextInt(5) + 1;
        for (int i = 0; i < randomGroup; i++) {
            CBVerticalGroupEntity cbVerticalGroupEntity = new CBVerticalGroupEntity();
            cbVerticalGroupEntity.setGroupName("Group-" + (i + 1));
            //子节点
            List<CBVerticalChildEntity> cbVerticalChildEntities = new ArrayList<>();
            int randomChild = new Random().nextInt(5) + 1;
            for (int j = 0; j < randomChild; j++) {
                CBVerticalChildEntity cbVerticalChildEntity = new CBVerticalChildEntity();
                cbVerticalChildEntity.setChildName("Child-" + (j + 1));
                cbVerticalChildEntities.add(cbVerticalChildEntity);
            }
            cbVerticalGroupEntity.setCbVerticalChildEntities(cbVerticalChildEntities);
            cbVerticalGroupEntities.add(cbVerticalGroupEntity);
        }
    }

    private void initView() {
        rlAll = (RelativeLayout) findViewById(R.id.rl_all_option);
        cbAll = (CheckBox) findViewById(R.id.cb_all);
        rlAll.setOnClickListener(this);
        elv = (ExpandableListView) findViewById(R.id.elv);
        elv.setGroupIndicator(null);//隐藏原始箭头
        adapter = new CBVerticalListAdapter(CBVerticalListActivity.this, cbVerticalGroupEntities);
        elv.setAdapter(adapter);
        //判定是否初始化全选
        cbAll.setChecked(adapter.itemCheckIsCheckAll());
        //全选点击监听事件
        adapter.setOnClickCheckAllListener(new CBVerticalListAdapter.IClickCheckAllCallback() {
            @Override
            public void checkAll(boolean checkAll) {
                cbAll.setChecked(checkAll);
            }
        });
        //点击group item监听事件（展开/收起）
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView ivArrow = (ImageView) v.findViewById(R.id.iv_arrow);
                if (parent.isGroupExpanded(groupPosition)) {
                    ivArrow.setBackgroundResource(R.drawable.img_arrow_down);
                    elv.expandGroup(groupPosition);
                } else {
                    ivArrow.setBackgroundResource(R.drawable.img_arrow_right);
                    elv.collapseGroup(groupPosition);
                }
                return false;
            }
        });
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                parent.getchildv
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_all_option:
                if(cbAll.isChecked()) {
                    cbAll.setChecked(false);
                } else {
                    cbAll.setChecked(true);
                }
                for (int i = 0; i < cbVerticalGroupEntities.size(); i++) {
                    cbVerticalGroupEntities.get(i).setCheck(cbAll.isChecked());
                    for (int j = 0; j < cbVerticalGroupEntities.get(i).getCbVerticalChildEntities().size(); j++) {
                        cbVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(j).setCheck(cbAll.isChecked());
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

}
