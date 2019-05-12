package com.example.whackthedroid;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private boolean clicked = true;
    private int counter;
    private int all;
    private TextView counterView;
    private TextView missView;

    private Handler handler = new Handler();
    private ImageView whackView1;
    private ImageView whackView2;
    private ImageView whackView3;
    private ImageView whackView4;
    private ImageView whackView5;
    private ImageView whackView6;
    private ImageView whackView7;
    private ImageView whackView8;
    private ImageView whackView9;

    private Button startButton;
    private Button stopButton;
    private Random rand = new Random();
    private ArrayList<ImageView> whackViews = new ArrayList<ImageView>();
    private ImageView actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.counterView = findViewById(R.id.countView);
        this.missView = findViewById(R.id.missView);

        this.whackView1 = findViewById(R.id.whackView1);
        this.whackView2 = findViewById(R.id.whackView2);
        this.whackView3 = findViewById(R.id.whackView3);
        this.whackView4 = findViewById(R.id.whackView4);
        this.whackView5 = findViewById(R.id.whackView5);
        this.whackView6 = findViewById(R.id.whackView6);
        this.whackView7 = findViewById(R.id.whackView7);
        this.whackView8 = findViewById(R.id.whackView8);
        this.whackView9 = findViewById(R.id.whackView9);

        this.startButton = findViewById(R.id.startButton);
        this.stopButton = findViewById(R.id.stopButton);


        if(savedInstanceState != null && !savedInstanceState.isEmpty()) {
            this.counter = savedInstanceState.getInt("counter");
            this.counterView.setText("Hit: " + String.valueOf(this.counter));
            this.all = savedInstanceState.getInt("all");
            this.missView.setText("Miss: " + String.valueOf(this.all));
        } else {
            this.counter = 0;
            this.all = 0;

            this.whackViews.add(0,whackView1);
            this.whackViews.add(1,whackView2);
            this.whackViews.add(2,whackView3);
            this.whackViews.add(3,whackView4);
            this.whackViews.add(4,whackView5);
            this.whackViews.add(5,whackView6);
            this.whackViews.add(6,whackView7);
            this.whackViews.add(7,whackView8);
            this.whackViews.add(8,whackView9);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", this.counter);
        outState.putInt("all",this.all);
    }



    public void startGame(View view){
        this.startButton.setVisibility(View.INVISIBLE);
        this.stopButton.setVisibility(View.VISIBLE);
        int n = rand.nextInt(9);
        actual = whackViews.get(n);
        ImageView tmp = whackViews.get(0);
        whackViews.set(0, actual);
        whackViews.set(n,tmp);

        actual.setVisibility(View.VISIBLE);
        actual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    if (actual.getVisibility() == View.VISIBLE) {
                        actual.setVisibility(View.INVISIBLE);
                        counter++;
                        all++;
                        counterView.setText("Hit: " + String.valueOf(counter));
                        missView.setText("Miss: " + String.valueOf(all-counter));
                        inGameRunnable.run();
                    }
            }
        });

    }

    private Runnable inGameRunnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacksAndMessages(null);
            actual.setVisibility(View.INVISIBLE);
            all++;
            int n = rand.nextInt(8);
            n++;
            missView.setText("Miss: " + String.valueOf(all - counter));

            actual = whackViews.get(n);
            ImageView tmp = whackViews.get(0);
            whackViews.set(0, actual);
            whackViews.set(n, tmp);
            actual.setVisibility(View.VISIBLE);

            actual.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (actual.getVisibility() == View.VISIBLE) {
                        handler.removeCallbacksAndMessages(null);

                        counter++;
                        counterView.setText("Hit: " + String.valueOf(counter));
                        handler.postDelayed(inGameRunnable,0);
                    }
                }
            });


            handler.postDelayed(this,1000);

        }
    };


    public void stopGame(View view){
        this.stopButton.setVisibility(View.INVISIBLE);
        this.startButton.setVisibility(View.VISIBLE);

        this.counter=0;
        this.all = 0;
        counterView.setText("Hit: " + String.valueOf(counter));
        missView.setText("Miss: " + String.valueOf(all-counter));
        actual.setVisibility(View.INVISIBLE);
        handler.removeCallbacks(inGameRunnable);
    }

}
