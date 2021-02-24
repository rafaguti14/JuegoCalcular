package com.example.juegocalcular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {
    PieChart pc;
    PieData pd;

    int aciertos,fallos,racha = 0;

    SharedPreferences sp;
    SharedPreferences spStats;
    SharedPreferences.Editor editor;

    TextView tv_aciertos,tv_fallos,tv_racha;

    ArrayList<PieEntry> pieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        getSupportActionBar().hide();
        sp=getSharedPreferences("estadisticas",this.MODE_PRIVATE);
        spStats=getSharedPreferences("estadisticas",this.MODE_PRIVATE);
        editor=spStats.edit();

        if(spStats.getBoolean("cuenta",false)){
            Intent intent=getIntent();
            aciertos=intent.getIntExtra("aciertos",0);
            fallos=intent.getIntExtra("fallos",0);
            racha=intent.getIntExtra("racha", 0);

            editor.putBoolean("cuenta",false);
            editor.commit();
        }else{
            aciertos = sp.getInt("totalaciertos",0);
            fallos = sp.getInt("totalfallos",0);
            racha = sp.getInt("totalracha",0);
        }

        tv_aciertos=(TextView)findViewById(R.id.tv_stats_aciertos);
        tv_fallos=(TextView)findViewById(R.id.tv_stats_fallo);
        tv_racha=(TextView)findViewById(R.id.tv_stats_racha);
        tv_aciertos.setText(Integer.toString(aciertos));
        tv_fallos.setText(Integer.toString(fallos));
        tv_racha.setText(Integer.toString(racha));
        hacerPieChart();
    }

    private void hacerPieChart(){
        pieList = new ArrayList<>();
        pc = findViewById(R.id.pieChart);

        pieList.add(new PieEntry(aciertos,"aciertos"));
        pieList.add(new PieEntry(fallos,"fallos"));

        PieDataSet pieDataSet = new PieDataSet(pieList,"");

        pd = new PieData(pieDataSet);
        pc.setData(pd);
        pc.invalidate();

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pc.getDescription().setText("");
        Legend l = pc.getLegend();
        l.setTextSize(18f);
        pd.setValueTextSize(18f);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this,PantallaInicio.class);
        startActivity(intent);
    }

}