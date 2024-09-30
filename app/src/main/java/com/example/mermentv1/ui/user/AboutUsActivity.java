package com.example.mermentv1.ui.user;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mermentv1.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Find the TextView by its ID
        TextView textView = findViewById(R.id.text_view_id);

// Set HTML formatted text
        String text = "<b><font color='#000000'>Chuyên Cho Thuê Các Thiết Bị Quay Chụp:</font></b> <b><font color='#082C44'>Camera, Máy Quay, Lens, Tripod, Gimbal, Đèn, Micro, Livestream, Studio.</font></b>";

// Apply the HTML formatting
        textView.setText(Html.fromHtml(text));



    }




    public void back(View view){
        finish();
    }


}