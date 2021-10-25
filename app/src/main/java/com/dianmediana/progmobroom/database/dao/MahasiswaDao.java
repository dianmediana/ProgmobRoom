package com.dianmediana.progmobroom.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.dianmediana.progmobroom.database.entitas.Mahasiswa;

import java.util.List;

@Dao
public interface MahasiswaDao {
    @Query("SELECT * FROM mahasiswa")
    List<Mahasiswa> getAll();

    @Query("INSERT INTO mahasiswa (nama,nim,email) VALUES(:nama, :nim, :email)")
    void insertAll(String nama, Integer nim, String email);

    @Query("UPDATE mahasiswa SET nama=:nama, nim=:nim, email=:email WHERE mhsid=:mhsid")
    void update(int mhsid, String nama, int nim, String email);

    @Query("SELECT * FROM mahasiswa WHERE mhsid=:mhsid")
    Mahasiswa get(int mhsid);

    @Delete
    void delete(Mahasiswa mahasiswa);
}
