package com.example.barulagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.CertPathBuilder;
import java.util.ArrayList;

public class PrintHasil extends AppCompatActivity {
    FloatingActionButton tambah;
    ImageView btnBack;
    AdapterPrint adapterPrint;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelPrint> listPrint;
    RecyclerView tv_tampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_hasil);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tambah = findViewById(R.id.btn_tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrintHasil.this, PrintActivity.class));
            }
        });

        tv_tampil = findViewById(R.id.tv_tampil);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        tv_tampil.setLayoutManager(mLayout);
        tv_tampil.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void tampilData() {
        database.child("FilePrint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPrint = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    ModelPrint mhs = item.getValue(ModelPrint.class);
                    mhs.setKey(item.getKey());
                    listPrint.add(mhs);
                }
              adapterPrint = new AdapterPrint(listPrint,PrintHasil.this);
                tv_tampil.setAdapter(adapterPrint);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}