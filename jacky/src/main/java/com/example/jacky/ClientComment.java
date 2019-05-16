package com.example.jacky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.jacky.Model.Foods;


import java.util.List;

public class ClientComment extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_foods);
        new FirebaseDatabaseHelper().readMenu(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Foods> foods, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, ClientComment.this, foods, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foodlist_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_comment:
                startActivity(new Intent(new Intent(this, NewFoodActivity.class)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
