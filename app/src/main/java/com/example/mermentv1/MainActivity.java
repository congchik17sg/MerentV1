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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mermentv1.databinding.ActivityMainBinding;
import com.example.mermentv1.model.Product;
import com.example.mermentv1.model.ProductResponse;
import com.example.mermentv1.ui.api.ApiService;
import com.example.mermentv1.ui.card.CardAdapter;
import com.example.mermentv1.ui.card.CardModel;
import com.example.mermentv1.ui.expendable.ExpendableListViewAdapter;
import com.example.mermentv1.ui.expendable.GroupObject;
import com.example.mermentv1.ui.expendable.ItemObject;
import com.example.mermentv1.ui.user.AboutUsActivity;
import com.example.mermentv1.ui.user.AccessoriesActivity;
import com.example.mermentv1.ui.user.CameraLegActivity;
import com.example.mermentv1.ui.user.LensActivity;
import com.example.mermentv1.ui.user.SearchActivity;
import com.example.mermentv1.ui.user.CameraRecorderActivity;
import com.example.mermentv1.ui.user.GimbalActivity;
import com.example.mermentv1.ui.user.HomeActivity;
import com.example.mermentv1.ui.user.LightActivity;
import com.example.mermentv1.ui.user.ShoppingCartActivity;
import com.example.mermentv1.ui.user.UserActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // khai bao list view
    private ExpandableListView expandableListView;
    private List<GroupObject> mListGroup;
    private Map<GroupObject, List<ItemObject>> mListItems;
    private ExpendableListViewAdapter expendableListViewAdapter;

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardModel> cardList;
    private ApiService apiService;


    // khai bao drawer
    private DrawerLayout drawerLayout;

    // khai bao drawer toogle
    private ActionBarDrawerToggle toggle;


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Setup BottomNavigationView item selection handling
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.user_home) {
                Intent intent = new Intent(MainActivity.this , MainActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.search) {
                Intent intent = new Intent(MainActivity.this , SearchActivity.class);
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


        recyclerView = findViewById(R.id.recycler_view);
        cardList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        cardAdapter = new CardAdapter(this, cardList);
        recyclerView.setAdapter(cardAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://merent.uydev.id.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        fetchProducts();
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
        GroupObject groupObject3 = new GroupObject("Combo", 3);

        List<ItemObject> objectsList1 = new ArrayList<>();


        List<ItemObject> objectsList2 = new ArrayList<>();
        objectsList2.add(new ItemObject(2, "Máy ảnh"));
        objectsList2.add(new ItemObject(2, "Gimbal"));
        objectsList2.add(new ItemObject(2, "Chân Máy"));
        objectsList2.add(new ItemObject(2, "Ánh Sáng"));
        objectsList2.add(new ItemObject(2, "Lens"));
        objectsList2.add(new ItemObject(2, "Phụ Kiện"));


        List<ItemObject> objectsList3 = new ArrayList<>();
        objectsList3.add(new ItemObject(3, "Combo Máy Ảnh"));
        objectsList3.add(new ItemObject(3, "Combo Quay Chụp"));

        listMap.put(groupObject1, objectsList1);
        listMap.put(groupObject2, objectsList2);
        listMap.put(groupObject3, objectsList3);

        return listMap;
    }

    private void setupExpandableListClickListener() {
        // Handle child item clicks
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            GroupObject group = mListGroup.get(groupPosition);
            ItemObject item = mListItems.get(group).get(childPosition);

            // Navigate to the CameraRecorder activity when "Máy ảnh/Quay Phim" is clicked
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Máy ảnh")) {
                Intent intent = new Intent(MainActivity.this, CameraRecorderActivity.class);
                startActivity(intent);
                return true;
            }
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Gimbal")) {
                Intent intent = new Intent(MainActivity.this, GimbalActivity.class);
                startActivity(intent);
                return true;
            } if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Ánh Sáng")) {
                Intent intent = new Intent(MainActivity.this, LightActivity.class);
                startActivity(intent);
                return true;
            }
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Chân Máy")) {
                Intent intent = new Intent(MainActivity.this, CameraLegActivity.class);
                startActivity(intent);
                return true;
            }
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Lens")) {
                Intent intent = new Intent(MainActivity.this, LensActivity.class);
                startActivity(intent);
                return true;
            }
            if (group.getName().equals("Cho Thuê Thiết Bị") && item.getName().equals("Phụ Kiện")) {
                Intent intent = new Intent(MainActivity.this, AccessoriesActivity.class);
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
    private void fetchProducts() {
        Log.d("API_FETCH", "Fetching all products...");
        apiService.getAllProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData();
                    if (products != null && !products.isEmpty()) {
                        Log.d("API_SUCCESS", "Fetched " + products.size() + " products.");
                        displayRandomProduct(products); // Display only one random product
                    } else {
                        Toast.makeText(MainActivity.this, "No products available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRandomProduct(List<Product> products) {
        cardList.clear();
        Random random = new Random();
        Product randomProduct = products.get(random.nextInt(products.size()));

        cardList.add(new CardModel(
                randomProduct.getName(),
                randomProduct.getPrice() + " VND",
                randomProduct.getDescription(),
                randomProduct.getUrlCenter(),
                randomProduct.getUrlLeft(),
                randomProduct.getUrlRight(),
                randomProduct.getUrlSide()
        ));

        cardAdapter.notifyDataSetChanged();
        Log.d("RecyclerView", "Displayed 1 random product: " + randomProduct.getName());
    }

    private void handleApiError(Response<ProductResponse> response) {
        Log.e("API_ERROR", "Response unsuccessful, Code: " + response.code());
        if (response.errorBody() != null) {
            try {
                Log.e("API_ERROR", "Error body: " + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(MainActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
    }
}
