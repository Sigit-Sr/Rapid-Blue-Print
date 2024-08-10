package com.example.barulagi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterPrint extends RecyclerView.Adapter<AdapterPrint.MyViewHolder> {
    private List<ModelPrint> mlist;
    private Activity activity;
    DatabaseReference datatabse = FirebaseDatabase.getInstance().getReference();

    public AdapterPrint (List<ModelPrint>mlist, Activity activity) {
        this.mlist =mlist;
        this.activity =activity;

    }
    @NonNull
    @Override
    public AdapterPrint.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_item, parent,false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPrint.MyViewHolder holder, int position) {
        final ModelPrint data = mlist.get(position);
        holder.tv_file.setText(data.getFile());
        holder.tv_nama.setText("Nama :" + data.getNama());
        holder.tv_alamat.setText("Alamat :" +data.getAlamat());
        holder.tv_pengiriman.setText("Pengiriman :" +data.getPengiriman());
        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datatabse.child("FilePrint").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity,"Data berasil dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "gagal menghapus data",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("Apakah yakin mau menghapus file dari " + data.getNama() +" ?" );
                builder.show();
            }
        });
        holder.card_print.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialog= new DialogForm(
                        data.getFile(),
                        data.getNama(),
                        data.getAlamat(),
                        data.getPengiriman(),
                        data.getKey(),
                        "Ubah"
                );
                dialog.show(manager, "form");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img,btn_hapus;
        TextView tv_file,tv_nama,tv_alamat,tv_pengiriman;
        CardView card_print;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv_file = itemView.findViewById(R.id.tv_file);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_pengiriman = itemView.findViewById(R.id.tv_pengiriman);
            btn_hapus = itemView.findViewById(R.id.hapus);
            card_print = itemView.findViewById(R.id.card_print);
        }
    }
}
