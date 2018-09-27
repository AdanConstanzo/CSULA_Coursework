package com.example.mesargent.travistestapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	// hint: fix this line to pass the auto test
            	// after pushing your changes to github check the progress of your auto-test 
            	// by first logging into your github account
            	// then going to https://travis-ci.com/cs4540/push-the-button-yourusername
            	// (replace yourusername with your github user name)
            	// click the 'login using github' button
            	// it should take about 10 minutes for the test to run
                textView.setText("Thanks for being so passionate!");
            }
        });

    }
}
