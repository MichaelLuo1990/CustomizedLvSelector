package com.michael.customizedlvdemo.checkboxstyle.horizontal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.michael.customizedlvdemo.R;

import java.util.List;

/**
 * Created by michaelluo on 17/08/07.
 *
 * @desc 层级菜单右侧adapter
 */
public class CBHorizontalRightAdapter extends BaseAdapter {
    private Context mContext;
    private List<CBHorizontalGroupEntity> cbHorizontalGroupEntities;
    private IClickCheckAllCallback clickCallback;
    private int currentSelectIndex;

    public CBHorizontalRightAdapter(Context context, List<CBHorizontalGroupEntity> cbHorizontalGroupEntities, int currentSelectIndex) {
        this.mContext = context;
        this.cbHorizontalGroupEntities = cbHorizontalGroupEntities;
        this.currentSelectIndex = currentSelectIndex;
    }

    public void setOnClickCheckAllListener(IClickCheckAllCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public int getCount() {
        return cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().size();
    }

    @Override
    public CBHorizontalChildEntity getItem(int position) {
        return cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolderChild viewHolderChild;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cmd_receiver_adapter_child_one, null);
            viewHolderChild = new ViewHolderChild();
            viewHolderChild.tvName = (TextView) view.findViewById(R.id.tv_child_name);
            viewHolderChild.tvDesc = (TextView) view.findViewById(R.id.tv_child_desc);
            viewHolderChild.cb = (CheckBox) view.findViewById(R.id.cbChild);
            viewHolderChild.rlChild = (RelativeLayout) view.findViewById(R.id.rl_child);
            view.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) view.getTag();
        }

        CBHorizontalChildEntity childModel = cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().get(position);
        viewHolderChild.tvName.setText(childModel.getChildName());
        viewHolderChild.tvDesc.setText(childModel.getChildDesc());
        viewHolderChild.cb.setChecked(childModel.isCheck());
        viewHolderChild.rlChild.setOnClickListener(new ChildCheckClickListener(viewHolderChild, currentSelectIndex, position));  //对layout监听
        return view;
    }


    public class ViewHolderChild {
        TextView tvName;
        TextView tvDesc;
        CheckBox cb;
        RelativeLayout rlChild;
    }

    /**
     * 子节点item点击事件，监听layout改变CheckBox，可放置aty中通过item监听实现
     */
    public class ChildCheckClickListener implements View.OnClickListener {
        ViewHolderChild cViewHolderChild;
        int gPosition;
        int cPosition;

        public ChildCheckClickListener(ViewHolderChild cViewHolderChild,int gPosition, int cPosition) {
            this.cViewHolderChild = cViewHolderChild;
            this.gPosition = gPosition;
            this.cPosition = cPosition;
        }

        @Override
        public void onClick(View view) {
            if(cViewHolderChild.cb.isChecked()) {
                cViewHolderChild.cb.setChecked(false);
            } else {
                cViewHolderChild.cb.setChecked(true);
            }
            //设置child的选中状态
            cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().get(cPosition).setCheck(cViewHolderChild.cb.isChecked());
            //设置该child所属group的选中状态
            cbHorizontalGroupEntities.get(currentSelectIndex).setCheck(childCheckIsGroupCheck(gPosition));
            //刷新数据
            notifyDataSetChanged();
            // 通知Activity 全选是否选中
            notifyAtyIsCheckedAll();
        }
    }

    public static interface IClickCheckAllCallback {
        void checkAll(boolean checkAll);
    }

    /**
     * 通知Activity 是否全选
     */
    public void notifyAtyIsCheckedAll() {
        if (clickCallback != null)
            clickCallback.checkAll(itemCheckIsCheckAll());
    }

    /**
     * item 选中改变后，判断是否全选，依据子节点判定
     *
     * @return
     */
    public boolean itemCheckIsCheckAll() {
        for (int i = 0; i < cbHorizontalGroupEntities.size(); i++) {
            for (int j = 0; j < cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().size(); j++) {
                if (!cbHorizontalGroupEntities.get(currentSelectIndex).getCbHorizontalChildEntities().get(j).isCheck()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * child 选中改变后，判断其group是否选中， 在child选中状态改变后调用此方法
     * @param gPosition
     * @return
     */
    public boolean childCheckIsGroupCheck(int gPosition) {
        for (int i = 0; i < cbHorizontalGroupEntities.get(gPosition).getCbHorizontalChildEntities().size(); i++) {
            if (!cbHorizontalGroupEntities.get(gPosition).getCbHorizontalChildEntities().get(i).isCheck()) {
                return false;
            }
        }
        return true;
    }

    public int getCurrentSelectIndex() {
        return currentSelectIndex;
    }

    public void setCurrentSelectIndex(int currentSelectIndex) {
        this.currentSelectIndex = currentSelectIndex;
    }

    //    public CBHorizontalGroupEntity getCBHorizontalGroupModel() {
//        return CBHorizontalGroupEntity;
//    }
//
//    public void setCBHorizontalGroupModel(CBHorizontalGroupEntity CBHorizontalGroupEntity) {
//        this.CBHorizontalGroupEntity = CBHorizontalGroupEntity;
//    }
}
