package com.hansung.android.teamproject2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MonthGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private LayoutInflater mLiInflater;
    ArrayList<String> days; //날짜 리스트 생성



    public MonthGridViewAdapter(Context context, int textResource, ArrayList<String> days) {

        this.mContext = context;
        this.mResource = textResource;
        this.days = days;
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayViewHolde dayViewHolder;

        if(convertView == null)
        {
            convertView = mLiInflater.inflate(mResource, null);

            dayViewHolder = new MonthGridViewAdapter.DayViewHolde();

            dayViewHolder.llBackground = (LinearLayout)convertView.findViewById(R.id.day_cell_ll_background);
            dayViewHolder.tvDay = (TextView) convertView.findViewById(R.id.day_cell_tv_day);

            convertView.setTag(dayViewHolder);
        }
        else
        {
            dayViewHolder = (DayViewHolde) convertView.getTag();
        }


        int height = mContext.getResources().getDisplayMetrics().heightPixels - 230;


        dayViewHolder.tvDay.setText(days.get(position));
        dayViewHolder.tvDay.setHeight(height/6 - 5);


        return convertView;
    }

    public static class DayViewHolde
    {
        public LinearLayout llBackground;
        public TextView tvDay;
    }

}
