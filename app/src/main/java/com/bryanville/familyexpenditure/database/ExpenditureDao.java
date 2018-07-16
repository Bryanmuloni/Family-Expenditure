package com.bryanville.familyexpenditure.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Bryanville on 7/4/2018.
 */
@Dao
public interface ExpenditureDao {
    @Insert
    void insertNewExpenditure(Expenditure expenditure);

    @Query("SELECT * FROM expenditure ORDER BY expenditureId DESC")
    LiveData<List<Expenditure>> queryAllExpenditure();

    @Query("SELECT * FROM expenditure WHERE expenditureId = :id")
    LiveData<Expenditure> queryExpenditureById(int id);
    @Delete
    void deleteExpenditure(Expenditure expenditure);
    @Update
    void updateExpenditure(Expenditure expenditure);
}
