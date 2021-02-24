package com.example.juegocalcular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Configuracion extends AppCompatActivity {
    Button menosA,menosB,masA,masB,guardar;
    TextView textoA,textoB,tv_tiempo;
    Switch cuentaAtras;
    TextInputEditText texto_cuentaAtras;
    CheckBox suma,resta,mult;
    Spinner spinner;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextInputLayout til;
    String array[]={"Sin animación","Tipo 1","Tipo 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuracion);
        getSupportActionBar().hide();
        sp=getSharedPreferences("sp1",this.MODE_PRIVATE);
        editor=sp.edit();

        menosA=(Button)findViewById(R.id.b_config_menos_a);
        menosB=(Button)findViewById(R.id.b_config_menos_b);
        masA=(Button)findViewById(R.id.b_config_mas_a);
        masB=(Button)findViewById(R.id.b_config_mas_b);
        guardar=(Button)findViewById(R.id.b_config_guardar);
        textoA=(TextView) findViewById(R.id.tv_config_a);
        textoB=(TextView)findViewById(R.id.tv_config_b);
        cuentaAtras=(Switch)findViewById(R.id.sw_config_cuenta_atras);
        texto_cuentaAtras=(TextInputEditText)findViewById(R.id.ti_config_cuenta_atras);
        suma=(CheckBox)findViewById(R.id.cb_config_suma);
        resta=(CheckBox)findViewById(R.id.cb_config_resta);
        mult=(CheckBox)findViewById(R.id.cb_config_mult);
        spinner=(Spinner)findViewById(R.id.spi_confi);
        til=(TextInputLayout) findViewById(R.id.til_config);
        tv_tiempo=(TextView)findViewById(R.id.tv_tiempo_config);




        int CA=sp.getInt("ca",0);

        if(CA==1){
            cuentaAtras.setChecked(true);
            spinner.setVisibility(View.VISIBLE);
            til.setVisibility(View.VISIBLE);
            tv_tiempo.setVisibility(View.VISIBLE);
            texto_cuentaAtras.setVisibility(View.VISIBLE);


        }else{
            texto_cuentaAtras.setEnabled(false);
            spinner.setVisibility(View.INVISIBLE);
            til.setVisibility(View.INVISIBLE);
            tv_tiempo.setVisibility(View.INVISIBLE);
            texto_cuentaAtras.setVisibility(View.INVISIBLE);
        }
        int valorA=sp.getInt("A",0);
        int valorB=sp.getInt("B",0);
        textoA.setText(Integer.toString(valorA));
        textoB.setText(Integer.toString(valorB));
        limite();


        cuentaAtras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    texto_cuentaAtras.setEnabled(true);
                    texto_cuentaAtras.requestFocus();
                    spinner.setVisibility(View.VISIBLE);
                    til.setVisibility(View.VISIBLE);
                    tv_tiempo.setVisibility(View.VISIBLE);
                    texto_cuentaAtras.setVisibility(View.VISIBLE);
                    editor.putInt("ca",1);
                    editor.commit();
                }else{
                    texto_cuentaAtras.setEnabled(false);
                    spinner.setVisibility(View.INVISIBLE);
                    til.setVisibility(View.INVISIBLE);
                    tv_tiempo.setVisibility(View.INVISIBLE);
                    texto_cuentaAtras.setVisibility(View.INVISIBLE);
                    editor.putInt("ca",0);
                    editor.commit();
                }
            }
        });

        ArrayAdapter adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("ani",i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adaptador);
        int posicion=sp.getInt("ani",0);
        spinner.setSelection(posicion);


        int t=sp.getInt("tiempo",0);
        texto_cuentaAtras.setText(Integer.toString(t));

        boolean s=sp.getBoolean("suma",false);
        boolean r=sp.getBoolean("resta",false);
        boolean m=sp.getBoolean("mult",false);
        suma.setChecked(s);
        resta.setChecked(r);
        mult.setChecked(m);


    }


    public void AumentarNumero(View v){
        int numero;
        if(v.getId()==masA.getId()){
            numero=Integer.parseInt(textoA.getText().toString());
            numero++;
            textoA.setText(Integer.toString(numero));
            editor.putInt("A",numero);
            editor.commit();
            limite();
        }else if(v.getId()==masB.getId()){
            numero=Integer.parseInt(textoB.getText().toString());
            numero++;
            textoB.setText(Integer.toString(numero));
            editor.putInt("B",numero);
            editor.commit();
            limite();

        }

    }
    public void DisminuirNumero(View v){
        int numero;
        if(v.getId()==menosA.getId()){
            numero=Integer.parseInt(textoA.getText().toString());
            numero--;
            textoA.setText(Integer.toString(numero));
            limite();
            editor.putInt("A",numero);
            editor.commit();
        }else if(v.getId()==menosB.getId()){
            numero=Integer.parseInt(textoB.getText().toString());
            numero--;
            textoB.setText(Integer.toString(numero));
            limite();
            editor.putInt("B",numero);
            editor.commit();
        }

    }

    public void limite(){
        if(Integer.parseInt(textoA.getText().toString())==0){
            menosA.setEnabled(false);
        }else{
            menosA.setEnabled(true);
        }
        if(Integer.parseInt(textoB.getText().toString())==0){
            menosB.setEnabled(false);
        }else{
            menosB.setEnabled(true);
        }
    }



    public void cuentaAtras(View v) {
        String key = texto_cuentaAtras.getText().toString();
        if (cuentaAtras.isChecked()) {
            String value = texto_cuentaAtras.getText().toString();
            editor.putString(key, value);
        }
    }

        public void guardar(View v){
        Boolean verdad=true;
            String ntxt=texto_cuentaAtras.getText().toString();
            if(cuentaAtras.isChecked()) {
                if (ntxt.equals("")) {
                    verdad=false;
                    Toast.makeText(getApplicationContext(), " Datos incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putInt("tiempo", Integer.parseInt(ntxt));
                    editor.commit();

                }
            }
            if(!suma.isChecked()&&!resta.isChecked()&&!mult.isChecked()){
                Toast.makeText(getApplicationContext(),"Debes seleccionar al menos una operacion",Toast.LENGTH_SHORT).show();
                verdad=false;
            }else{
                if(suma.isChecked()){
                    editor.putBoolean("suma",true);
                }else{
                    editor.putBoolean("suma",false);
                }
                if(resta.isChecked()){
                    editor.putBoolean("resta",true);
                }else{
                    editor.putBoolean("resta",false);
                }
                if(mult.isChecked()){
                    editor.putBoolean("mult",true);
                }else{
                    editor.putBoolean("mult",false);
                }
                editor.commit();
            }
            if(verdad){
                Toast.makeText(getApplicationContext(), "Guardada", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),PantallaInicio.class);
                startActivity(intent);
            }



        }
        @Override
        public void onBackPressed(){
            finish();
            Intent intent = new Intent(getApplicationContext(),PantallaInicio.class);
            startActivity(intent);
        }

    }








