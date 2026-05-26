package com.svalero.tourfrance.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavouriteCyclistDao {

    @Query("SELECT * FROM favourite_cyclists")
    List<FavouriteCyclist> getAll();

    @Insert
    void insert(FavouriteCyclist cyclist);

    @Update
    void update(FavouriteCyclist cyclist);

    @Delete
    void delete(FavouriteCyclist cyclist);
}
