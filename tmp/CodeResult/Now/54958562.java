package com.example.now;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Now extends Activity implements OnClickListener {
	Button button;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        updateTime();
        /*
        button = new Button(this);
        button.setOnClickListener(this);
        setContentView(R.layout.main);
        updateTime();
        setContentView(button);
        */
    }
    
    public void onClick(View view){
    	updateTime();
    }
    
    private void updateTime(){
    	button.setText(new Date().toString());
    	
    }
}