package com.michael.customizedlvdemo.checkboxstyle.vertical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michael.customizedlvdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelluo on 17/7/14.
 *
 * @desc 带CheckBox-adapter
 */
public class CBVerticalListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<CBVerticalGroupEntity> CBVerticalGroupEntities;
    private IClickCheckAllCallback clickCallback;

    public CBVerticalListAdapter(Context context, List<CBVerticalGroupEntity> CBVerticalGroupEntities) {
        this.mContext = context;
        this.CBVerticalGroupEntities = CBVerticalGroupEntities;
    }

    public void setOnClickCheckAllListener(IClickCheckAllCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public int getGroupCount() {
        return CBVerticalGroupEntities == null ? 0 : CBVerticalGroupEntities.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return CBVerticalGroupEntities.get(i).getCbVerticalChildEntities() == null ? 0 : CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().size();
    }

    @Override
    public Object getGroup(int i) {
        return CBVerticalGroupEntities.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        ViewHolderGroup viewHolderGroup;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_checkbox_vertical_group, null);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.tv = (TextView) view.findViewById(R.id.tv_group_name);
            viewHolderGroup.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            view.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) view.getTag();
        }

        CBVerticalGroupEntity groupModel = CBVerticalGroupEntities.get(i);
        viewHolderGroup.tv.setText(groupModel.getGroupName());
        //自定义展开收起图标
        if (isExpanded) {
            viewHolderGroup.ivArrow.setBackgroundResource(R.drawable.img_arrow_down);
        } else {
            viewHolderGroup.ivArrow.setBackgroundResource(R.drawable.img_arrow_right);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderChild viewHolderChild;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_checkbox_vertical_child, null);
            viewHolderChild = new ViewHolderChild();
            viewHolderChild.rl = (RelativeLayout) view.findViewById(R.id.rl_child);
            viewHolderChild.cb = (CheckBox) view.findViewById(R.id.cb_child);
            viewHolderChild.tv = (TextView) view.findViewById(R.id.tv_child);
            view.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) view.getTag();
        }

        CBVerticalChildEntity childModel = CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(i1);
        viewHolderChild.tv.setText(childModel.getChildName());
        viewHolderChild.cb.setChecked(childModel.isCheck());

        viewHolderChild.rl.setOnClickListener(new ChildCheckClickListener(viewHolderChild, i, i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    public class ViewHolderGroup {
        TextView tv;
        ImageView ivArrow;
    }

    public class ViewHolderChild {
        RelativeLayout rl;
        TextView tv;
        CheckBox cb;
    }

    /**
     * 子节点layout点击事件监听
     */
    public class ChildCheckClickListener implements View.OnClickListener {
        ViewHolderChild cViewHolderChild;
        int gPosition;
        int cPosition;

        public ChildCheckClickListener(ViewHolderChild cViewHolderChild, int gPosition, int cPosition) {
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
            CBVerticalGroupEntities.get(gPosition).getCbVerticalChildEntities().get(cPosition).setCheck(cViewHolderChild.cb.isChecked());
            //设置该child所属group的选中状态
            CBVerticalGroupEntities.get(gPosition).setCheck(childCheckIsGroupCheck(gPosition));
            //刷新数据
            notifyDataSetChanged();
            // 通知Activity 全选是否选中
            if (clickCallback != null)
                clickCallback.checkAll(itemCheckIsCheckAll());
        }
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
        for (int i = 0; i < CBVerticalGroupEntities.size(); i++) {
            for (int j = 0; j < CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().size(); j++) {
                if (!CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(j).isCheck()) {
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
        for (int i = 0; i < CBVerticalGroupEntities.get(gPosition).getCbVerticalChildEntities().size(); i++) {
            if (!CBVerticalGroupEntities.get(gPosition).getCbVerticalChildEntities().get(i).isCheck()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取被选中的child列表Users对象
     *
     * @return
     */
    public List<CBVerticalChildEntity> getCheckedUsers() {
        List<CBVerticalChildEntity> users = new ArrayList<>();
        for (int i = 0; i < CBVerticalGroupEntities.size(); i++) {
            for (int j = 0; j < CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().size(); j++) {
                if (CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(j).isCheck()) {
                    CBVerticalChildEntity CBVerticalChildEntity = CBVerticalGroupEntities.get(i).getCbVerticalChildEntities().get(j);
                    users.add(CBVerticalChildEntity);
                }
            }
        }
        return users;
    }

}
