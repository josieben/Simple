package com.example.simpletodo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    public static int code =1;
    public static String text ="edittext";
    public static String positionitem = "position";
    ArrayAdapter arrayAdapter;
    ListView listView;
    Button button_add;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.lv);


        getSupportActionBar().setTitle("SimpleTodo");
        arrayList = new ArrayList<>();

        button_add = (Button)findViewById(R.id.add);
        editText = (EditText)findViewById(R.id.edtadd);

        readitem();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);



        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editText.getText().toString();
                if (item.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter a new item", Toast.LENGTH_SHORT).show();
                }else{
                    arrayAdapter.add(item);
                    Writeitem();
                    Toast.makeText(MainActivity.this, "Added Item to List", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra(text,arrayList.get(position));
                intent.putExtra(positionitem,position);
                startActivityForResult(intent,code);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == code){
            String textupdate = data.getExtras().getString(text);
            int postioniupadte = data.getExtras().getInt(positionitem);
            arrayList.set(postioniupadte,textupdate);
            arrayAdapter.notifyDataSetChanged();
            Writeitem();
            Toast.makeText(this, "Updated Item to list", Toast.LENGTH_SHORT).show();
        }
    }

    private File getitem(){
        return new File(getFilesDir(),"list.txt");
    }
    private void readitem(){
        try {
           arrayList = new ArrayList<>(FileUtils.readLines(getitem(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Writeitem() {
        try {
            FileUtils.writeLines(getitem(),arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
