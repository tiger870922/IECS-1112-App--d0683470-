package com.example.orderapp2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PictureListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SelectItem> mList;
    private LayoutInflater mInflater;

    public PictureListAdapter(Context context, List<SelectItem> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PictureListAdapter.ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.select_item, null);
            holder = new PictureListAdapter.ViewHolder();
            holder.img_selectItem = (ImageView) view.findViewById(R.id.img_selectItem);
            holder.txt_selectItem_num = (TextView) view.findViewById(R.id.txt_selectItem_num);
            view.setTag(holder);
        }else {
            holder = (PictureListAdapter.ViewHolder) view.getTag();
        }

        holder.img_selectItem.setImageResource(mList.get(i).getItem_img());
        int num = i+1;
        holder.txt_selectItem_num.setText(""+num);
        return view;
    }

    class ViewHolder {
        private ImageView img_selectItem;
        private TextView txt_selectItem_num;
    }
}
