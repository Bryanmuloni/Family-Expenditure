package com.bryanville.familyexpenditure;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;

public class AddNewExpenditureActivity extends AppCompatActivity {
    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemAmount;
    private EditText expDate;
    private EditText expComment;

    private String item_name;
    private String item_quantity;
    private String item_amount;
    private String exp_date;
    private String exp_comment;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expenditure);

        itemName = findViewById(R.id.itemNameText);
        itemQuantity = findViewById(R.id.itemQuantityText);
        itemAmount = findViewById(R.id.itemAmountText);
        expDate = findViewById(R.id.expenditureDateText);
        expComment = findViewById(R.id.expenditureCommentText);
    }

    public void chooseExpenditureStatus(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.itemStatusPaid:
                if (isChecked)
                    status = "Paid";
                    break;
            case R.id.itemStatusCredit:
                if (isChecked)
                    status = "Credit";
                break;

        }
    }

    public void saveExpenditureToDatabase(View view) {
        item_name = itemName.getText().toString();
        item_amount = itemAmount.getText().toString();
        item_quantity  = itemQuantity.getText().toString();
        exp_date = expDate.getText().toString();
        exp_comment = expComment.getText().toString();
        if (!item_name.isEmpty()
                && !item_amount.isEmpty()
                && !item_quantity.isEmpty()
                && !exp_date.isEmpty()
                && !exp_comment.isEmpty()){
            Expenditure expenditure = new Expenditure(item_name,item_quantity,item_amount,status,exp_date,exp_comment);
            getExpenditureDatabaseInstance().expenditureDao().insertNewExpenditure(expenditure);
            Toast.makeText(this,expenditure.getItemName()+" with "+expenditure.getExpenditureStatus()+" status expenditure saved", Toast.LENGTH_LONG).show();
            NotificationUtils.remindNewExpenditureAdded(this,item_name,exp_comment);
            finish();
        }
        else {
            Toast.makeText(this,"Make sure all fields are filled",Toast.LENGTH_SHORT).show();
        }
    }
    public ExpenditureDatabase getExpenditureDatabaseInstance(){
        String dbName = "expenditure_db";
        ExpenditureDatabase expenditureDatabase = Room.databaseBuilder(AddNewExpenditureActivity.this,
                ExpenditureDatabase.class,
                dbName)
                .allowMainThreadQueries()
                .build();
        return expenditureDatabase;

    }
}