package com.dianmediana.progmobroom.database.entitas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mahasiswa {
    @PrimaryKey
    public int mhsid;

    @ColumnInfo(name = "nama") //nama fieldnya nama, bukan fullname
    public String fullname;

    public int nim;

    public String email;
}
