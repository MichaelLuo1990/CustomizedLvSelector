package com.michael.customizedlvdemo.dialogstyle.multiple;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.michael.customizedlvdemo.R;

import java.util.List;


/**
 * Created by michaelluo on 17/5/31.
 *
 * @desc 单项itemCheckBox适配器
 */

public class MyCBAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyCBEntity> myCBEntitys;
    private SparseArray<View> lmap = new SparseArray<>();

    public MyCBAdapter(Context context, List<MyCBEntity> myCBEntitys) {
        this.myCBEntitys = myCBEntitys;
        this.mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myCBEntitys.size();
    }

    @Override
    public MyCBEntity getItem(int position) {
        return myCBEntitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyCBAdapter.ViewHolder holder;
        View view;
        if (lmap.get(position) == null) {
            view = mInflater.inflate(R.layout.item_dialog_checkbox, null);
            holder = new MyCBAdapter.ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.dialog_checkbox_item_img);
            holder.name = (TextView) view.findViewById(R.id.dialog_checkbox_item_name);
            holder.checkBox = (CheckBox) view.findViewById(R.id.dialog_checkbox_item_checkbox);
            lmap.put(position, view);
            view.setTag(holder);
        } else {
            view = lmap.get(position);
            holder = (MyCBAdapter.ViewHolder) view.getTag();
        }
        if (myCBEntitys != null && myCBEntitys.size() > 0) {
            holder.name.setText(myCBEntitys.get(position).getName().toString());
            holder.checkBox.setChecked(myCBEntitys.get(position).isChecked());
            holder.img.setBackgroundResource(R.mipmap.ic_launcher);
        }
        return view;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView name;
        public CheckBox checkBox;
    }

}
