package com.example.juegocalcular;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Cuenta implements Parcelable {
    int a,b,operator = 0;
    String operadores[];

    public Cuenta(int maxA, int maxB,String[] operaciones) {
        Random r = new Random();
        a = r.nextInt(maxA+1);
        b = r.nextInt(maxB+1);
        operadores=operaciones;
        operator = r.nextInt(operaciones.length);

        if(b>a){
            int aux = a;
            a = b;
            b = aux;
        }
    }


    static String[] getOperators(){
        String[] operators = new String[]{"+", "-", "*"};
        return operators;
    }

    public String print(){
        String cuenta = Integer.toString(a);
        cuenta += " "+Cuenta.getOperators()[operator]+" ";
        cuenta += Integer.toString(b);
        return cuenta;
    }

    public int resolver(){
        int resultado = -1;
        if(operadores[operator].equals("+")){
            resultado=a+b;
        }else if(operadores[operator].equals("-")){
            resultado = a-b;
        }else if(operadores[operator].equals("*")){
            resultado = a*b;
        }

        return resultado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.a);
        dest.writeInt(this.b);
        dest.writeInt(this.operator);
    }

    protected Cuenta(Parcel in) {
        this.a = in.readInt();
        this.b = in.readInt();
        this.operator = in.readInt();
    }

    public static final Creator<Cuenta> CREATOR = new Creator<Cuenta>() {
        @Override
        public Cuenta createFromParcel(Parcel source) {
            return new Cuenta(source);
        }

        @Override
        public Cuenta[] newArray(int size) {
            return new Cuenta[size];
        }
    };
}
