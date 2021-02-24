package com.example.juegocalcular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void empezarJuego(View v){
        Intent intent = new Intent(this, Calculadora.class);
        startActivity(intent);
    }
    public void irConfiguracion(View v){
        Intent intent = new Intent(this, Configuracion.class);
        startActivity(intent);
    }
    public void  estadisticas(View v){
        Intent intent = new Intent(this, Estadisticas.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}