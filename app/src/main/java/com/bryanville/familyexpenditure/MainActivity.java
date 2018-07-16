package com.bryanville.familyexpenditure;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bryanville.familyexpenditure.adapters.ExpenditureAdapter;
import com.bryanville.familyexpenditure.database.Expenditure;
import com.bryanville.familyexpenditure.database.ExpenditureDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpenditureAdapter.ExpenditureListItemClickListener {
    private RecyclerView mRecyclerView;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    ExpenditureAdapter expenditureAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.expenditureRecyclerView);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewExpenditureActivity.class);
                startActivity(intent);
            }
        });
        expenditureAdapter = new ExpenditureAdapter(this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(expenditureAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<Expenditure> expenditures = expenditureAdapter.getExpenditure();
                getExpenditureDatabaseInstance().expenditureDao().deleteExpenditure(expenditures.get(position));

            }
        }).attachToRecyclerView(mRecyclerView);
        //retrieveAllExpenditure();


    }


    @Override
    protected void onResume() {
        super.onResume();
        retrieveAllExpenditure();
    }

    private void retrieveAllExpenditure() {
        final LiveData<List<Expenditure>> expenditures = getExpenditureDatabaseInstance().expenditureDao().queryAllExpenditure();
        //ExpenditureViewModel viewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);
        expenditures.observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> list) {
                Log.d(LOG_TAG, "Database update message from LiveData ");
                expenditureAdapter.refreshLists(list);
            }
        });
    }

    public ExpenditureDatabase getExpenditureDatabaseInstance() {
        String dbName = "expenditure_db";
        ExpenditureDatabase expenditureDatabase = Room.databaseBuilder(MainActivity.this,
                ExpenditureDatabase.class,
                dbName)
                .allowMainThreadQueries()
                .build();
        return expenditureDatabase;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int itemId) {
        Intent takeMeToDetailsActivity = new Intent(this, ExpenditureDetailsActivity.class);
        takeMeToDetailsActivity.putExtra(ExpenditureDetailsActivity.EXTRA_EXPENDITURE_ID, itemId);
        startActivity(takeMeToDetailsActivity);
    }

}
