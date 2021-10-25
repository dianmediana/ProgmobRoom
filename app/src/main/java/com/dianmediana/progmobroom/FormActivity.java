package com.dianmediana.progmobroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dianmediana.progmobroom.database.AppDatabase;
import com.dianmediana.progmobroom.database.entitas.Mahasiswa;

public class FormActivity extends AppCompatActivity {
    private EditText editName, editEmail, editNim;
    private Button btnSave;
    private AppDatabase database;
    private int mhsid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editName = findViewById(R.id.full_name);
        editEmail = findViewById(R.id.email);
        editNim = findViewById(R.id.nim);
        btnSave = findViewById(R.id.btn_save);

        database = AppDatabase.getInstance(getApplicationContext()); //inisialisasi database

        Intent intent = getIntent();
        mhsid = intent.getIntExtra("mhsid", 0);
        if (mhsid>0){
            isEdit = true;
            Mahasiswa mahasiswa = database.mahasiswaDao().get(mhsid);
            editEmail.setText(mahasiswa.email);
            editNim.setText(String.valueOf(mahasiswa.nim));
            editName.setText(mahasiswa.fullname);
        }else{
            isEdit = false;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){
                    database.mahasiswaDao().update(mhsid, editName.getText().toString(),
                            Integer.parseInt(editNim.getText().toString()),
                            editEmail.getText().toString());
                }else{
                    database.mahasiswaDao().insertAll(editName.getText().toString(),
                            Integer.parseInt(editNim.getText().toString()),
                            editEmail.getText().toString());
                }
                finish();
            }
        });
    }
}
