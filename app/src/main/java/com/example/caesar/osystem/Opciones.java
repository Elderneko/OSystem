package com.example.caesar.osystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;


public class Opciones extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana


    }

}

