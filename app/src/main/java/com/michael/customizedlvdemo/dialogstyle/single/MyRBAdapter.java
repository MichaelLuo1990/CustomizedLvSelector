package com.michael.customizedlvdemo.dialogstyle.single;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.michael.customizedlvdemo.R;

import java.util.List;

/**
 * Created by michaelluo on 17/8/23.
 *
 * @desc 单选弹窗适配器
 */

public class MyRBAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyRBEntity> myRBEntitys;
    private SparseArray<View> lmap = new SparseArray<>();

    public MyRBAdapter(Context context, List<MyRBEntity> myRBEntitys) {
        this.myRBEntitys = myRBEntitys;
        this.mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myRBEntitys.size();
    }

    @Override
    public MyRBEntity getItem(int position) {
        return myRBEntitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;
        if (lmap.get(position) == null) {
            view = mInflater.inflate(R.layout.item_dialog_radiobutton, null);
            holder = new ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.iv_item_dialog_img);
            holder.name = (TextView) view.findViewById(R.id.tv_item_dialog_name);
            holder.radioButton = (RadioButton) view.findViewById(R.id.rb_item_dialog);
            lmap.put(position, view);
            view.setTag(holder);
        } else {
            view = lmap.get(position);
            holder = (ViewHolder) view.getTag();
        }
        if (myRBEntitys != null && myRBEntitys.size() > 0) {
            holder.name.setText(myRBEntitys.get(position).getName().toString());
//            myRBEntitys.get(position)
//            holder.radioButton.setChecked(myRBEntitys.get(position).isChecked());
            holder.img.setBackgroundResource(R.mipmap.ic_launcher);
        }
        return view;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView name;
        public RadioButton radioButton;
    }
}
