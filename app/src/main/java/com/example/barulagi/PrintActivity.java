package com.example.barulagi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class PrintActivity extends Activity {
    Button uploadBTn, btn_simpan;
    EditText edpilih, edalamat, edpengiriman, ednama;


    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        edpilih = findViewById(R.id.edpilih);
        edalamat = findViewById(R.id.edalamat);
        edpengiriman = findViewById(R.id.edpengiriman);
        ednama = findViewById(R.id.ednama);
        btn_simpan = findViewById(R.id.btn_simpan);
        uploadBTn = findViewById(R.id.btn);

        storageReference = FirebaseStorage.getInstance().getReference();

        uploadBTn.setEnabled(false);

        btn_simpan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getFile = edpilih.getText().toString();
                String getAlamat = edalamat.getText().toString();
                String getPengrimanan = edpengiriman.getText().toString();
                String getNama = ednama.getText().toString();

                if (getFile.isEmpty()) {
                    edpilih.setError("Mohon di Isi");
                } else if (getAlamat.isEmpty()) {
                    edalamat.setError("Mohon di Isi");
                } else if (getPengrimanan.isEmpty()) {
                    edpengiriman.setError("Mohon di Isi");
                } else if (getNama.isEmpty()) {
                    ednama.setError("Mohon di Isi");
                } else {
                    database.child("FilePrint").push().setValue(new ModelPrint(getFile,getNama,getAlamat,getPengrimanan)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(PrintActivity.this, "Data berhasil di simpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PrintActivity.this, PrintHasil.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PrintActivity.this, "Gagal Di Simpan", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        edpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectFile();
            }

        });
    }
    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf files"),101);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 & resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri =data.getData();

            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")){
                displayName = myFile.getName();
            }
            uploadBTn.setEnabled(true);
            edpilih.setText(displayName);

            uploadBTn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDF(data.getData());
                }
            });

        }
    }

    private void uploadPDF(Uri data) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File uploading...");
        pd.show();;

        final StorageReference reference = storageReference.child("uploads/"+ System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        Toast.makeText(PrintActivity.this,"File Uploded Successfuly", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded : "+ (int) percent + "%");
                    }
                });

    }




}

