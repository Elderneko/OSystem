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
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private Button bt1, bt2;
    private Bundle datosOpciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana

        bt1=(Button) findViewById(R.id.angry_btn);
        bt2=(Button) findViewById(R.id.angry_btn2);

        // Recojo datos de opciones
        datosOpciones = this.getIntent().getExtras();
        if(datosOpciones!=null){
            Toast.makeText(this, "Sonido: "+datosOpciones.getBoolean("opcionSonido"), Toast.LENGTH_SHORT).show();
        }
    }

    public void jugar(View v){
        Intent jugar1=new Intent(this, MainActivity.class);
        // Si hay datos, es que hemos cambiado algo en opciones
        // Y por tanto se usan las opciones, si no, se usa lo predeterminado
        // en este caso que opcionSonido sea false
        if(datosOpciones!=null){
            jugar1.putExtra("opcionSonido",datosOpciones.getBoolean("opcionSonido"));
        } else{
            jugar1.putExtra("opcionSonido",false);
        }
        startActivity(jugar1);
    }

    public void jugardificil(View v){
        Intent jugardificil1=new Intent(this, Dificil.class);
        startActivity(jugardificil1);
    }

    public void opciones(View v){
        Intent opciones=new Intent(this, Opciones.class);
        if(datosOpciones!=null){
            opciones.putExtra("opcionSonido",datosOpciones.getBoolean("opcionSonido"));
        }
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
        creaAlerta("Tutorial","Existen dos modos de juego:\n" +
                "Modo fácil: tienes ayudas desde cofres, monedas y saltos.\n" +
                "Modo difícil: no tienes ayudas y por cada error pierdes una vida.\n\n" +
                "Trabajo realizado por: Cesar, Javier, Rubén, David y Oliver").show();
    }
}