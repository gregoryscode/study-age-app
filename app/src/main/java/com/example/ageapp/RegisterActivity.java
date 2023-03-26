package com.example.ageapp;

import android.content.ContentValues;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    private EditText _txtName;
    private Spinner _spinnerAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _txtName = (EditText)findViewById(R.id.txtName);
        _spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        FloatingActionButton fabSave = findViewById(R.id.fabSave);
        FloatingActionButton fabClear = findViewById(R.id.fabClear);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(_txtName.getText().toString().trim().length() == 0) {
                        ShowMessage(view, getString(R.string.app_user_name_empty));
                        return;
                    }

                    RegisterUser();
                    ShowMessage(view, getString(R.string.app_user_create_success));
                }
                catch (Exception e) {
                    ShowMessage(view, getString(R.string.app_user_create_error));
                }
            }
        });

        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear();
            }
        });

        SetupSpinnerAge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetupSpinnerAge() {
        String ages[] = {getString(R.string.app_age_one), getString(R.string.app_age_two), getString(R.string.app_age_three), getString(R.string.app_age_four), getString(R.string.app_age_five), getString(R.string.app_age_six)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerAge.setAdapter(spinnerAdapter);
    }

    private void RegisterUser() {
        ContentValues data = new ContentValues();
        data.put("name", _txtName.getText().toString().toUpperCase());
        data.put("age", _spinnerAge.getSelectedItem().toString());
        DBAdapter.getConnection(getApplicationContext()).insert("user", null, data);
        DBAdapter.close();
        Clear();
    }

    private void Clear() {
        _txtName.setText("");
        _spinnerAge.setSelection(0);
        _txtName.requestFocus();
    }

    private void ShowMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.app_ok), null).show();
    }
}
