package com.example.caesar.osystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.EditText;
import android.widget.Toast;

public class Dificil extends AppCompatActivity {

    private ImageView imagen;
    private TextView vidas,textoPista;
    private EditText respuesta;
    private Button botonEnviar;
    private int vidasAux;
    private ArrayList<String> lista=new ArrayList<String>();
    private String solucion,solucionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.dificil);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana

        // Cambiar esto en funcion de la dificultad
        vidasAux=5; // Vidas del jugador

        botonEnviar=(Button) findViewById(R.id.enviar);
        imagen=(ImageView) findViewById(R.id.imagen);
        vidas=(TextView) findViewById(R.id.vidas);

        respuesta=(EditText) findViewById(R.id.respuesta);
        textoPista=(TextView) findViewById(R.id.textoPista);

        // Nombres a adivinar, los archivos tienen el mismo nombre pero en minuscula
        lista.add("Abierto");
        lista.add("Alas");
        lista.add("Altura");
        lista.add("Atletico");
        lista.add("Buceo");
        lista.add("Casino");
        lista.add("Cristiano");
        lista.add("Disparo");
        lista.add("Emilio");
        lista.add("Emociones");
        lista.add("Frio");
        lista.add("Hospital");
        lista.add("Hugo");
        lista.add("Marina");
        lista.add("Messi");
        lista.add("Oxidado");
        lista.add("Palmera");
        lista.add("Pareja");
        lista.add("Porra");
        lista.add("Raiz");
        lista.add("Ramos");
        lista.add("Seda");
        lista.add("Teatro");
        lista.add("Uniforme");
        lista.add("Velocidad");
        lista.add("Revilla");
        lista.add("Totti");

        // Muestra vidas y monedas en pantalla
        vidas.setText(Integer.toString(vidasAux));

        solucion=randomImage();
        solucionEdit=cambiaString(solucion);
        textoPista.setText(solucionEdit);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String respuestaAux=respuesta.getText().toString();
                respuesta.setText("");
                if(respuestaAux.equals("")){ // Si no se escribe nada
                    creaMensaje("No hay valor");
                } else if(respuestaAux.equalsIgnoreCase(solucion)){
                    solucion=randomImage();
                    if(solucion!=null){
                        solucionEdit=cambiaString(solucion);
                        textoPista.setText(solucionEdit);
                    }
                } else{
                    vidas(false); //  Si la solucion no es correccta, rip 1 vida
                }
            }
        });
    }

    /**
     * Metodo que muestra una imagen random de todas la que tenemos "almacenadas"
     * en el arraylist lista
     * @return devuelve el valor de la imagen
     */
    public String randomImage(){
        if(lista.size()>16){ // TODO Controlar el numero de preguntas
            int random=(int)(Math.random()*lista.size());
            String nombre=lista.get(random).toLowerCase();
            int resID= getResources().getIdentifier(nombre,"drawable",getPackageName());
            imagen.setImageResource(resID);
            imagen.setVisibility(View.VISIBLE);
            lista.remove(random);
            return nombre;
        } else{
            creaAlerta("Juego finalizado","Tienes "+ vidasAux).show();
            botonEnviar.setEnabled(false);

            respuesta.setEnabled(false); // Si el numero de preguntas se supera se acaba el juego
            return null;
        }
    }

    /**
     * Metodo auxiliar para crear DialogAlert en pantalla
     * @param titulo titulo de la ventana
     * @param mensaje mensaje que queremos mostrar
     * @return un dialogo, hay que usar .show() para que se muestre en pantalla
     */
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

    public void creaMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Actualiza contador de vidas
     * @param decision false para restar una moneda, true para sumar una moneda
     */
    public void vidas(boolean decision){
        if(vidasAux>1) {
            if(decision==false){
                vidasAux = vidasAux - 1;
                creaMensaje("Has perdido una vida");
            }else{
                vidasAux = vidasAux + 1;
            }
            vidas.setText(Integer.toString(vidasAux));
        } else{
            vidasAux = vidasAux - 1;
            vidas.setText(Integer.toString(vidasAux));
            botonEnviar.setEnabled(false);

            respuesta.setEnabled(false);
            creaAlerta("Fin de la partida","Te has quedado sin vidas crack").show();
        }
    }

    /**
     * Cambia los caracteres de un string por "_ "
     * @param palabra Palabra a modificar
     * @return Palabra modificada
     */
    public String cambiaString(String palabra) {
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) != ' ') { // Si el caracter no es un espacio entonces...
                palabra = palabra.replace(palabra.charAt(i), '_'); // Reemplaza cada caracter por '_'
            }
        }
        palabra = palabra.replaceAll("_", "_ "); // Reemplaza cada _ por "_ "
        palabra = palabra.substring(0, palabra.length() - 1); // Borra el ultimo espacio
        return palabra;
    }

    /**
     * Revela un caracter aleatorio de la palabra que hay que adivinar
     * @param palabra Palabra solucion
     * @param palabraEdit cambiaString(palabra)
     * @return palabraEdit con una letra revelada
     */
    public String letraRandom(String palabra, String palabraEdit) {
        int random=(int)(Math.random()*palabra.length());
        if(palabraEdit.charAt(random*2)=='_') { // Si la palabra es un '_'
            // Sustituye el '_' por una letra aleatoria
            palabraEdit= palabraEdit.substring(0,random*2)+palabra.charAt(random)+palabraEdit.substring(random*2+1,palabraEdit.length());
        } else { // Si no, significa que la letra esta repetida y vuelve a lanzar la funcion
            palabraEdit=letraRandom(palabra, palabraEdit);
        }
        return palabraEdit;
    }
}
