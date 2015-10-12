package com.backstopmedia.kotlin.chapter3.hello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backstopmedia.kotlin.chapter3.R;

import static android.view.View.OnClickListener;

public class HelloActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        final EditText editText = (EditText) findViewById(R.id.editText);
        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                button.setText("Hello " + name + "!");
            }
        });
    }
}
