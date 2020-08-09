package com.example.braintrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button option1,option2,option3,option4,playagain,refresher;
    TextView timeleft,answers,anscheck,question,timedecide;
    SeekBar timechanger;
    CountDownTimer timer;
    int total=0,correct=0,correctans,correctanstag,attempt=0,totaltime=30;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        option1=(Button)findViewById(R.id.option1);
        option2=(Button)findViewById(R.id.option2);
        option3=(Button)findViewById(R.id.option3);
        option4=(Button)findViewById(R.id.option4);
        playagain=(Button)findViewById(R.id.playagain);
        timeleft=(TextView)findViewById(R.id.timer);
        answers=(TextView)findViewById(R.id.answers);
        anscheck=(TextView)findViewById(R.id.anscheck);
        question=(TextView)findViewById(R.id.question);
        refresher=(Button)findViewById(R.id.changetime);
        timedecide=(TextView)findViewById(R.id.timetextview);
        timechanger=(SeekBar)findViewById(R.id.timesetter);
        timechanger.setMin(10);
        timechanger.setMax(180);
        timechanger.setProgress(30);
        timechanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int min=i/60;
                int seconds=i-(min*60);
                String secondstring=String.valueOf(seconds);
                if(secondstring.length()==1){
                    secondstring="0"+secondstring;
                }
                timedecide.setText(min+":"+secondstring);
                totaltime=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void newquestion(View view){
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
        int o1,o2,r4=4,r5=0;
        String c[]={"+","*","-"};
        o1= (int) Math.ceil(Math.random()*50);
        o2= (int) Math.ceil(Math.random()*50);
        while(r4>=3)
            r4=(int) Math.floor(Math.random()*3);
        if(r4!=2 || (r4==2 && o1>o2)) {
            question.setText(o1 + "" + c[r4] + "" + o2);
        }
        else{
            question.setText(o2 + "" + c[r4] + "" + o1);
        }
        while(r5==0)
            r5=(int) Math.ceil(Math.random()*4);
        if(r4==0){
            correctans=o1+o2;
        }
        else if(r4==1){
            correctans=o1*o2;
        }
        else if(r4==2 && o1>o2){
            correctans=o1-o2;
        }
        else{
            correctans=o2-o1;
        }
        correctanstag=r5;
        Integer optionarray[]=new Integer[4];
        for(int i=1;i<=4;i++){
            if(i==correctanstag)
                optionarray[i-1]=correctans;
            else {
                int k=0;
                if(r4==0) {
                    k=(int) Math.ceil(Math.random() * 100);
                        while (Arrays.asList(optionarray).contains(k))
                            k= (int) Math.ceil(Math.random() * 100);
                }
                else if(r4==1) {
                    k=(int) Math.ceil(Math.random() * 2500);
                        while (Arrays.asList(optionarray).contains(k))
                            k= (int) Math.ceil(Math.random() * 2500);
                }
                else {
                    k=(int) Math.ceil(Math.random() * 50);
                    while (Arrays.asList(optionarray).contains(k))
                        k= (int) Math.ceil(Math.random() * 50);
                }
                optionarray[i-1]=k;
            }
        }
        option1.setText(String.valueOf(optionarray[0]));
        //Log.i("Check",question.getText().toString());
        option2.setText(String.valueOf(optionarray[1]));
        option3.setText(String.valueOf(optionarray[2]));
        option4.setText(String.valueOf(optionarray[3]));
        total++;
        answers.setText(correct+"/"+total);
    }
    public void startgame( View view){
        view.setVisibility(view.GONE);
        timechanger.setVisibility(View.GONE);
        timedecide.setVisibility(View.GONE);
        //newquestion function
        try {
            newquestion(view);
        }
        catch (Exception e){
            Log.i("Error", String.valueOf(e));
        }
        option1.setVisibility(View.VISIBLE);
        option2.setVisibility(View.VISIBLE);
        option3.setVisibility(View.VISIBLE);
        option4.setVisibility(View.VISIBLE);
        timeleft.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        answers.setVisibility(View.VISIBLE);
        option1.setTag(1);
        option2.setTag(2);
        option3.setTag(3);
        option4.setTag(4);
        //anscheck.setVisibility(View.VISIBLE);
        timer=new CountDownTimer(totaltime*1000,1000) {
            @Override
            public void onTick(long l) {
                int i=(int)l/1000;
                int min=i/60;
                int seconds=i-(min*60);
                String secondstring=String.valueOf(seconds);
                if(secondstring.length()==1){
                    secondstring="0"+secondstring;
                }
               timeleft.setText(min+":"+secondstring);
            }

            @Override
            public void onFinish() {
                timeleft.setText("0:00");
                //anscheck.setText("You have answered "+correct+" questions correctly outoff "+total);
                anscheck.setText("Your Score "+correct+"/"+total);
                playagain.setVisibility(View.VISIBLE);
                refresher.setVisibility(View.VISIBLE);
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);
                Toast.makeText(MainActivity.this, "Your Score is "+correct+" out of "+total, Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    public void checkans(View view){
        //Log.i("Check ans",correctanstag+" "+view.getTag());
        anscheck.setVisibility(View.VISIBLE);
        if(correctanstag==(int)view.getTag() && attempt==0){
            correct++;
            anscheck.setText("Correct (-:");
            Toast.makeText(this, "Correct (-:", Toast.LENGTH_SHORT).show();
            newquestion(view);
        }
        else if (correctanstag==(int)view.getTag() && attempt==1){
            anscheck.setText("Correct (-:");
            newquestion(view);
            attempt=0;
        }
        else{
            anscheck.setText("Wrong )-:");
            attempt=1;
            Toast.makeText(this, "Wrong )-:", Toast.LENGTH_SHORT).show();
        }
    }
    public void newgame(View view){
        attempt=0;
        view.setVisibility(View.INVISIBLE);
        anscheck.setVisibility(View.INVISIBLE);
        refresher.setVisibility(View.INVISIBLE);
        total=0;
        correct=0;
        Button go=(Button)findViewById(R.id.go);
        startgame(go);
    }

    public void completenewgame(View view){
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}