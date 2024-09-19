//package com.example.mermentv1;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.widget.ExpandableListView;
//
//import com.example.mermentv1.ui.expendable.ExpendableListViewAdapter;
//import com.example.mermentv1.ui.expendable.GroupObject;
//import com.example.mermentv1.ui.expendable.ItemObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class SubMainActivity extends AppCompatActivity {
//
//    private ExpandableListView expandableListView;
//    private List<GroupObject> mListGroup;
//    private Map<GroupObject , List<ItemObject>> mListItems;
//    private ExpendableListViewAdapter expendableListViewAdapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_sub_main);
//
//
//        expandableListView = findViewById(R.id.expandableListView);
//
//        mListItems = getmListItems();
//        mListGroup = new ArrayList<>(mListItems.keySet());
//
//        expendableListViewAdapter = new ExpendableListViewAdapter(this, mListGroup , mListItems);
//        expandableListView.setAdapter(expendableListViewAdapter);
//
//
//
//    }
//
//    private Map<GroupObject , List<ItemObject>> getmListItems(){
//        Map<GroupObject , List<ItemObject>> listMap = new HashMap<>();
//
//        GroupObject groupObject1 = new GroupObject("ahiahi" , 1);
//        GroupObject groupObject2 = new GroupObject("hehe" , 2);
//        GroupObject groupObject3 = new GroupObject("hoho" , 3);
//
//
//        List<ItemObject> objectsList1 = new ArrayList<>();
//        objectsList1.add(new ItemObject(1 , "lalalala"));
//        objectsList1.add(new ItemObject(2 , "lalalala"));
//        objectsList1.add(new ItemObject(3 , "lalalala"));
//
//        List<ItemObject> objectsList2 = new ArrayList<>();
//        objectsList2.add(new ItemObject(4 , "lalalala"));
//        objectsList2.add(new ItemObject(5 , "lalalala"));
//        objectsList2.add(new ItemObject(6 , "lalalala"));
//
//        List<ItemObject> objectsList3 = new ArrayList<>();
//        objectsList3.add(new ItemObject(7 , "lalalala"));
//        objectsList3.add(new ItemObject(8 , "lalalala"));
//        objectsList3.add(new ItemObject(9 , "lalalala"));
//
//        listMap.put(groupObject1 , objectsList1);
//        listMap.put(groupObject2 , objectsList2);
//        listMap.put(groupObject3 , objectsList3);
//
//        return listMap;
//    }
//
//
//}