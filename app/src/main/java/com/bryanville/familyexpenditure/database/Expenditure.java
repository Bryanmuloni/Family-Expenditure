package com.bryanville.familyexpenditure.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bryanville on 7/4/2018.
 */
@Entity(tableName = "expenditure")
public class Expenditure {
    @PrimaryKey(autoGenerate = true)
    private int expenditureId;
    @ColumnInfo(name = "name")
    private String itemName;
    @ColumnInfo(name = "quantity")
    private String itemQuantity;
    @ColumnInfo(name = "amount")
    private String itemAmount;
    @ColumnInfo(name = "status")
    private String expenditureStatus;
    @ColumnInfo(name = "date")
    private String expenditureDate;
    @ColumnInfo(name = "comment")
    private String expenditureComment;

    public Expenditure(int expenditureId, String itemName, String itemQuantity, String itemAmount, String expenditureStatus, String expenditureDate, String expenditureComment) {
        this.expenditureId = expenditureId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemAmount = itemAmount;
        this.expenditureStatus = expenditureStatus;
        this.expenditureDate = expenditureDate;
        this.expenditureComment = expenditureComment;
    }

    @Ignore
    public Expenditure(String itemName, String itemQuantity, String itemAmount, String expenditureStatus, String expenditureDate, String expenditureComment) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemAmount = itemAmount;
        this.expenditureStatus = expenditureStatus;
        this.expenditureDate = expenditureDate;
        this.expenditureComment = expenditureComment;
    }

    public int getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(int expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getExpenditureStatus() {
        return expenditureStatus;
    }

    public void setExpenditureStatus(String expenditureStatus) {
        this.expenditureStatus = expenditureStatus;
    }

    public String getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(String expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public String getExpenditureComment() {
        return expenditureComment;
    }

    public void setExpenditureComment(String expenditureComment) {
        this.expenditureComment = expenditureComment;
    }
}