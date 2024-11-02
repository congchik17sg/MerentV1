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
    private Map<GroupObject, List<ItemObject>> mListItems;

    public ExpendableListViewAdapter(MainActivity mainActivity, List<GroupObject> mListGroup, Map<GroupObject, List<ItemObject>> mListItems) {
        this.mListGroup = mListGroup;
        this.mListItems = mListItems;
    }

    @Override
    public int getGroupCount() {
        return mListGroup != null ? mListGroup.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mListGroup != null && mListItems != null) {
            List<ItemObject> items = mListItems.get(mListGroup.get(groupPosition));
            return items != null ? items.size() : 0;
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ItemObject> items = mListItems.get(mListGroup.get(groupPosition));
        return items != null ? items.get(childPosition) : null;
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
        ViewHolderGroup holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent, false);
            holder = new ViewHolderGroup();
            holder.tv_group = convertView.findViewById(R.id.tv_group);
            convertView.setTag(holder);  // Store the view holder
        } else {
            holder = (ViewHolderGroup) convertView.getTag();  // Retrieve the view holder
        }

        // Always update the TextView text
        GroupObject groupObject = mListGroup.get(groupPosition);
        holder.tv_group.setText(groupObject.getName().toUpperCase());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            holder = new ViewHolderChild();
            holder.tv_item = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);  // Store the view holder
        } else {
            holder = (ViewHolderChild) convertView.getTag();  // Retrieve the view holder
        }

        // Always update the TextView text
        ItemObject itemObject = mListItems.get(mListGroup.get(groupPosition)).get(childPosition);
        holder.tv_item.setText(itemObject.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // View holder classes for better performance
    static class ViewHolderGroup {
        TextView tv_group;
    }

    static class ViewHolderChild {
        TextView tv_item;
    }
}
