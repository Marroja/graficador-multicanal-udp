import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class VentanaPrincipal extends JFrame {

    Lienzo lienzo;
    VentanaPrincipal VP;
    final int ANCHO_ORIGINAL = 500;
    final int ALTO_ORIGINAL = 500;
    boolean bloqueado = false;

    long ultimoTiempoDibujo = 0;
    long momentoMilis = 0;


    VentanaPrincipal()
    {
        this.VP = this;
        this.setAlwaysOnTop( true );
        this.setSize(ANCHO_ORIGINAL, ALTO_ORIGINAL);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.setTitle("Osciloscopio");

        lienzo = new Lienzo(500, 500);
        this.add(lienzo);

        lienzo.pintaFondo();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                VP.repaint();
                lienzo.bi = new BufferedImage(VP.getWidth(), VP.getHeight(), BufferedImage.TYPE_INT_RGB);
                lienzo.fondo = new BufferedImage(VP.getWidth(), VP.getHeight(), BufferedImage.TYPE_INT_RGB);
                lienzo.revalidate();
                lienzo.repaint();
                lienzo.pintaFondo();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //Para pausar la graficación
                if(e.getKeyChar() == KeyEvent.VK_SPACE){
                    bloqueado = !bloqueado;
                    VP.repaint();
                }
            }

            //Controles para el movimiento X y Y de la gráfica
            @Override
            public void keyPressed(KeyEvent e) {
                //Cambio escala eje Y
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    System.out.println("Cambio proporción Y");
                    if(Math.abs(lienzo.pixelesPorVoltY) >= 10){
                        lienzo.pixelesPorVoltY += 1;
                        lienzo.pixelesPorVoltX += 1;
                    } else if(Math.abs(lienzo.pixelesPorVoltY) >= 1){
                        lienzo.pixelesPorVoltY += 0.1;
                        lienzo.pixelesPorVoltX += 0.1;
                    }else if (Math.abs(lienzo.pixelesPorVoltY) >= 0.1){
                        lienzo.pixelesPorVoltY += 0.01;
                        lienzo.pixelesPorVoltX += 0.01;
                    }else if(Math.abs(lienzo.pixelesPorVoltY) >= 0.01){
                        lienzo.pixelesPorVoltY += 0.001;
                        lienzo.pixelesPorVoltX += 0.001;
                    }else{
                        lienzo.pixelesPorVoltY += 0.0001;
                        lienzo.pixelesPorVoltX += 0.0001;
                    }
                    lienzo.pintaFondo();
                }
                if(e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("Cambio proporción Y");
                    if(Math.abs(lienzo.pixelesPorVoltY) >= 10){
                        lienzo.pixelesPorVoltY -= 1;
                        lienzo.pixelesPorVoltX -= 1;
                    }else if(Math.abs(lienzo.pixelesPorVoltY) >= 1){
                        lienzo.pixelesPorVoltY -= 0.1;
                        lienzo.pixelesPorVoltX -= 0.1;
                    }else if (Math.abs(lienzo.pixelesPorVoltY) >= 0.1){
                        lienzo.pixelesPorVoltY -= 0.01;
                        lienzo.pixelesPorVoltX -= 0.01;
                    }else if(Math.abs(lienzo.pixelesPorVoltY) >= 0.01){
                        lienzo.pixelesPorVoltY -= 0.001;
                        lienzo.pixelesPorVoltX -= 0.001;
                    }else{
                        lienzo.pixelesPorVoltY -= 0.0001;
                        lienzo.pixelesPorVoltX -= 0.0001;
                    }
                    lienzo.pintaFondo();
                }

                //Cambio escala eje X
                if(e.getKeyCode() == KeyEvent.VK_W){
                    System.out.println("Cambio proporción X");
                    if(Math.abs(lienzo.nanoSegundosPorPixelX) >= 1000_000){
                        lienzo.nanoSegundosPorPixelX += 100_000;
                    }else if (Math.abs(lienzo.nanoSegundosPorPixelX) >= 100_000){
                        lienzo.nanoSegundosPorPixelX += 10_000;
                    }else if(Math.abs(lienzo.nanoSegundosPorPixelX) >= 10_000){
                        lienzo.nanoSegundosPorPixelX += 1000;
                    }else{
                        lienzo.nanoSegundosPorPixelX += 100;
                    }
                    lienzo.pintaFondo();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    System.out.println("Cambio proporción X");
                    if(Math.abs(lienzo.nanoSegundosPorPixelX) >= 1000_000){
                        lienzo.nanoSegundosPorPixelX -= 100_000;
                    }else if (Math.abs(lienzo.nanoSegundosPorPixelX) >= 100_000){
                        lienzo.nanoSegundosPorPixelX -= 10_000;
                    }else if(Math.abs(lienzo.nanoSegundosPorPixelX) >= 10_000){
                        lienzo.nanoSegundosPorPixelX -= 1000;
                    }else{
                        lienzo.nanoSegundosPorPixelX -= 100;
                    }
                    lienzo.pintaFondo();
                }

                //Desfase horizontal del tiempo
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    lienzo.desfaseX += 1;
                    System.out.println("Cambio eje X:" + lienzo.desfaseX);
                }
                if(e.getKeyCode() == KeyEvent.VK_X){
                    lienzo.desfaseX -= 1;
                    System.out.println("Cambio eje X:" + lienzo.desfaseX);
                }

                //Desfase vertical del Canal 1
                if(e.getKeyCode() == KeyEvent.VK_U){
                    lienzo.desfaseY1 += 1;
                    System.out.println("Cambio eje Y1:" + lienzo.desfaseY1);
                }
                if(e.getKeyCode() == KeyEvent.VK_J){
                    lienzo.desfaseY1 -= 1;
                    System.out.println("Cambio eje Y1:" + lienzo.desfaseY1);
                }

                //Desfase vertical del Canal 2
                if(e.getKeyCode() == KeyEvent.VK_I){
                    lienzo.desfaseY2 += 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_K){
                    lienzo.desfaseY2 -= 1;
                }

                //Interruptor de cambio para modo Lissajous
                if(e.getKeyCode() == KeyEvent.VK_L){
                    lienzo.modoLissajous = !lienzo.modoLissajous;
                    lienzo.pintaFondo();
                }

                //Interruptor de cambio para mostrar puntos o puntos y líneas
                if(e.getKeyCode() == KeyEvent.VK_P){
                    lienzo.soloPuntos = !lienzo.soloPuntos;
                    lienzo.pintaFondo();
                }

                if(e.getKeyCode() == KeyEvent.VK_F1){
                    new VentanaControles((VentanaPrincipal) e.getSource());
                }

                lienzo.pintaGrafica(ultimoTiempoDibujo);
                lienzo.paintComponent(lienzo.getGraphics());
            }


        });

        this.setSize(ANCHO_ORIGINAL, ALTO_ORIGINAL);

        this.revalidate();

        this.setSize(ANCHO_ORIGINAL + 1, ALTO_ORIGINAL + 1);

    }

    public void agregaValor(String nombre, long momento, double voltaje){

        // Desde la clase de BuzonUDP se guardan todos los valores recibido en el buzón a estas ArrayList.
        // Existen otras opciones más seguras como tener dos ArrayList, una donde la escritura es concurrente y
        // otra a donde se copia la lista completa en vez de modificarla simultáneamente. Realizar esos cambios
        // queda para una revisión posterior. QPH

        if(bloqueado)
            return;
        //Con 100_000 lecturas máximas tenemos un buffer de máximas 100_000 lecturas en cada instante
        //Esto significa que si mandamos más de 100_000 lecturas por segundo (100kHz) va a graficarse
        //Incompleta la gráfica.
        if(lienzo.nombreDispositivo1 != null && lienzo.nombreDispositivo1.equals(nombre)){
            lienzo.listaMomentos1.add(momento);
            lienzo.listaVoltajes1.add(voltaje);
            try{
                while (lienzo.listaVoltajes1.size() > 100_000){
                    lienzo.listaVoltajes1.remove(0);
                    lienzo.listaMomentos1.remove(0);
                }
            }catch (IndexOutOfBoundsException e){

            }
        }

        if(lienzo.nombreDispositivo2 != null && lienzo.nombreDispositivo2.equals(nombre)){
            lienzo.listaMomentos2.add(momento);
            lienzo.listaVoltajes2.add(voltaje);
            try{
                while(lienzo.listaVoltajes2.size() > 100_000){
                    lienzo.listaVoltajes2.remove(0);
                    lienzo.listaMomentos2.remove(0);
                }
            }catch (IndexOutOfBoundsException e){

            }
        }
    }

    public int grafica(){
        if(!bloqueado) {
            final int INTERVALO = 20;

            //Cada intervalo de 20 milisegundos escribimos una nueva imagen en pantalla
            //Esto se traduce a 50fps
            if (System.currentTimeMillis() > momentoMilis + INTERVALO) {
                momentoMilis = System.currentTimeMillis();
                ultimoTiempoDibujo = System.nanoTime();
                lienzo.pintaGrafica(System.nanoTime());
                lienzo.paintComponent(lienzo.getGraphics());
                return 1;
            }
            return 0;
        }
        else
            return 0;

    }

    public void ponNombreDispositivo(String nombre, int i){
        if(i == 0){
            lienzo.nombreDispositivo1 = nombre;
        }
        else{
            lienzo.nombreDispositivo2 = nombre;
        }
    }
}


/*
    La clase de Lienzo es un JPanel en el que se da el Override al método de PaintComponent.
    En el Lienzo existen dos BufferedImage, uno es el fondo (el cual solo se tiene que re-calcular cuando se cambia
    la escala del voltaje o del tiempo, también cuando se cambia de modo normal-lissajous. La otra imagen es la llamada
    "bi". Esa imagen se tiene que trazar 50 veces por segundo para mantener la ilusión de movimiento fluida.

     El valor se puede modificar cambiando el valor de INTERVALO.

     El lienzo no llama a su dibujo, se llama desde la VentanaPrincipal.

     El cálculo para las gráficas se realiza siempre a partir de una transformación a partir de las dimensiones originales
     (500, 500).
 */

class Lienzo extends JPanel {

    ArrayList<Long> listaMomentos1;
    ArrayList<Double> listaVoltajes1;
    String nombreDispositivo1 = null;
    ArrayList<Long> listaMomentos2;
    ArrayList<Double> listaVoltajes2;
    String nombreDispositivo2 = null;
    BufferedImage bi;
    BufferedImage fondo;
    final double ANCHO_ORIGINAL = 500.0;
    final double ALTO_ORIGINAL = 500.0;
    double nanoSegundosPorPixelX = (double) 1000_000.0;
    double pixelesPorVoltY = 1.0;
    double pixelesPorVoltX = 1.0;
    int desfaseX = 0;
    int desfaseY1 = 0;
    int desfaseY2 = 0;

    boolean soloPuntos = false;

    int contadorX = 0;
    boolean modoLissajous = false;
    Lienzo(int ancho, int alto) {
        this.setSize(ancho, alto);
        bi = new BufferedImage((int) ANCHO_ORIGINAL, (int) ALTO_ORIGINAL, BufferedImage.TYPE_INT_RGB);
        fondo = new BufferedImage((int) ANCHO_ORIGINAL, (int) ALTO_ORIGINAL, BufferedImage.TYPE_INT_RGB);
        listaMomentos1 = new ArrayList<>();
        listaVoltajes1 = new ArrayList<>();
        listaMomentos2 = new ArrayList<>();
        listaVoltajes2 = new ArrayList<>();
        //System.out.println("Dimensiones lienzo:" +this.getWidth() + ":"+this.getHeight());
    }

    public void pintaFondo(){

        Graphics2D g = (Graphics2D) fondo.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //Est es el momento en tiempo que coincide con el borde derecho de la graficación
        //El punto más nuevo debería estar muy cerca de este tiempo

        //Los ejes menores
        g.setColor(Color.gray);
        g.setFont(new Font("Courier New", Font.PLAIN, this.getWidth() / 60));
        //Graficamos los ejes "menores" para crear la gradilla
        for(int i = -1; i < 11; i++) {// -1 -> +11 por los artefactos que luego ocurren durante la graficación

            //Eje Y
            double numeroVoltaje = (ALTO_ORIGINAL / pixelesPorVoltY) / 2.0 - ALTO_ORIGINAL / pixelesPorVoltY / 10.0 * i;
            numeroVoltaje = (double) (int) (100 * (numeroVoltaje)) / 100; //redondeo a dos dígitos
            String textoVoltaje = String.valueOf(numeroVoltaje);
            g.drawString(textoVoltaje, this.getWidth() / 2, this.getHeight() / 10 * i + this.getWidth() / 50);

            //Eje X
            if (!modoLissajous) {
                int potencia = 0;
                int momentoPixelNanos = (int) (-(double) ANCHO_ORIGINAL / 10.0 * nanoSegundosPorPixelX * (i - 5));

                while (Math.abs(momentoPixelNanos) >= 1000) {
                    momentoPixelNanos /= 1000;
                    potencia += 3;
                }

                //Para poner la notación científica en los valores de tiempo
                String sufijo = "";
                if (potencia == 0) {
                    sufijo = "n";
                }
                if (potencia == 3) {
                    sufijo = "μ";
                }
                if (potencia == 6) {
                    sufijo = "m";
                }
                if (potencia == 9) {
                    sufijo = "s";
                }
                String momentoLecturaNanos = String.valueOf(momentoPixelNanos + sufijo);
                g.drawString(momentoLecturaNanos, this.getWidth() - this.getWidth() / 10 * (i), this.getHeight() / 2 - this.getHeight() / 100);
            }

            //Si el modo es Lissajous hay que poner la gradilla con voltaje, no con tiempo
            if(modoLissajous){
                numeroVoltaje = (-((ANCHO_ORIGINAL / pixelesPorVoltX) / 2 - ANCHO_ORIGINAL / pixelesPorVoltX / 10 * i));
                numeroVoltaje = (double) (int) (100 * (numeroVoltaje)) /100;
                textoVoltaje = String.valueOf(numeroVoltaje);
                g.drawString(textoVoltaje, this.getWidth() / 10 * i, this.getHeight() / 2 );
            }

            if(i == 5){
                continue;
            }
            g.drawLine(0, this.getHeight()/10 * i, this.getWidth(), this.getHeight()/10 * i);
            g.drawLine(this.getWidth() / 10 * i, 0, this.getWidth()/10 * i, this.getHeight());
        }

        //Graficamos los dos ejes del centro en blanco
        g.setColor(Color.white);
        g.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());

        g.dispose();
    }

    public int pintaGrafica(long momentoNanos) {

        Graphics2D g = (Graphics2D) bi.getGraphics();

        //Dibujamos el fondo diréctramente en cada cuadro
        //Pero no la tenemos que recalcular
        g.drawImage(fondo, 0, 0, null);

        if(!modoLissajous) {

            //Graficar los puntos de lectura del canal 1
            g.setColor(Color.YELLOW);

            //Comenzamos en el extremo derecho a la mitad de la pantalla
            int x_anterior = this.getWidth();
            int y_anterior = this.getHeight() / 2;

            //
            for (int i = 0; i < listaMomentos1.size(); i += 1) {
                int x = this.getWidth();
                int y = this.getHeight() / 2;
                try {
                    //Comenzamos a dibujar desde la derecha
                    //Calculamos el valor de X con el momentoNanos, le restamos el valor del tiempo que viene en el
                    //paquete de lectura.
                    //Luego lo multiplicamos por el facto de escala con el que se calibró al inicio.
                    //Se resta porque partimos desde el extremo derecho
                    x -= (momentoNanos - (listaMomentos1.get(listaMomentos1.size() - i))) //momentoNanos - tPaquete
                            / (nanoSegundosPorPixelX / this.getWidth() * ANCHO_ORIGINAL); //Factor de escala
                    x -= this.getWidth() / 100 * desfaseX;                                //Desfase por manipulación
                    //Hacemos lo mismo con Y. Partimos del punto central, le sumamos el voltaje multiplicado
                    //por el factor de calibración en las dimensiones iniciales.
                    y -= listaVoltajes1.get(listaVoltajes1.size() - i)           //Voltaje recibido por UDP
                            * pixelesPorVoltY / ALTO_ORIGINAL * this.getHeight() //Factor de escala
                            - this.getHeight() / 100 * desfaseY1;                //Manipulación eje Y
                    if (x < 0) {
                        //Si el valor graficado ya excede el lado izquierdo de la venatana, no es necesario graficar
                        //Así ahorramos el dibujo de los puntos que no aparecen en pantalla
                    } else {
                        if (x_anterior != this.getWidth()) {
                            //Si el valor de X es diferente al primero, se conecta con una línea.
                            if(soloPuntos){

                            }else{
                                g.drawLine(x_anterior, y_anterior, x, y);
                            }
                        }
                        g.fillRect(x, y, 2, 2);
                        //Guardamos el punto para la siguiente iteración
                        x_anterior = x;
                        y_anterior = y;
                    }
                } catch (NullPointerException | IndexOutOfBoundsException er) {
                    //Como la modificación de los ArrayList es concurrente por el hilo de ejecución del buzónUDP
                    //A veces el arreglo habrá crecido uno o dos elementos más de los que aparecen en el .size()
                    //También a veces se cambia su dirección y por eso ocurre el NullPointerException
                }
            }

            //Comenzamos en el extremo derecho a la mitad de la pantalla
            x_anterior = this.getWidth();
            y_anterior = this.getHeight() / 2;

            g.setColor(Color.GREEN);
            for (int i = 0; i < listaMomentos2.size(); i += 1) {
                int x = this.getWidth();
                int y = this.getHeight() / 2;
                try {
                    //Comenzamos a dibujar desde la derecha
                    //Calculamos el valor de X con el momentoNanos, le restamos el valor del tiempo que viene en el
                    //paquete de lectura.
                    //Luego lo multiplicamos por el facto de escala con el que se calibró al inicio.
                    //Se resta porque partimos desde el extremo derecho
                    x -= (momentoNanos - (listaMomentos2.get(listaMomentos2.size() - i))) //momentoNanos - tPaquete
                            / (nanoSegundosPorPixelX / this.getWidth() * ANCHO_ORIGINAL); //Factor de escala
                    x -= this.getWidth() / 100 * desfaseX;                                //Desfase por manipulación
                    //Hacemos lo mismo con Y. Partimos del punto central, le sumamos el voltaje multiplicado
                    //por el factor de calibración en las dimensiones iniciales.
                    y -= listaVoltajes2.get(listaVoltajes2.size() - i)           //Voltaje recibido por UDP
                            * pixelesPorVoltY / ALTO_ORIGINAL * this.getHeight() //Factor de escala
                            - this.getHeight() / 100 * desfaseY2;                //Manipulación eje Y
                    if (x < 0) {
                        //Si el valor graficado ya excede el lado izquierdo de la venatana, no es necesario graficar
                        //Así ahorramos el dibujo de los puntos que no aparecen en pantalla
                    } else {
                        if (x_anterior != this.getWidth()) {
                            //Si el valor de X es diferente al primero, se conecta con una línea.
                            if(soloPuntos){

                            }else{
                                g.drawLine(x_anterior, y_anterior, x, y);
                            }
                        }
                        g.fillRect(x, y, 2, 2);
                        //Guardamos el punto para la siguiente iteración
                        x_anterior = x;
                        y_anterior = y;
                    }
                } catch (NullPointerException | IndexOutOfBoundsException er) {
                    //Como la modificación de los ArrayList es concurrente por el hilo de ejecución del buzónUDP
                    //A veces el arreglo habrá crecido uno o dos elementos más de los que aparecen en el .size()
                    //También a veces se cambia su dirección y por eso ocurre el NullPointerException
                }
            }
        }

        if(modoLissajous){

            int tamMuestra = 1500;
            int[] pixelesX = new int[tamMuestra];
            int[] pixelesY = new int[tamMuestra];

            int xAnterior = -10;
            int yAnterior = -10;
            Arrays.fill(pixelesY, -10);
            Arrays.fill(pixelesX, -10);

            g.setColor(Color.GREEN);

            //Se calcula el tiempo de graficación como se calcula la X en el modo de graficación normal.
            //Ese número se redondea a un entero y ese se convierte en un índice de pixelesX o pixelesY. Se guardan
            //así los dos arreglos. A veces hay índices que no coinciden por lo que hay espacios que no se grafican.
            //Sin embargo, es suficientemente bueno este método, al menos es funcional.

            try{
                for(int i = 1; i < listaMomentos1.size(); i++){
                    int posTiempo = (int) ((momentoNanos - (listaMomentos1.get(listaMomentos1.size() - i)))
                            / (nanoSegundosPorPixelX / this.getWidth() * ANCHO_ORIGINAL));

                    if(posTiempo < tamMuestra && posTiempo >= 0 && pixelesX[posTiempo] == -10){
                        pixelesX[posTiempo] = (int) (listaVoltajes1.get(listaVoltajes1.size() - i)
                                                        * pixelesPorVoltX / ANCHO_ORIGINAL * this.getWidth()
                                                        + this.getWidth() / 2);
                    }
                }

                for(int i = 1; i < listaMomentos2.size(); i++){
                    int posTiempo = (int) ((momentoNanos - (listaMomentos2.get(listaMomentos2.size() - i)))
                            / (nanoSegundosPorPixelX / this.getWidth() * ANCHO_ORIGINAL));


                    if(posTiempo < tamMuestra && posTiempo >= 0 && pixelesY[posTiempo] == -10){
                        pixelesY[posTiempo] = -(int) (listaVoltajes2.get(listaVoltajes2.size() - i)
                                                        * pixelesPorVoltY / ALTO_ORIGINAL * this.getHeight()
                                                        - this.getHeight() / 2);
                    }
                }

                for(int i = 0; i < tamMuestra; i++){
                    if(soloPuntos){

                    }
                    else{
                        if(xAnterior >= 0 && yAnterior >= 0 && pixelesX[i] >= 0 && pixelesY[i] >= 0){
                            g.drawLine(xAnterior, yAnterior, pixelesX[i], pixelesY[i]);
                        }
                    }
                    g.fillRect(pixelesX[i], pixelesY[i], 2,2);
                    xAnterior = pixelesX[i];
                    yAnterior = pixelesY[i];
                }


            }catch (IndexOutOfBoundsException | NullPointerException e){

            }
        }

        g.dispose();
        return 1;
    }

    public void paintComponent(Graphics g) {

        g.drawImage(bi, 0, 0, null);
        g.dispose();
    }


}
