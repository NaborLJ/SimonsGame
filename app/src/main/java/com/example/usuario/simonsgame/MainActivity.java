package com.example.usuario.simonsgame;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.*;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mpb,mpr,mpg,mpy,mpf,mpw;

    TextView Text;
    Button ButtonGreen, ButtonYellow, ButtonRed, ButtonBlue, Start;
    TimerTask JobTimer;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mpb = MediaPlayer.create(this, R.raw.blue);
        mpg = MediaPlayer.create(this, R.raw.green);
        mpr = MediaPlayer.create(this, R.raw.red);
        mpy = MediaPlayer.create(this, R.raw.yellow);
        mpw = MediaPlayer.create(this, R.raw.win);
        mpf = MediaPlayer.create(this, R.raw.loose);

        Start = (Button) findViewById(R.id.buttonStart);
        ButtonGreen= (Button) findViewById(R.id.buttonGreen);
        ButtonRed = (Button) findViewById(R.id.buttonRed);
        ButtonBlue = (Button) findViewById(R.id.buttonBlue);
        ButtonYellow = (Button) findViewById(R.id.buttonYellow);
        Text = (TextView) findViewById(R.id.textView);





    }
    ArrayList<Integer> randomColor = new ArrayList();
    ArrayList<Integer> ChosenColor = new ArrayList();
    protected static int LVL=3; // Marcará el nivel (número de colores que aparecerán).
    protected static int CONT=0;// Cuenta las veces que pulsamos un botón.

    int Level=1;
    void StartEvent(View a){ //Ejecutará el juego y marcará en un TextView la dificultad en la que nos encontramos

        CONT =0;
        StartTimer();
        Text.setText("Level : "+Level);

    }
    void InitTimer (){ // Este metodo hará que los parpadeos de los botones se respetarán y no se colapsarán al aparecer todos a la vez.
        JobTimer = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int numero=Random();
                        randomColor.add(numero);

                        if (numero == 0) {
                            ButtonGreen.setBackgroundColor(Color.GREEN);
                            mpg.start();
                            ButtonGreen.postDelayed(new Runnable() {
                                public void run() {
                                    ButtonGreen.setBackgroundColor(Color.parseColor("#1EA307"));
                                }
                            }, 500);

                        }

                        if (numero == 1) {
                            ButtonRed.setBackgroundColor(Color.RED);
                            mpr.start();
                            ButtonRed.postDelayed(new Runnable() {
                                public void run() {
                                    ButtonRed.setBackgroundColor(Color.parseColor("#CD3813"));
                                }
                            }, 500);


                        }
                        if (numero == 2) {
                            ButtonBlue.setBackgroundColor(Color.BLUE);
                            mpb.start();
                            ButtonBlue.postDelayed(new Runnable() {
                                public void run() {
                                    ButtonBlue.setBackgroundColor(Color.parseColor("#136CF1"));
                                }
                            }, 500);

                        }
                        if (numero == 3) {
                            ButtonYellow.setBackgroundColor(Color.YELLOW);
                            mpy.start();
                            ButtonYellow.postDelayed(new Runnable() {
                                public void run() {
                                    ButtonYellow.setBackgroundColor(Color.parseColor("#D4E113"));
                                }
                            }, 500);


                        }
                        CONT++;
                        if(CONT==LVL){
                            StopTimer();
                            CONT=0;
                        }

                    }
                });

            }
        };
        LVL++;



    }

    void GreenEvent(View gr){
        ButtonGreen = (Button) findViewById(R.id.buttonGreen);
        mpg.start();
        ChosenColor.add(0);
        ButtonGreen.setBackgroundColor(Color.GREEN);

        final long changeTime = 200L;
        ButtonGreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                ButtonGreen.setBackgroundColor(Color.parseColor("#1EA307"));

            }
        }, changeTime);
        CONT++;
        Check();

    }
    void RedEvent(View r){
        final Button ButtonRed = (Button) findViewById(R.id.buttonRed);
        mpr.start();
        ChosenColor.add(1);
        ButtonRed.setBackgroundColor(Color.RED);
        final long changeTime = 200L;
        ButtonRed.postDelayed(new Runnable() {
            @Override
            public void run() {
                ButtonRed.setBackgroundColor(Color.parseColor("#CD3813"));

            }
        }, changeTime);
        CONT++;
        Check();

    }
    void BlueEvent(View bl){
        final Button ButtonBlue = (Button) findViewById(R.id.buttonBlue);
        mpb.start();
        ChosenColor.add(2);
        ButtonBlue.setBackgroundColor(Color.BLUE);
        final long changeTime = 200L;
        ButtonBlue.postDelayed(new Runnable() {
            @Override
            public void run() {
                ButtonBlue.setBackgroundColor(Color.parseColor("#136CF1"));

            }
        }, changeTime);
        CONT++;
        Check();

    }

    void YellowEvent(View ye){
        final Button ButtonYellow = (Button) findViewById(R.id.buttonYellow);
        mpy.start();
        ChosenColor.add(3);
        ButtonYellow.setBackgroundColor(Color.YELLOW);
        final long changeTime = 200L;
        ButtonYellow.postDelayed(new Runnable() {
            @Override
            public void run() {
                ButtonYellow.setBackgroundColor(Color.parseColor("#D4E113"));

            }
        }, changeTime);
        CONT++;
        Check();

    }



    public void StartTimer(){ // Llama a InitTimer ( Metodo que hace que los botones no se colapsen) y asigna el tiempo que queremos que se ejecute cada boton.
        timer = new Timer();
        InitTimer();
        timer.schedule(JobTimer, 100, 1000);
    }
    public void StopTimer(){ // Para el timer para evitar la eterna ejecución.
        if (timer !=null){
            timer.cancel();
            timer= null;
        }
    }

    void Check() { // Comprueba los Arrays donde guardamos tanto los colores generados aleatoriamente como los escogidos.
        if (CONT == LVL) { //En caso de ganar añade un color mas al array. Y limpia los colores generados anteriormente para dejar paso a los nuevos generados.
            String ran = randomColor.toString();
            String chos = ChosenColor.toString();
            if (ran.equals(chos)) {
                Toast.makeText(getApplicationContext(), "HAS GANADO", Toast.LENGTH_SHORT).show();
                mpw.start();
                int numColor = (int) Math.floor(Math.random()*4);
                randomColor.add(numColor);
                randomColor.clear();
                ChosenColor.clear();
                Level++;
            } else { // En caso de perder limpia ambos arrays y restablece la dificultad al primer nivel.
                Toast.makeText(getApplicationContext(), "HAS PERDIDO", Toast.LENGTH_SHORT).show();
                mpf.start();
                CONT=0;

                LVL=3;
                Level=1;
            }
            ChosenColor.clear();

        }
    }

    public int Random(){ // Genera los numeros aleatorios a los que les asignaremos un color.
        int numColor = (int) Math.floor(Math.random()*4);
        return numColor;



    }



}
