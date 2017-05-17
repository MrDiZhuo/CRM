package com.zwd.crm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwd.crm.HomePage.apply.Module.apply_departmentElement;
import com.zwd.crm.HomePage.inform.Module.subjectdepartmentElement;
import com.zwd.crm.R;
import com.zwd.crm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus-pc on 2017/3/10.
 */

public class adjust_adapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<subjectdepartmentElement> father=new ArrayList<>();
    private ArrayList<ArrayList<subjectdepartmentElement>> child=new ArrayList<>();

    public adjust_adapter(Context mContext, ArrayList<subjectdepartmentElement> father, ArrayList<ArrayList<subjectdepartmentElement>> child) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.child = child;
        this.father = father;
    }



    @Override
    public int getGroupCount() {
        return father.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return father.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_adjust_bumen_father, parent, false);
        }
        final ImageView expandedImage = (ImageView) convertView.findViewById(R.id.father_img);
        final int resId = isExpanded ? R.mipmap.father_open : R.mipmap.father_close;
        expandedImage.setImageResource(resId);
        final TextView text_all= (TextView) convertView.findViewById(R.id.father_txt);
        text_all.setText(father.get(groupPosition).getDepartmentname());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_adjust_bumen_child, parent, false);
        }
        final TextView child_text = (TextView) convertView.findViewById(R.id.child_txt);
        child_text.setText(child.get(groupPosition).get(childPosition).getDepartmentname());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
      //  ToastUtils.showShort(mContext,getChild(groupPosition,childPosition).toString());
        return true;
    }
}
