package com.example.mermentv1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.mermentv1.databinding.ActivityMainBinding;
import com.example.mermentv1.ui.expendable.ExpendableListViewAdapter;
import com.example.mermentv1.ui.expendable.GroupObject;
import com.example.mermentv1.ui.expendable.ItemObject;
import com.example.mermentv1.ui.user.AboutUsActivity;
import com.example.mermentv1.ui.user.AddActivity;
import com.example.mermentv1.ui.user.CameraRecorederActivity;
import com.example.mermentv1.ui.user.HomeActivity;
import com.example.mermentv1.ui.user.ShoppingCartActivity;
import com.example.mermentv1.ui.user.UserActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // khai bao list view
    private ExpandableListView expandableListView;
    private List<GroupObject> mListGroup;
    private Map<GroupObject, List<ItemObject>> mListItems;
    private ExpendableListViewAdapter expendableListViewAdapter;

    // khai bao drawer
    private DrawerLayout drawerLayout;

    // khai bao drawer toogle
    private ActionBarDrawerToggle toggle;

    // khai bao bottom
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set default fragment
//
        // Setup BottomNavigationView item selection handling
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.user_home) {
                Intent intent = new Intent(MainActivity.this , HomeActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.user_add) {
                Intent intent = new Intent(MainActivity.this , AddActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.shopping_cart) {
                Intent intent = new Intent(MainActivity.this , ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.user_menu) {
                Intent intent = new Intent(MainActivity.this , UserActivity.class);
                startActivity(intent);
                return true;
            } else {
                return false;
            }

        });

        // Initialize the drawer layout and toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup window insets (for edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the ExpandableListView within the drawer
        expandableListView = findViewById(R.id.expandableListView);

        mListItems = getmListItems();
        mListGroup = new ArrayList<>(mListItems.keySet());

        expendableListViewAdapter = new ExpendableListViewAdapter(this, mListGroup, mListItems);
        expandableListView.setAdapter(expendableListViewAdapter);

        setupExpandableListClickListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to populate the expandable list data
    private Map<GroupObject, List<ItemObject>> getmListItems() {
        Map<GroupObject, List<ItemObject>> listMap = new HashMap<>();

        GroupObject groupObject1 = new GroupObject("Giới Thiệu", 1);
        GroupObject groupObject2 = new GroupObject("Cho Thuê Thiết Bị", 2);
        GroupObject groupObject6 = new GroupObject("Combo", 3);

        List<ItemObject> objectsList1 = new ArrayList<>();
        List<ItemObject> objectsList2 = new ArrayList<>();
        objectsList2.add(new ItemObject(1, "Máy ảnh/Quay Phim"));
        objectsList2.add(new ItemObject(2, "Ống Kính"));
        objectsList2.add(new ItemObject(3, "Chân Máy/Gimbal"));
        objectsList2.add(new ItemObject(4, "Ánh Sáng"));
        objectsList2.add(new ItemObject(5, "Flycam"));
        objectsList2.add(new ItemObject(6, "Phụ Kiện"));
        objectsList2.add(new ItemObject(7, "Work Shop"));

        List<ItemObject> objectsList6 = new ArrayList<>();
        objectsList6.add(new ItemObject(10, "Combo Máy Ảnh"));
        objectsList6.add(new ItemObject(11, "Combo Quay Chụp"));

        listMap.put(groupObject1, objectsList1);
        listMap.put(groupObject2, objectsList2);
        listMap.put(groupObject6, objectsList6);

        return listMap;
    }

    private void setupExpandableListClickListener() {
        // Handle child item clicks
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            GroupObject group = mListGroup.get(groupPosition);
            ItemObject item = mListItems.get(group).get(childPosition);

            // Navigate to the CameraRecorder activity when "Máy ảnh/Quay Phim" is clicked
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Máy ảnh/Quay Phim")) {
                Intent intent = new Intent(MainActivity.this, CameraRecorederActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        // Handle group header clicks
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            GroupObject group = mListGroup.get(groupPosition);

            // Navigate to the AboutUsActivity when "Giới Thiệu" group is clicked
            if (group.getName().equals("Giới Thiệu")) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                return true;  // Return true to indicate that the click was handled
            }

            return false;  // Return false to allow the default behavior of expanding/collapsing the group
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.user_menu, fragment); // Make sure 'user_menu' is a container in your layout
        fragmentTransaction.commit();
    }
}
