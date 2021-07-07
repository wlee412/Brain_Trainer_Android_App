package com.example.braintrainnerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    TextView resultTextView;
    TextView pointTextView;
    TextView timerTextView;
    TextView percentTextView;
    Button playAgainButton;
    RelativeLayout gameScreen;
    RelativeLayout startScreen;

    MediaPlayer startSound;
    MediaPlayer gameOverSound;
    MediaPlayer clickSound;

    Button b0;
    Button b1;
    Button b2;
    Button b3;

    TextView sumText;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        pointTextView.setText("0/0");
        resultTextView.setText(" ");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();

        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }
            public void onFinish(){
                resultTextView.setText("Your Score:" + Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
                timerTextView.setText("0s");
                playAgainButton.setVisibility(RelativeLayout.VISIBLE);
                gameOverSound.start();
            }
        }.start();
    }
    public void generateQuestion() {
        answers.clear();
        Random ran = new Random();
        int a = ran.nextInt(21);
        int b = ran.nextInt(21);

        if(b > a){
            int temp = a;
            a = b;
            b = temp;
        }

        int opNum = (int) (Math.random() * 4) + 1;
        int result = 0;
        char operator = ' ';

        switch (opNum) {
            case 1:
                result = a + b;
                operator = '+';
                break;
            case 2:
                result = a - b;
                operator = '-';
                break;
            case 3:
                result = a * b;
                operator = '*';
                break;
            case 4:
                result = a / b;
                operator = '/';
                break;
        }



        sumText.setText(Integer.toString(a) + " " + operator + " " + Integer.toString(b));

        locationOfCorrectAnswer = ran.nextInt(4);


        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(result);
            } else {
                incorrectAnswer = ran.nextInt(41);
                while (incorrectAnswer == result) {
                    incorrectAnswer = ran.nextInt(41);
                }
                answers.add(incorrectAnswer);

            }
        }
        b0.setText(Integer.toString(answers.get(0)));
        b1.setText(Integer.toString(answers.get(1)));
        b2.setText(Integer.toString(answers.get(2)));
        b3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view) {
        clickSound.start();

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct! ");
            resultTextView.setTextColor(0xFF96DC5C);


        } else {
            resultTextView.setText("Wrong :(" );
            resultTextView.setTextColor(0xFFED5975);
        }

        numberOfQuestions++;
        pointTextView.setText(Integer.toString(score) + " / " + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void start(View view) {
        startScreen.setVisibility(View.INVISIBLE);
        gameScreen.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));

        startSound.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        sumText = (TextView) findViewById(R.id.sumTextView);
        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);

        resultTextView = (TextView) findViewById(R.id.resultTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        pointTextView = (TextView) findViewById(R.id.pointsView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        gameScreen = (RelativeLayout)findViewById(R.id.gameScreen);
        startScreen = (RelativeLayout)findViewById(R.id.startScreen);


        gameOverSound = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        startSound = MediaPlayer.create(getApplicationContext(), R.raw.go_sound);
        clickSound = MediaPlayer.create(getApplicationContext(), R.raw.mouse_click_sound);




    }
}