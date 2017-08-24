package com.michael.customizedlvdemo.qqstyle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.michael.customizedlvdemo.R;

import java.util.List;

/**
 * Created by michaelluo on 17/8/17.
 *
 * @desc QQ风格列表适配器
 */

public class QQStyleAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;//组-数据源
    private List<List<String>> childList;//子节点-数据源

    public QQStyleAdapter(Context context, List<String> groupList, List<List<String>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition).toString();
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition).toString();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            viewHolderGroup = new ViewHolderGroup();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qq_style_group, null);
            viewHolderGroup.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            viewHolderGroup.tvGroupName = (TextView) convertView.findViewById(R.id.tv_group_name);
            viewHolderGroup.tvGroupNumber = (TextView) convertView.findViewById(R.id.tv_group_number);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        //自定义展开收起图标
        if (isExpanded) {
            viewHolderGroup.ivArrow.setBackgroundResource(R.drawable.img_arrow_down);
        } else {
            viewHolderGroup.ivArrow.setBackgroundResource(R.drawable.img_arrow_right);
        }
        viewHolderGroup.tvGroupName.setText(groupList.get(groupPosition));
        viewHolderGroup.tvGroupNumber.setText("0/" + childList.get(groupPosition).size());
        return convertView;
    }

    public class ViewHolderGroup {
        ImageView ivArrow;
        TextView tvGroupName;
        TextView tvGroupNumber;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if(convertView == null) {
            viewHolderChild = new ViewHolderChild();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_qq_style_child, null);
            viewHolderChild.tvChildName = (TextView) convertView.findViewById(R.id.tv_child_name);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
        viewHolderChild.tvChildName.setText(childList.get(groupPosition).get(childPosition));
        return convertView;
    }

    public class ViewHolderChild {
        TextView tvChildName;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
