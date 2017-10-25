package com.example.caesar.osystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana

        bt1=(Button) findViewById(R.id.angry_btn);
        bt2=(Button) findViewById(R.id.angry_btn2);
        
    }

    public void jugar(View v){
        Intent jugar1=new Intent(this, MainActivity.class);
        startActivity(jugar1);
    }

    public void jugardificil(View v){
        Intent jugardificil1=new Intent(this, Dificil.class);
        startActivity(jugardificil1);
    }

    public void opciones(View v){
        Intent opciones=new Intent(this, Opciones.class);
        startActivity(opciones);
    }

    public Dialog creaAlerta(String titulo, String mensaje){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No se
            }
        });
        return builder.create();
    }

    public void info(View v){
        creaAlerta("Info","Okay").show();
    }
}