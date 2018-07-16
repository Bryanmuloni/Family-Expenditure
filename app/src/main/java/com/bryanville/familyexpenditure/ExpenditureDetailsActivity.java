package com.bryanville.familyexpenditure;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;

public class ExpenditureDetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ExpenditureDetailsActivity.class.getSimpleName();
    public static final String EXTRA_EXPENDITURE_ID = "expenditureId";
    public static final int DEFAULT_ID = -1;
    public int expenditureId = DEFAULT_ID;
    private TextView itemDetail;
    private TextView totalDetail;
    private TextView subTotalDetail;
    private TextView statusDetail;
    private TextView dateDetail;
    private TextView quantityDetail;
    private TextView commentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemDetail = findViewById(R.id.itemDetail);
        totalDetail = findViewById(R.id.totalDetail);
        subTotalDetail = findViewById(R.id.subTotalDetail);
        quantityDetail = findViewById(R.id.quantityDetail);
        statusDetail = findViewById(R.id.statusDetail);
        commentDetail = findViewById(R.id.commentDetail);
        dateDetail = findViewById(R.id.dateDetail);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_EXPENDITURE_ID)) {
            if (expenditureId == DEFAULT_ID) {
                expenditureId = intent.getIntExtra(EXTRA_EXPENDITURE_ID, DEFAULT_ID);
                final LiveData<Expenditure> expenditureList = getDatabaseInstance().expenditureDao().queryExpenditureById(expenditureId);
                expenditureList.observe(this, new Observer<Expenditure>() {
                    @Override
                    public void onChanged(@Nullable Expenditure expenditure) {
                        expenditureList.removeObserver(this);
                        Log.d(LOG_TAG,"Database update message from LiveData");
                        UpdateUI(expenditure);
                    }
                });

            }

        }

    }

    public ExpenditureDatabase getDatabaseInstance() {
        String dbName = "expenditure_db";
        ExpenditureDatabase database = Room.databaseBuilder(this, ExpenditureDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();
        return database;
    }

    public void UpdateUI(Expenditure expenditure) {
        if (expenditure == null) {
            return;

        }
        itemDetail.setText(expenditure.getItemName());
        totalDetail.setText(expenditure.getItemAmount());
        subTotalDetail.setText(expenditure.getItemAmount());
        quantityDetail.setText(expenditure.getItemQuantity());
        statusDetail.setText(expenditure.getExpenditureStatus());
        dateDetail.setText(expenditure.getExpenditureDate());
        commentDetail.setText(expenditure.getExpenditureComment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        String comment = commentDetail.getText().toString();
        MenuItem item = menu.findItem(R.id.action_share);
        item.setIntent(shareExpenditure(comment));
        return true;
    }

    private Intent shareExpenditure(String comment){
        String mimeType = "text/plain";
       Intent intent =  ShareCompat.IntentBuilder.from(this)
                .setChooserTitle("Choose action")
                .setType(mimeType)
                .setText("#Expenditure App \n"+comment)
               .getIntent();
       return intent;
    }
}
