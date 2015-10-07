package com.backstopmedia.kotlin.hello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backstopmedia.kotlin.R;

import static android.view.View.OnClickListener;

public class JavaHelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JavaHelloActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
                button.setText("Thanks!");
            }
        });
    }
}
