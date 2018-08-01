package com.bryanville.familyexpenditure;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;
import com.bryanville.familyexpenditure.repository.ExpenditureRepository;

import java.util.List;

/**
 * Created by Bryanville on 7/5/2018.
 */

public class ExpenditureViewModel extends AndroidViewModel {
    private static final String LOG_TAG = ExpenditureViewModel.class.getSimpleName();
    private LiveData<List<Expenditure>> expenditureList;
    private ExpenditureRepository mRepository;

    public ExpenditureViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExpenditureRepository(application);
        expenditureList = mRepository.getExpenditureList();
    }

    public LiveData<List<Expenditure>> getExpenditureList() {
        Log.d(LOG_TAG, "Actively retrieving expenditure from database");
        return expenditureList;
    }
    public void insertExpenditure(Expenditure expenditure){
        mRepository.insertSingleExpenditure(expenditure);
    }

}
