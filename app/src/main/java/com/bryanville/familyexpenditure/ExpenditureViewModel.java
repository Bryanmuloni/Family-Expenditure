package com.bryanville.familyexpenditure;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;

import java.util.List;

/**
 * Created by Bryanville on 7/5/2018.
 */

public class ExpenditureViewModel extends AndroidViewModel {
    private static final String LOG_TAG = ExpenditureViewModel.class.getSimpleName();
    private LiveData<List<Expenditure>> expenditureList;
    public ExpenditureViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Expenditure>> getExpenditureList(){
        expenditureList = getExpenditureDatabaseInstance().expenditureDao().queryAllExpenditure();
        Log.d(LOG_TAG,"Actively retrieving expenditure from database");
        return expenditureList;
    }
    public ExpenditureDatabase getExpenditureDatabaseInstance() {
        String dbName = "expenditure_db";
        ExpenditureDatabase expenditureDatabase = Room.databaseBuilder(this.getApplication(),
                ExpenditureDatabase.class,
                dbName)
                .allowMainThreadQueries()
                .build();
        return expenditureDatabase;

    }
}
