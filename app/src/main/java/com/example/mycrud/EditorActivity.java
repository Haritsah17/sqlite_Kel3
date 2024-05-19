package com.example.mycrud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycrud.helper.Helper;

public class EditorActivity extends AppCompatActivity {

    private EditText editName, editEmail;
    private Button btnSave;
    private Helper db = new Helper(this);
    private String id, name, email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        btnSave = findViewById(R.id.btn_save);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        if (id == null || id.equals("")){
            setTitle("Tambah User");
        } else {
            setTitle("Edit User");
            editName.setText(name);
            editEmail.setText(email);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    save();
                } catch (Exception e){
                    Log.e("Saving", e.getMessage());
                }
            }
        });
    }

    private void save() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            if (id == null || id.equals("")) {
                // Tambahkan logika untuk menambah data baru
                db.insert(name, email);
            } else {
                // Update data yang ada
                db.update(Integer.parseInt(id), name, email);
            }
            finish();
        }
    }
}
