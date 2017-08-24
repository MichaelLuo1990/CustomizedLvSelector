package com.michael.customizedlvdemo.dialogstyle.single;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.michael.customizedlvdemo.R;
import com.michael.customizedlvdemo.utils.DesityUtil;

import java.util.List;

/**
 * Created by michaelluo on 17/8/23.
 *
 * @desc 自定义单选dialog
 */

public class MyRBDialog extends Dialog{

    public MyRBDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context context;
        private View vDialogLayout;
        private MyRBDialog myRBDialog;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private ListView listView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private AdapterView.OnItemClickListener onItemClickListener;

        private List<MyRBEntity> myRBEntities;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置标题显示内容
         *
         * @param title
         * @return
         */
        public MyRBDialog.Builder setTitle(String title) {
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
        public MyRBDialog.Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
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
        public MyRBDialog.Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param myRBEntities               item实体类列表对象
         * @param onItemClickListener        item点击事件
         * @return
         */
        public MyRBDialog.Builder setSingleChoiceItems(List<MyRBEntity> myRBEntities,
                                                      AdapterView.OnItemClickListener onItemClickListener) {
            this.myRBEntities = myRBEntities;
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        /**
         * 初始化构造创建dialog对象
         * @return
         */
        public MyRBDialog create() {
            //动态加载布局
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myRBDialog = new MyRBDialog(context, R.style.Dialog);
            vDialogLayout = inflater.inflate(R.layout.layout_dialog_radiobutton, null);
            myRBDialog.addContentView(vDialogLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialogTopController();//弹窗顶部控制
            dialogCenterController();//弹窗items控制（先加载引用listview实例）
            dialogBottomController();//弹窗底部控制
            myRBDialog.setContentView(vDialogLayout);
            return myRBDialog;
        }

        /**
         * 清空单选状态
         */
        public void clearSingleOptionStatus() {
            if(myRBEntities.size() > 0) {
                //状态置空-FALSE状态
                for (int i = 0; i < myRBEntities.size(); i++) {
                    RadioButton radioButton = (RadioButton) listView.getAdapter()
                            .getView(i, null, null).findViewById(R.id.rb_item_dialog);
                    radioButton.setChecked(false);
                    myRBEntities.get(i).setChecked(false);
                }
            }
        }

        /**
         * 弹窗顶部控制器
         */
        private void dialogTopController() {
            // 设置标题文字
            TextView multichoicTitle = (TextView) vDialogLayout.findViewById(R.id.tv_title);
            multichoicTitle.setText(title);
        }

        /**
         * 弹窗中间items控制器：item具体逻辑在adapter中实现
         */
        private void dialogCenterController() {
            //listview添加item项与适配器
            final MyRBAdapter myCBAdapter = new MyRBAdapter(context, myRBEntities);
            listView = (ListView) vDialogLayout.findViewById(R.id.multichoiceList);
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
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
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
            Button positiveButton = (Button) vDialogLayout.findViewById(R.id.positiveButton);
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(myRBDialog, DialogInterface.BUTTON_POSITIVE);
                            myRBDialog.dismiss();
                        }
                    });
                } else {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myRBDialog.dismiss();
                        }
                    });
                }
            } else {
                positiveButton.setVisibility(View.GONE);
            }
            // 取消按键相关状态判断
            Button negativeButton = (Button) vDialogLayout.findViewById(R.id.negativeButton);
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(myRBDialog, DialogInterface.BUTTON_NEGATIVE);
                            myRBDialog.dismiss();
                        }
                    });
                } else {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            myRBDialog.dismiss();
                        }
                    });
                }
            } else {
                negativeButton.setVisibility(View.GONE);
            }
        }
    }

}
