package com.bryanville.familyexpenditure.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bryanville on 7/16/2018.
 */
@Entity(tableName = "income")
public class Income {
    @PrimaryKey(autoGenerate = true)
    public int incomeId;
    @ColumnInfo(name = "source")
    public String incomeSource;
    @ColumnInfo(name = "amount")
    public String incomeAmount;
    @ColumnInfo(name = "income_date")
    public String incomeDate;

    @ColumnInfo(name = "comment")
    public String incomeComment;

    @ColumnInfo(name = "account")
    public String accountNumber;

    public Income(int incomeId, String incomeSource, String incomeAmount, String incomeDate, String incomeComment) {
        this.incomeId = incomeId;
        this.incomeSource = incomeSource;
        this.incomeAmount = incomeAmount;
        this.incomeDate = incomeDate;
        this.incomeComment = incomeComment;
    }
}
