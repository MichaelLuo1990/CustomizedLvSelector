package com.michael.customizedlvdemo.dialogstyle.multiple;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.michael.customizedlvdemo.R;
import com.michael.customizedlvdemo.utils.DesityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelluo on 17/5/31.
 * @desc 多选弹窗公共dialog
 */
public class MyCBDialog extends Dialog {

    public MyCBDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context context;
        private View vCheckBoxDialogLayout;
        private MyCBDialog myCBDialog;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private ListView listView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private OnItemClickListener onItemClickListener;
        private CheckBox checkAll;

        private List<MyCBEntity> myCBEntities;
        private boolean isShowTopSelectAllCheckBox;//是否显示顶部选择所有CheckBox

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置选择所有按键状态
         * @param isCheckedAll   通过当前items总数与已选总数比较？相等时为选中：不等时不选中
         * @return
         */
        public Builder setCheckAllStatus(boolean isCheckedAll) {
            if (isCheckedAll) {
                checkAll.setChecked(true);
            } else {
                checkAll.setChecked(false);
                if (myCBEntities.size() > 0) {
                    //遍历控制当前item是否选中
                    for (int i = 0; i < myCBEntities.size(); i++) {
                        CheckBox itemCheckBox = (CheckBox) listView.getAdapter()
                                .getView(i, null, null).findViewById(R.id.dialog_checkbox_item_checkbox);
                        if (myCBEntities.get(i).isChecked()) {
                            itemCheckBox.setChecked(true);
                        } else {
                            itemCheckBox.setChecked(false);
                        }
                    }
                }
            }
            return this;
        }

        /**
         * 设置标题显示内容
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置确定按键
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置取消按键
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param myCBEntities               item实体类列表对象
         * @param onItemClickListener        item点击事件
         * @param isShowTopSelectAllCheckBox 是否显示顶部选择全部多选框
         * @return
         */
        public Builder setMultiChoiceItems(List<MyCBEntity> myCBEntities,
                                           OnItemClickListener onItemClickListener, boolean isShowTopSelectAllCheckBox) {
            this.myCBEntities = myCBEntities;
            this.onItemClickListener = onItemClickListener;
            this.isShowTopSelectAllCheckBox = isShowTopSelectAllCheckBox;
            return this;
        }

        /**
         * 初始化构造创建dialog对象
         * @return
         */
        public MyCBDialog create() {
            //动态加载布局
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myCBDialog = new MyCBDialog(context, R.style.Dialog);
            vCheckBoxDialogLayout = inflater.inflate(R.layout.layout_dialog_checkbox, null);
            myCBDialog.addContentView(vCheckBoxDialogLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            dialogCenterController();//弹窗items控制（先加载引用listview实例）
            dialogTopController();//弹窗顶部控制
            dialogBottomController();//弹窗底部控制
            myCBDialog.setContentView(vCheckBoxDialogLayout);
            return myCBDialog;
        }

        /**
         * 是否选中所有状态
         */
        public boolean isCheckedAll() {
            int count = listView.getAdapter().getCount();
            if (count <= 0) return false;
            for (int i = 0; i < myCBEntities.size(); i++) {
                if(!myCBEntities.get(i).isChecked()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 弹窗顶部控制器
         */
        private void dialogTopController() {
            // 全选CheckBox
            checkAll = (CheckBox) vCheckBoxDialogLayout.findViewById(R.id.chk_selectall);
            // 设置标题文字
            TextView multichoicTitle = (TextView) vCheckBoxDialogLayout.findViewById(R.id.multichoic_title);
            multichoicTitle.setText(title);
            // 是否显示顶部选择所有CheckBox
            if (isShowTopSelectAllCheckBox) {
                final int count = listView.getAdapter().getCount();
                // 初始化选择所有选项状态(根据items是否所有选中)
                if (count > 0) {
                    List<String> itemsIsCheckeds = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        CheckBox itemCheckBox = (CheckBox) listView.getAdapter()
                                .getView(i, null, null).findViewById(R.id.dialog_checkbox_item_checkbox);
                        itemsIsCheckeds.add(String.valueOf(itemCheckBox.isChecked()));
                    }
                    if (itemsIsCheckeds.contains("false")) {
                        checkAll.setChecked(false);
                    } else {
                        checkAll.setChecked(true);
                    }
                }
                // 顶部选择所有checkbox控制监听
                checkAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkAll.isChecked()) {
                            for (int i = 0; i < count; i++) {
                                CheckBox itemCheckBox = (CheckBox) listView.getAdapter()
                                        .getView(i, null, null).findViewById(R.id.dialog_checkbox_item_checkbox);
                                itemCheckBox.setChecked(true);
                            }
                        } else {
                            for (int i = 0; i < count; i++) {
                                CheckBox itemCheckBox = (CheckBox) listView.getAdapter()
                                        .getView(i, null, null).findViewById(R.id.dialog_checkbox_item_checkbox);
                                itemCheckBox.setChecked(false);
                            }
                        }
                        for (int i = 0; i < myCBEntities.size(); i++) {
                            myCBEntities.get(i).setChecked(checkAll.isChecked());
                        }
                    }
                });

            } else {
                checkAll.setVisibility(View.GONE);
            }
        }
        /**
         * 弹窗中间items控制器：item具体逻辑在adapter中实现
         */
        private void dialogCenterController() {
            //listview添加item项与适配器
            final MyCBAdapter myCBAdapter = new MyCBAdapter(context, myCBEntities);
            listView = (ListView) vCheckBoxDialogLayout.findViewById(R.id.multichoiceList);
            listView.setAdapter(myCBAdapter);
            //获取子item高度总和->动态设置listview高度
            int itemsHeightPx = 0;
            int singgleItemHeightPx = 0;
            ListAdapter listAdapter = listView.getAdapter();
            if(listView.getAdapter().getCount() > 0) {
                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    View item = listAdapter.getView(i, null, null);
                    item.measure(0, 0);
                    singgleItemHeightPx = item.getMeasuredHeight();
                    itemsHeightPx += singgleItemHeightPx;
                }
            }
            int itemsHeightDp = DesityUtil.px2dip(context, itemsHeightPx);
            int singgleItemHeightDp = DesityUtil.px2dip(context, singgleItemHeightPx);
//            Toast.makeText(context, "itemsHeightDp->" + itemsHeightDp, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "singgleItemHeightDp->" + singgleItemHeightDp, Toast.LENGTH_SHORT).show();
            LayoutParams layoutParams = listView.getLayoutParams();
            if(itemsHeightDp <= singgleItemHeightDp) {
                layoutParams.height = DesityUtil.dip2px(context, singgleItemHeightDp);//设置最小高度(一条item的高度)
            } else if(100 < itemsHeightDp && itemsHeightDp < 300) {
                layoutParams.height = DesityUtil.dip2px(context, itemsHeightDp);//根据当前的items高度总和
            } else {
                layoutParams.height = DesityUtil.dip2px(context, 300);//设置最大高度300dp
            }
            listView.setLayoutParams(layoutParams);
            //item点击事件
            if (onItemClickListener != null) {
                listView.setOnItemClickListener(onItemClickListener);
            }
        }

        /**
         * 弹窗底部按键控制器
         */
        private void dialogBottomController() {
            // 确定按键相关状态判断
            Button positiveButton = (Button) vCheckBoxDialogLayout.findViewById(R.id.positiveButton);
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(myCBDialog, DialogInterface.BUTTON_POSITIVE);
                            myCBDialog.dismiss();
                        }
                    });
                } else {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myCBDialog.dismiss();
                        }
                    });
                }
            } else {
                positiveButton.setVisibility(View.GONE);
            }
            // 取消按键相关状态判断
            Button negativeButton = (Button) vCheckBoxDialogLayout.findViewById(R.id.negativeButton);
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(myCBDialog, DialogInterface.BUTTON_NEGATIVE);
                            myCBDialog.dismiss();
                        }
                    });
                } else {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myCBDialog.dismiss();
                        }
                    });
                }
            } else {
                negativeButton.setVisibility(View.GONE);
            }
        }
    }
}
