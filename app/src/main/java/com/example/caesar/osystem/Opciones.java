package com.example.caesar.osystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;


public class Opciones extends AppCompatActivity{

    private boolean activado=false; // Valor por defecto
    private Bundle datosOpciones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.opciones);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana

        // Cojo los supuestos datos del Intent en el que estoy
        datosOpciones = this.getIntent().getExtras();
        // Si los datos existen...
        if(datosOpciones!=null){
            if(datosOpciones.getBoolean("opcionSonido")){
                RadioButton radioSi=(RadioButton) findViewById(R.id.radioSi);
                radioSi.setChecked(true);
            } else{
                RadioButton radioNo=(RadioButton) findViewById(R.id.radioNo);
                radioNo.setChecked(true);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioNo:
                if (checked){
                    activado=false;
                }
                break;
            case R.id.radioSi:
                if (checked){
                    activado=true;
                }
        }
    }

    /**
     * Se aplican los cambios y se vuelve al menu principal
     *
     * @param view
     */
    public void aplicarCambios(View view){
        Intent i=new Intent(this, Menu.class);
        i.putExtra("opcionSonido",activado);
        startActivity(i);
    }
}

