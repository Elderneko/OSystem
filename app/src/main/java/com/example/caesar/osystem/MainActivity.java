package com.example.caesar.osystem;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView vidas, monedas, textoPista, cofres;
    private EditText respuesta;
    private Button botonPista, botonEnviar, botonSaltar, botonCofre;
    private int saltosAux, monedasAux, cofreIntentos, contadorPistas, contadorFallos, contadorAciertos, cofresUsados, tamanioLista;
    private ArrayList<String> lista = new ArrayList<String>();
    private String solucion, solucionEdit;
    private Bundle datosOpciones;
    private MediaPlayer sonido,sonido2;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Oculta barra de notificaciones
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // Oculta Titulo de la ventana

        // Cambiar esto en funcion de la dificultad
        saltosAux = 5; // Vidas del jugador
        monedasAux = 5; // Monedas del jugador
        cofreIntentos = 3; // Cofres del jugador

        //    Puntuaciones
        contadorFallos=0;
        contadorAciertos=0;
        cofresUsados=0;
        contadorPistas = 0; // No modificar

        //Opciones
        datosOpciones = this.getIntent().getExtras();

        //
        botonPista = (Button) findViewById(R.id.pista);
        botonEnviar = (Button) findViewById(R.id.enviar);
        botonCofre = (Button) findViewById(R.id.cc);
        imagen = (ImageView) findViewById(R.id.imagen);
        botonSaltar = (Button) findViewById(R.id.aa);
        vidas = (TextView) findViewById(R.id.vidas);
        monedas = (TextView) findViewById(R.id.monedas);
        cofres = (TextView) findViewById(R.id.cofres);
        respuesta = (EditText) findViewById(R.id.respuesta);
        textoPista = (TextView) findViewById(R.id.textoPista);

        // Nombres a adivinar, los archivos tienen el mismo nombre pero en minuscula
        lista.add("Marina");
        lista.add("Abierto");
        lista.add("Alas");
        lista.add("Altura");
        lista.add("Atletico");
        lista.add("Buceo");
        lista.add("Casino");
        lista.add("Cristiano");
        lista.add("Disparo");
        lista.add("Emociones");
        lista.add("Frio");
        lista.add("Hospital");
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

        lista.add("Hugo");
        lista.add("Emilio");
        lista.add("Fernando");
        lista.add("Jesus");
        // Mejor que no aparezcan
        // lista.add("Juan");
        // lista.add("Roberto");
        tamanioLista=lista.size();

        // Muestra vidas y monedas en pantalla
        vidas.setText(Integer.toString(saltosAux));
        monedas.setText(Integer.toString(monedasAux));
        cofres.setText(Integer.toString(cofreIntentos));

        // Sale siempre la primera posicion del array
        solucion = lista.get(0).toLowerCase();
        int resID = getResources().getIdentifier(solucion, "drawable", getPackageName());
        imagen.setImageResource(resID);
        imagen.setVisibility(View.VISIBLE);
        lista.remove(0);
        solucionEdit = cambiaString(solucion);
        textoPista.setText(solucionEdit);

        context=this;

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String respuestaAux = respuesta.getText().toString();
                sonidoPorImagen(respuestaAux); // TODO EasterEgg
                respuesta.setText("");
                if (respuestaAux.equals("")) { // Si no se escribe nada
                    // Sonido fallos
                    if(sonido!=null){
                        sonido.stop();
                    }
                    if(datosOpciones.getBoolean("opcionSonido")){
                        sonido= MediaPlayer.create(context,R.raw.error);
                        sonido.start();
                    }
                    creaAlerta("Error", "No me has dicho ninguna palabra").show();
                } else if (respuestaAux.equalsIgnoreCase(solucion)){
                    // Sonido acierto
                    if(datosOpciones.getBoolean("opcionSonido")){
                        sonido= MediaPlayer.create(context,R.raw.acierto);
                        sonido.start();
                    }
                    monedas(true); // Si la solucion es correcta se añade una moneda
                    solucion = randomImage();
                    contadorAciertos++;
                    if (solucion != null) {
                        solucionEdit = cambiaString(solucion);
                        textoPista.setText(solucionEdit);
                    }
                } else {
                    // Sonido fallos
                    if(sonido!=null){
                        sonido.stop();
                    }
                    if(datosOpciones.getBoolean("opcionSonido")){
                        sonido= MediaPlayer.create(context,R.raw.error);
                        sonido.start();
                    }
                    creaMensaje("Respuesta incorrecta");
                    contadorFallos++;
                }
            }
        });

        botonPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si en solucionEdit no se encuentra '_'
                if (solucionEdit.indexOf("_") == -1) {
                    creaAlerta("Pistas","Eres subnormal o que te pasa?").show();
                } else{
                    if (monedasAux < 1) {
                        monedas(false);
                    } else {
                        monedas(false);
                        solucionEdit = letraRandom(solucion, solucionEdit);
                        textoPista.setText(solucionEdit);
                    }
                }
            }
        });

        botonSaltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saltosAux < 1) {
                    creaAlerta("Saltos", "No te quedan saltos").show();
                } else {
                    solucion = randomImage();
                    if (solucion != null) {
                        solucionEdit = cambiaString(solucion);
                        textoPista.setText(solucionEdit);
                        saltos(false);
                    }
                }
            }
        });
    }

    /**
     * Metodo que muestra una imagen random de todas la que tenemos "almacenadas"
     * en el arraylist lista
     *
     * @return devuelve el valor de la imagen
     */
    public String randomImage() {
        if (lista.size() > (tamanioLista-3)) { // Lo que se resta es el numero de preguntas
            int random = (int) (Math.random() * lista.size());
            String nombre = lista.get(random).toLowerCase();
            int resID = getResources().getIdentifier(nombre, "drawable", getPackageName());
            imagen.setImageResource(resID);
            imagen.setVisibility(View.VISIBLE);
            lista.remove(random);
            //sonidoPorImagen(nombre); //TODO atencion
            return nombre;
        } else {
            // Sonido victoria
            if(sonido!=null){
                sonido.stop();
            }
            if(datosOpciones.getBoolean("opcionSonido")){
                sonido= MediaPlayer.create(context,R.raw.win);
                sonido.start();
            }
            // Puntuaciones
            creaAlerta("Juego finalizado", "Pistas usadas: " + contadorPistas).show();
     
            // Se desactivan todos para que no se pueda interactuar
            botonEnviar.setEnabled(false);
            botonPista.setEnabled(false);
            botonSaltar.setEnabled(false);
            botonCofre.setEnabled(false);
            respuesta.setEnabled(false);
            return null;
        }
    }

    /**
     * Metodo auxiliar para crear DialogAlert en pantalla
     *
     * @param titulo  titulo de la ventana
     * @param mensaje mensaje que queremos mostrar
     * @return un dialogo, hay que usar .show() para que se muestre en pantalla
     */
    public Dialog creaAlerta(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setTitle(titulo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No se
            }
        });
        return builder.create();
    }

    /**
     * Metodo auxiliar para crear Toast.makeText en pantalla
     *
     * @param mensaje
     */
    public void creaMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Actualiza contador de saltos
     *
     * @param decision false para restar un salto, true para sumar un salto
     */
    public void saltos(boolean decision) {
        if (saltosAux > 1) {
            if (decision == false) {
                saltosAux = saltosAux - 1;
                // Sonido salto
                if(sonido!=null){
                    sonido.stop();
                }
                if(datosOpciones.getBoolean("opcionSonido")){
                    sonido= MediaPlayer.create(context,R.raw.jump);
                    sonido.start();
                }
                creaMensaje("Te quedan " + saltosAux + " saltos");
            } else {
                saltosAux = saltosAux + 1;
            }
            vidas.setText(Integer.toString(saltosAux));
        } else {
            saltosAux = saltosAux - 1;
            vidas.setText(Integer.toString(saltosAux));
        }
    }

    /**
     * Actualiza contador de monedas
     *
     * @param decision, false para restar una moneda, true para sumar una moneda
     */
    public void monedas(boolean decision) {
        if (decision == false && monedasAux > 0) { // Si quiero restar y tengo saldo, lo hago
            monedasAux = monedasAux - 1;
            // Sonido moneda
            if(sonido!=null){
                sonido.stop();
            }
            if(datosOpciones.getBoolean("opcionSonido")){
                sonido= MediaPlayer.create(context,R.raw.coin);
                sonido.start();
            }
            monedas.setText(Integer.toString(monedasAux));
            creaMensaje("Has usado una moneda");
            contadorPistas++; // Has usado una pista
        } else if (decision == true) { // Si quiero sumar, sumo
            monedasAux = monedasAux + 1;
            monedas.setText(Integer.toString(monedasAux));
            creaMensaje("Has ganado una moneda");
        } else { // Si quiero restar y no tengo saldo, mensajito
            monedas.setText(Integer.toString(monedasAux));
            creaAlerta("Monedas", "Te has quedado sin monedas").show();
        }
    }

    /**
     * Cambia los caracteres de un string por "_ "
     *
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
     *
     * @param palabra Palabra solucion
     * @param palabraEdit cambiaString(palabra)
     * @return palabraEdit con una letra revelada
     */
    public String letraRandom(String palabra, String palabraEdit) {
        int random = (int) (Math.random() * palabra.length());
        if (palabraEdit.charAt(random * 2) == '_') { // Si la palabra es un '_'
            // Sustituye el '_' por una letra aleatoria
            palabraEdit = palabraEdit.substring(0, random * 2) + palabra.charAt(random) + palabraEdit.substring(random * 2 + 1, palabraEdit.length());
        } else { // Si no, significa que la letra esta repetida y vuelve a lanzar la funcion
            palabraEdit = letraRandom(palabra, palabraEdit);
        }
        return palabraEdit;
    }


    /**
     * Metodo que permite abrir un cofre que genera monedas de 1-4
     * @param v
     */
    public void cofre(View v) {
        if (cofreIntentos > 0) {
            cofreIntentos--;
            cofresUsados++;
            cofres.setText(Integer.toString(cofreIntentos));
            int nalt = (int) (Math.random() * 4 + 1); // Rango: 1-4
            monedas.setText(String.valueOf(monedasAux));
            monedasAux = monedasAux + nalt;
            monedas.setText(Integer.toString(monedasAux));
            // Creaccion del AlertDialog con gif
            LayoutInflater imagen_alert = LayoutInflater.from(MainActivity.this);
            //Se carga el layout del gif
            final View vista = imagen_alert.inflate(R.layout.gif, null);
            AlertDialog.Builder titulo = new AlertDialog.Builder(MainActivity.this);
            titulo.setMessage("Has ganado " + String.valueOf(nalt) + " monedas");
            titulo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            titulo.setTitle("Cofre");
            titulo.setView(vista);
            titulo.show();
            // Sonido cofre
            if(sonido!=null){
                sonido.stop();
            }
            if(datosOpciones.getBoolean("opcionSonido")){
                sonido= MediaPlayer.create(context,R.raw.cofre);
                sonido.start();
            }
        } else {
            // Creaccion del AlertDialog con la imagen del cofre
            LayoutInflater imagen_alert = LayoutInflater.from(MainActivity.this);
            final View vista = imagen_alert.inflate(R.layout.cofre, null);
            AlertDialog.Builder titulo = new AlertDialog.Builder(MainActivity.this);
            titulo.setMessage("No te quedan mas cofres");
            titulo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            titulo.setTitle("Cofre");
            titulo.setView(vista);
            titulo.show();
        }
    }

    /**
     * Metodo que activa los sonidos por imagen si la opcion sonido esta activada
     * @param s nombreImagen
     */
    public void sonidoPorImagen(String s){
        if(datosOpciones.getBoolean("opcionSonido")){
            // Este if hace que no se monte un sonido encima de otro
            // Si el objeto sonido existe, lo para
            if(sonido!=null){
                sonido.stop();
            }
            if(sonido2!=null){
                sonido2.stop();
            }
            if(s.equalsIgnoreCase("Mercoin")){ // Sonido especial de Hugo
                sonido2=MediaPlayer.create(context, R.raw.egg);
                sonido2.start();
            }
        }
    }
}