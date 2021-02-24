package com.example.juegocalcular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Calculadora extends AppCompatActivity {

    int contador_racha,acertadas, falladas,racha = 0;
    int maxA,maxB;
    int restantes = 20;
    Cuenta actual;
    LinearLayout log;
    ScrollView sv;
    TextView lcd;
    Button borrar;
    ImageView izq,der;
    TextView tiempo;
    SharedPreferences sp, spStats;
    SharedPreferences.Editor editor;
    String[] operadores;
    Contador cont;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        getSupportActionBar().hide();

        log = (LinearLayout) findViewById(R.id.ll_datos);
        sv = (ScrollView) findViewById(R.id.sv_datos);
        lcd = (TextView) findViewById(R.id.tv_lcd);
        tiempo=(TextView)findViewById(R.id.tv_cal_crono);
        izq=(ImageView)findViewById(R.id.iv_cal_izq);
        der=(ImageView)findViewById(R.id.iv_cal_der);
        sp=getSharedPreferences("sp1",this.MODE_PRIVATE);
        spStats=getSharedPreferences("estadisticas",this.MODE_PRIVATE);
        editor=spStats.edit();
         maxA=sp.getInt("A",0);
         maxB=sp.getInt("B",0);
         int ca=sp.getInt("ca",0);
         if(ca==0){
             tiempo.setVisibility(View.INVISIBLE);
             izq.setVisibility(View.INVISIBLE);
             der.setVisibility(View.INVISIBLE);
         }else{
             tiempo.setVisibility(View.VISIBLE);
             izq.setVisibility(View.VISIBLE);
             der.setVisibility(View.VISIBLE);
             int ti=sp.getInt("tiempo",0);
             tiempo.setText(Integer.toString(ti));
             cont=new Contador(ti*1000,1000);
             cont.start();
         }


          String aux="";
         if(sp.getBoolean("suma",false)){
             aux+="+ ";
         }
         if(sp.getBoolean("resta",false)){
             aux+="- ";

         }
         if(sp.getBoolean("mult",false)){
             aux+="* ";
         }
         operadores=aux.split(" ");
        ponerCuenta();
    }

    public class Contador extends CountDownTimer {


        public Contador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            int ani= sp.getInt("ani",0);
            int t=Integer.parseInt(tiempo.getText().toString());
            t-=1;
            tiempo.setText(Integer.toString(t));
            if(ani==1){
                if(izq.getVisibility()==View.VISIBLE){
                    izq.setVisibility(View.INVISIBLE);
                    der.setVisibility(View.INVISIBLE);
                }else{
                    izq.setVisibility(View.VISIBLE);
                    der.setVisibility(View.VISIBLE);
                }

            }else if(ani==2){
                if(izq.getVisibility()==View.VISIBLE){
                    izq.setVisibility(View.INVISIBLE);
                    der.setVisibility(View.VISIBLE);
                }else{
                    izq.setVisibility(View.VISIBLE);
                    der.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onFinish() {
            ponerFin();
            
        }
    }



    public TextView crearTexto(String s){
        TextView texto = new TextView(this);
        texto.setText(s);
        texto.setTextSize(25);
        texto.setTextColor(Color.BLACK);
        texto.setPadding(32, 8, 0, 8);
        return texto;
    }

    public void ponerCuenta(){
        actual = new Cuenta(maxA,maxB,operadores);
        TextView cuentaMsg = crearTexto(actual.print());
        log.addView(cuentaMsg);
        restantes -= 1;
    }

    public void ponerAcierto(){
        TextView aciertoMsg = crearTexto("Has acertado");
        aciertoMsg.setTextColor(Color.GREEN);
        log.addView(aciertoMsg);
        acertadas += 1;
        contador_racha +=1;

        if (contador_racha>racha){
            racha = contador_racha;
        }
    }

    public void ponerFallo(){
        TextView falloMsg = crearTexto("Has fallado");
        falloMsg.setTextColor(Color.RED);
        log.addView(falloMsg);
        falladas += 1;
        contador_racha = 0;
    }

    public void ponerFin(){
        guardarStats();
        Intent intent = new Intent(getApplicationContext(),Estadisticas.class);
        intent.putExtra("aciertos",acertadas);
        intent.putExtra("fallos",falladas);
        intent.putExtra("racha",racha);
        startActivity(intent);
    }

    public void ponerNum(View v){
        Button b = (Button) v;
        String num = b.getText().toString();
        String actual = lcd.getText().toString();
        actual += num;
        lcd.setText(actual);
    }

    public void responder(View v){
        if(lcd.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Rellena la operacion",Toast.LENGTH_SHORT).show();
        }else {
            int puesto = Integer.parseInt(lcd.getText().toString());
            lcd.setText("");
            int resultado = actual.resolver();
            if (puesto == resultado) {
                ponerAcierto();
            } else {
                ponerFallo();
            }


            sv.post(new Runnable() {
                @Override
                public void run() {
                    sv.fullScroll(View.FOCUS_DOWN);
                }
            });

            if (restantes == 0) {
                ponerFin();
            } else {
                ponerCuenta();
            }

        }
    }
    public void borrar(View v) {

        String actual = lcd.getText().toString();
        int tam=actual.length();
        if (tam > 0) {
            actual=actual.substring(0,tam-1);
            lcd.setText(actual);
        }
    }
    private void guardarStats(){
        if (racha > spStats.getInt("totalracha",0)){
            editor.putInt("totalracha",racha);
        }

        int sumAciertos= spStats.getInt("totalaciertos",0);
        sumAciertos+=acertadas;
        editor.putInt("totalaciertos",sumAciertos);

        int sumFallos=spStats.getInt("totalfallos",0);
        sumFallos += falladas;
        editor.putInt("totalfallos",sumFallos);
        editor.putBoolean("cuenta",true);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        int ca=sp.getInt("ca",0);
        if(ca==0){
            cont.cancel();
        }
        finish();
        Intent intent = new Intent(this,PantallaInicio.class);
        startActivity(intent);
    }



}