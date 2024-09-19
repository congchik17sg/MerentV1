package com.example.mermentv1.ui.expendable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.mermentv1.MainActivity;
import com.example.mermentv1.R;

import java.util.List;
import java.util.Map;

public class ExpendableListViewAdapter extends BaseExpandableListAdapter {

    private List<GroupObject> mListGroup;
    private Map<GroupObject , List<ItemObject>> mListItems;


    public ExpendableListViewAdapter(MainActivity mainActivity, List<GroupObject> mListGroup, Map<GroupObject, List<ItemObject>> mListItems) {
        this.mListGroup = mListGroup;
        this.mListItems = mListItems;
    }

    @Override
    public int getGroupCount() {
       if(mListGroup != null){
           return mListGroup.size();
       }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       if(mListGroup != null && mListItems != null){
           return mListItems.get(mListGroup.get(groupPosition)).size();
       }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)  {
//        return mListItems.get(mListGroup.get(groupPosition));
        return mListItems.get(mListGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
       GroupObject groupObject = mListGroup.get(groupPosition);
        return groupObject.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        ItemObject itemObject = mListItems.get(mListGroup.get(groupPosition)).get(childPosition);
        return itemObject.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       if(convertView == null){
           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent,false);

           TextView tv_group = convertView.findViewById(R.id.tv_group);
           GroupObject groupObject = mListGroup.get( groupPosition);
           tv_group.setText(groupObject.getName().toUpperCase());
       }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent,false);

            TextView tv_item = convertView.findViewById(R.id.tv_item);
            ItemObject itemObject = mListItems.get(mListGroup.get(groupPosition)).get(childPosition);
            tv_item.setText(itemObject.getName());
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true ;
    }
}
