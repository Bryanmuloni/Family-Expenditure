package com.bryanville.familyexpenditure.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDao;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;

import java.util.List;

/**
 * Created by Bryanville on 8/1/2018.
 */

public class ExpenditureRepository {
    private ExpenditureDao expenditureDao;
    private LiveData<List<Expenditure>> expenditureList;

    public ExpenditureRepository(Application application) {
        ExpenditureDatabase expenditureDatabase = ExpenditureDatabase.getDatabaseInstance(application);
        expenditureDao = expenditureDatabase.expenditureDao();
        expenditureList = expenditureDao.queryAllExpenditure();
    }

    public LiveData<List<Expenditure>> getExpenditureList() {
        return expenditureList;
    }

    public void insertSingleExpenditure(Expenditure expenditure) {
        new insertExpenditureAsyncTask(expenditureDao).execute(expenditure);
    }

    private static class insertExpenditureAsyncTask extends AsyncTask<Expenditure, Void, Void> {
        private ExpenditureDao mDao;

        public insertExpenditureAsyncTask(ExpenditureDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Expenditure... expenditures) {
            mDao.insertNewExpenditure(expenditures[0]);
            return null;
        }
    }
}
