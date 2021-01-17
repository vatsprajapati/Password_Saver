package com.bce.passwordsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AddEditItemActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.bce.passwordsaver.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.bce.passwordsaver.EXTRA_TITLE";
    public static final String EXTRA_USERID = "com.bce.passwordsaver.EXTRA_USERID";
    public static final String EXTRA_PASSWORD = "com.bce.passwordsaver.EXTRA_PASSWORD";
    public static final String EXTRA_DESCRIPTION = "com.bce.passwordsaver.EXTRA_DESCRIPTION";

    private EditText editTextTitle;
    private EditText editTextUserId;
    private EditText editTextPassword;
    private EditText editTextDescription;
    Button saveButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        editTextTitle = findViewById(R.id.titleEditText);
        editTextUserId = findViewById(R.id.userIdEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        editTextDescription = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveItemButton);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Information");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextUserId.setText(intent.getStringExtra(EXTRA_USERID));
            editTextPassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Information");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInformation();
            }
        });
    }

    private void saveInformation() {
        String title = editTextTitle.getText().toString();
        String userid = editTextUserId.getText().toString();
        String password = editTextPassword.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || userid.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter All Necessary Information.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_USERID, userid);
        data.putExtra(EXTRA_PASSWORD, password);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

}
