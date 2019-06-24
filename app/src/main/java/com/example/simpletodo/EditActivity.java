package com.example.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static com.example.simpletodo.MainActivity.positionitem;
import static com.example.simpletodo.MainActivity.text;

public class EditActivity extends AppCompatActivity {
    int position;
    EditText edit;
    Button button_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setTitle("Edit Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit = (EditText)findViewById(R.id.editText);
        button_edit = (Button) findViewById(R.id.editsave);

        edit.setText(getIntent().getStringExtra(text));
        position = getIntent().getIntExtra(positionitem,0);

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemedit = edit.getText().toString();
                if (itemedit.equals("")){
                    Toast.makeText(EditActivity.this, "Enter a new item to edit", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(text,itemedit);
                    intent.putExtra(positionitem,position);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }
}
