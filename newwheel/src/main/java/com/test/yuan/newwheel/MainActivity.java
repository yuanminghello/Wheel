package com.test.yuan.newwheel;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    LuckPan pan;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pan = findViewById(R.id.pan);

        iv = findViewById(R.id.btn);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pan.isStart()){
                    iv.setImageResource(R.drawable.stop);
                    pan.mSpeed = 38 ;
                }else {
                    iv.setImageResource(R.drawable.start);
                    pan.isShouldEnd = true;
                }
            }
        });
    }
}
