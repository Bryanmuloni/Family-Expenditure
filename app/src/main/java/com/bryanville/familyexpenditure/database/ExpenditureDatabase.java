package com.bryanville.familyexpenditure.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Bryanville on 7/4/2018.
 */
@Database(entities = {Expenditure.class},version = 1,exportSchema = false)
public abstract class ExpenditureDatabase extends RoomDatabase {
    public abstract ExpenditureDao expenditureDao();
}
