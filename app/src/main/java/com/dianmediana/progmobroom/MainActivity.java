package com.dianmediana.progmobroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dianmediana.progmobroom.adapter.MahasiswaAdapter;
import com.dianmediana.progmobroom.adapter.MahasiswaAdapter;
import com.dianmediana.progmobroom.database.AppDatabase;
import com.dianmediana.progmobroom.database.entitas.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnTambah;
    private AppDatabase database;
    private MahasiswaAdapter mahasiswaAdapter;
    private List<Mahasiswa> list = new ArrayList<>();
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        btnTambah = findViewById(R.id.btn_tambah);

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.mahasiswaDao().getAll()); //data inputan dimasukin ke list terlebih dahulu, baru masuk ke adapter
        mahasiswaAdapter = new MahasiswaAdapter(getApplicationContext(), list);
        mahasiswaAdapter.setDialog(new MahasiswaAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                                intent.putExtra("mhsid", list.get(position).mhsid);
                                startActivity(intent);
                                break;
                            case 1:
                                Mahasiswa mahasiswa = list.get(position);
                                database.mahasiswaDao().delete(mahasiswa);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mahasiswaAdapter);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormActivity.class));
            }
        });
    }

    //otomatis menambahkan list data setelah proses input (refresh data)
    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.mahasiswaDao().getAll()); //ambil data dari database
        mahasiswaAdapter.notifyDataSetChanged(); //kirim notifikasi ke adapter
    }
}