package com.example.mycrud;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mycrud.helper.Helper;
import com.example.mycrud.model.Data;
import com.example.mycrud.adapter.Adapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> lists = new ArrayList<>();
    Adapter adapter;
    Helper ad = new Helper(this);
    Button btnAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad = new Helper(getApplicationContext());
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        listView = findViewById(R.id.list_item);
        adapter = new Adapter(MainActivity.this, lists);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String itemId = lists.get(position).getId();
                final String name = lists.get(position).getName();
                final String email = lists.get(position).getEmail();
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                                intent.putExtra("id", itemId);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                break;
                            case 1:
                                ad.delete(Integer.parseInt(itemId));
                                lists.remove(position);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }).show();
                return true;
            }
        });

        getData();
    }

    private void getData() {
        ArrayList<HashMap<String, String>> rows = ad.getAll();
        lists.clear();
        for (int i = 0; i < rows.size(); i++) {
            String id = rows.get(i).get("id");
            String name = rows.get(i).get("name");
            String email = rows.get(i).get("email");

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setEmail(email);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        lists.clear();
        getData();
    }
}