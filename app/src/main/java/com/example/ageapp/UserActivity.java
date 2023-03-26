package com.example.ageapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private View _parentLayout;
    private ListView _listUser;
    private TextView _txtNoUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _parentLayout = (CoordinatorLayout)findViewById(R.id.layoutMain);
        _txtNoUsers = (TextView)findViewById(R.id.txtNoUsers);
        _listUser = (ListView) findViewById(R.id.listUser);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_about:
                ShowMessage(_parentLayout, getString(R.string.app_about));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void LoadUsers() {

        try {
            ArrayList<String> users = new ArrayList<>();

            users = DBAdapter.getUsers(getApplicationContext());

            if(users.size()== 0) {
                _txtNoUsers.setVisibility(View.VISIBLE);
                _listUser.setVisibility(View.GONE);
            }

            _txtNoUsers.setVisibility(View.GONE);
            _listUser.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
            _listUser.setAdapter(adapter);
        }
        catch (Exception e) {
            ShowMessage(_parentLayout, getString(R.string.app_user_not_loaded));
        }
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
