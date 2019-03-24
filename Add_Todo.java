package com.example.praneet.todo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Todo extends AppCompatActivity {

    private EditText title, subtitle;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        title = findViewById(R.id.add_title);
        subtitle = findViewById(R.id.add_subtitle);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(subtitle.getWindowToken(), 0);

                if(TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(subtitle.getText().toString())){

                    Toast.makeText(Add_Todo.this, "You can't leave inputs blank", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("subtitle", subtitle.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
