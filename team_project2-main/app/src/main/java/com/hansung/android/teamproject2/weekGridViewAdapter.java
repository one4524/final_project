package com.hansung.android.teamproject2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class weekGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private LayoutInflater mLiInflater;

    public weekGridViewAdapter(Context context, int textResource) {

        this.mContext = context;
        this.mResource = textResource;
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 168;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        int index = position;
        WeekViewHolder weekViewHolder;
        if(convertView == null)
        {
            convertView = mLiInflater.inflate(mResource, null);

            weekViewHolder = new weekGridViewAdapter.WeekViewHolder();
            weekViewHolder.tvDay = (TextView) convertView.findViewById(R.id.day_cell_tv_day);
            convertView.setTag(index);

        } else
        {
             index = (int)convertView.getTag();
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        int height = mContext.getResources().getDisplayMetrics().heightPixels - 230;
        ((TextView)convertView.findViewById(R.id.day_cell_tv_day)).setHeight(height/6 - 23);

        return convertView;
    }

    public static class WeekViewHolder
    {
        public TextView tvDay;
    }


}


