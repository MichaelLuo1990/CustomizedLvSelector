package com.michael.customizedlvdemo.dialogstyle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.customizedlvdemo.R;
import com.michael.customizedlvdemo.dialogstyle.multiple.MyCBDialog;
import com.michael.customizedlvdemo.dialogstyle.multiple.MyCBEntity;
import com.michael.customizedlvdemo.dialogstyle.single.MyRBDialog;
import com.michael.customizedlvdemo.dialogstyle.single.MyRBEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelluo on 17/8/23.
 *
 * @desc 弹窗样式选择/编辑器
 */

public class MainDialogActivity extends AppCompatActivity {

    private TextView tvMultiContent;
    private TextView tvSingleContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dialog);
        tvMultiContent = (TextView) findViewById(R.id.tv_multi_choice_content);
        tvSingleContent = (TextView) findViewById(R.id.tv_single_choice_content);
    }

    /**
     * 初始化测试数据
     */
    private List<MyCBEntity> initMultiData() {
        List<MyCBEntity> myCBEntities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyCBEntity myCBEntity = new MyCBEntity();
            myCBEntity.setName("item-" + (i + 1));
            myCBEntities.add(myCBEntity);
        }
        return myCBEntities;
    }

    /**
     * 显示多选弹窗
     *
     * @param view
     */
    public void showMultiChoiceDialog(View view) {
        final List<MyCBEntity> myCBEntities = initMultiData();
        final MyCBDialog.Builder myCheckBoxDialogBuilder = new MyCBDialog.Builder(this);
        MyCBDialog multiChoiceDialog = myCheckBoxDialogBuilder
                .setTitle("请选择")
                .setMultiChoiceItems(myCBEntities, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //控制item选中状态
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.dialog_checkbox_item_checkbox);
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                            myCBEntities.get(position).setChecked(false);
                        } else {
                            checkBox.setChecked(true);
                            myCBEntities.get(position).setChecked(true);
                        }
                        //设置选择所有按键状
                        myCheckBoxDialogBuilder.setCheckAllStatus(myCheckBoxDialogBuilder.isCheckedAll());
                    }
                }, true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = "您选择了: ";
                        if (myCBEntities != null && myCBEntities.size() > 0) {
                            for (int i = 0; i < myCBEntities.size(); i++) {
                                if (myCBEntities.get(i).isChecked()) {
                                    s += myCBEntities.get(i).getName() + "  ";
                                }
                            }
                        }
                        Toast.makeText(MainDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                        tvMultiContent.setText(s);
                    }
                })
                .setNegativeButton("取消", null).create();
        multiChoiceDialog.show();
    }

    /**
     * 初始化测试数据
     */
    private List<MyRBEntity> initSingleData() {
        List<MyRBEntity> myRBEntities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyRBEntity myRBEntity = new MyRBEntity();
            myRBEntity.setName("item-" + (i + 1));
            myRBEntities.add(myRBEntity);
        }
        return myRBEntities;
    }

    /**
     * 显示单选弹窗
     *
     * @param view
     */
    public void showSingleChoiceDialog(View view) {
        final List<MyRBEntity> myRBEntities = initSingleData();
        final MyRBDialog.Builder myBuilder = new MyRBDialog.Builder(this);
        MyRBDialog myRBDialog = myBuilder
                .setTitle("请选择")
                .setSingleChoiceItems(myRBEntities, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //清空单选状态
                        myBuilder.clearSingleOptionStatus();
                        //控制当前item选中状态
                        RadioButton radioButton = (RadioButton) view.findViewById(R.id.rb_item_dialog);
                        radioButton.setChecked(true);
                        myRBEntities.get(position).setChecked(true);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = "您选择了: ";
                        if (myRBEntities != null && myRBEntities.size() > 0) {
                            for (int i = 0; i < myRBEntities.size(); i++) {
                                if (myRBEntities.get(i).isChecked()) {
                                    s += myRBEntities.get(i).getName() + "  ";
                                }
                            }
                        }
                        Toast.makeText(MainDialogActivity.this, s, Toast.LENGTH_SHORT).show();
                        tvSingleContent.setText(s);
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        myRBDialog.show();
    }

}
