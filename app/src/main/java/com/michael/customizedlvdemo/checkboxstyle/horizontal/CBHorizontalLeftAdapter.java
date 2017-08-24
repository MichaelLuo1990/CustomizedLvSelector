package com.michael.customizedlvdemo.checkboxstyle.horizontal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.michael.customizedlvdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelluo on 17/08/07.
 *
 * @desc 层级菜单左侧adapter
 */
public class CBHorizontalLeftAdapter extends BaseAdapter {
    private Context mContext;
    private List<CBHorizontalGroupEntity> CBHorizontalGroupEntities;
    private IClickCheckAllCallback clickCallback;

    public CBHorizontalLeftAdapter(Context context, List<CBHorizontalGroupEntity> CBHorizontalGroupEntities) {
        this.mContext = context;
        this.CBHorizontalGroupEntities = CBHorizontalGroupEntities;
    }

    public void setOnClickCheckAllListener(IClickCheckAllCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public int getCount() {
        return CBHorizontalGroupEntities.size();
    }

    @Override
    public CBHorizontalGroupEntity getItem(int position) {
        return CBHorizontalGroupEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cmd_receiver_adapter_group_one, null);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.tvName = (TextView) view.findViewById(R.id.tv_group_name);
            viewHolderGroup.tvNum = (TextView) view.findViewById(R.id.tv_group_num);
            view.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) view.getTag();
        }
        viewHolderGroup.tvName.setText(CBHorizontalGroupEntities.get(position).getGroupName());
        viewHolderGroup.tvNum.setText("（" + CBHorizontalGroupEntities.get(position).getCbHorizontalChildEntities().size() + "人）");
        return view;
    }

    public class ViewHolderGroup {
        TextView tvName;
        TextView tvNum;
    }


    public static interface IClickCheckAllCallback {
        void checkAll(boolean checkAll);
    }

    /**
     * item 选中改变后，判断是否全选，依据子节点判定
     *
     * @return
     */
    public boolean itemCheckIsCheckAll() {
        for (int i = 0; i < CBHorizontalGroupEntities.size(); i++) {
            for (int j = 0; j < CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().size(); j++) {
                if (!CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().get(j).isCheck()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * child 选中改变后，判断其group是否选中， 在child选中状态改变后调用此方法
     *
     * @param gPosition
     * @return
     */
    public boolean childCheckIsGroupCheck(int gPosition) {
        for (int i = 0; i < CBHorizontalGroupEntities.get(gPosition).getCbHorizontalChildEntities().size(); i++) {
            if (!CBHorizontalGroupEntities.get(gPosition).getCbHorizontalChildEntities().get(i).isCheck()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否存在子节点
     * @return
     */
    public boolean isChildUsers() {
        for (int i = 0; i < CBHorizontalGroupEntities.size(); i++) {
            if (CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取被选中的child列表Users对象
     *
     * @return
     */
    public List<CBHorizontalChildEntity> getCheckedUsers() {
        List<CBHorizontalChildEntity> users = new ArrayList<>();
        for (int i = 0; i < CBHorizontalGroupEntities.size(); i++) {
            for (int j = 0; j < CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().size(); j++) {
                if (CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().get(j).isCheck()) {
                    CBHorizontalChildEntity CBHorizontalChildEntity = CBHorizontalGroupEntities.get(i).getCbHorizontalChildEntities().get(j);
                    users.add(CBHorizontalChildEntity);
                }
            }
        }
        return users;
    }

    public List<CBHorizontalGroupEntity> getCBHorizontalGroupEntities() {
        return CBHorizontalGroupEntities;
    }

    public void setCBHorizontalGroupEntities(List<CBHorizontalGroupEntity> CBHorizontalGroupEntities) {
        this.CBHorizontalGroupEntities = CBHorizontalGroupEntities;
    }
}
