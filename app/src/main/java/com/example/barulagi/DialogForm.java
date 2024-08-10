package com.example.barulagi;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String file, nama, alamat, pengiriman, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String file, String nama, String alamat, String pengiriman, String key, String pilih) {
        this.file = file;
        this.nama = nama;
        this.alamat = alamat;
        this.pengiriman = pengiriman;
        this.key = key;
        this.pilih = pilih;
    }
    TextView tnama,talamat,tpengiriman,tfile;
    Button btn_simpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_print,container,false);
        tnama = view.findViewById(R.id.ednama);
        talamat = view.findViewById(R.id.edalamat);
        tpengiriman = view.findViewById(R.id.edpengiriman);
        tfile = view.findViewById(R.id.edpilih);
        btn_simpan = view.findViewById(R.id.btn_simpan);

        tfile.setText(file);
        tnama.setText(nama);
        talamat.setText(alamat);
        tpengiriman.setText(pengiriman);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = tfile.getText().toString();
                String nama = tnama.getText().toString();
                String alamat = talamat.getText().toString();
                String pengiriman = tpengiriman.getText().toString();

                if (pilih.equals("Ubah")){
                    database.child("FilePrint").child(key).setValue(new ModelPrint(file,nama, alamat,pengiriman)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(),"Berhasil di Update",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Maaf gagal mengupdate data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog =getDialog();
        if (dialog!= null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
