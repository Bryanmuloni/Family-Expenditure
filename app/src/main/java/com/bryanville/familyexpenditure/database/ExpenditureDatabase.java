package com.bryanville.familyexpenditure.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Bryanville on 7/4/2018.
 */
@Database(entities = {Expenditure.class}, version = 1, exportSchema = false)
public abstract class ExpenditureDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "expenditure_db";
    private static final Object LOCK = new Object();
    private static ExpenditureDatabase DATABASE_INSTANCE;

    public abstract ExpenditureDao expenditureDao();

    public static ExpenditureDatabase getDatabaseInstance(final Context context) {
        if (DATABASE_INSTANCE == null) {
            synchronized (LOCK) {
                if (DATABASE_INSTANCE == null) {
                    DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ExpenditureDatabase.class, ExpenditureDatabase.DATABASE_NAME)
                            .build();
                }
            }
        }
        return DATABASE_INSTANCE;
    }
}
