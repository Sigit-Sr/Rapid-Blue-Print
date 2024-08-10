package com.example.barulagi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mendapatkan referensi ke LinearLayout
        LinearLayout printLinearLayout = findViewById(R.id.print);

        // Menambahkan OnClickListener
        printLinearLayout = findViewById(R.id.print);
        printLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrintHasil.class);
                startActivity(intent);
            }
        });

    }
}
