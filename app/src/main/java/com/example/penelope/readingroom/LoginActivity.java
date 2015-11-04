package com.example.penelope.readingroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Preferences.hasUsername(this)) {
            gotoSelectBookListActivity();
            return;
        }

        final EditText enterName = (EditText) findViewById(R.id.enter_name_field);
        final Button getStarted = (Button) findViewById(R.id.get_started_button);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enterName.getText().toString();
                Preferences.setUsername(LoginActivity.this, username);
                gotoSelectBookListActivity();
            }
        });

        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getStarted.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getStarted.setEnabled(enterName.getText().toString().length() > 0);
    }

    private void gotoSelectBookListActivity() {
        Intent intent = new Intent(LoginActivity.this, SelectBookListActivity.class);
        startActivity(intent);
        finish();
    }
}
