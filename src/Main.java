import sun.awt.OverrideNativeWindowHandle;

import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {

        HiloUDP hudp1;
        HiloUDP hudp2;
        HiloDibujo hd;

        /* Configuración para mandar argumentos desde consola */
        if(args.length == 1 && args[0].equals("h")){
            System.out.println("Para cambiar las propiedades del generador de señal introdúscalos en consola:");
            System.out.println("formato: java -jar nombre-programa.jar puerto1 puerto2");
            System.out.println("ejemplo: java -jar graficador-multicanal-udp.jar 8080 8081");

            System.exit(0);
        }

        int PUERTO1 = 8080;
        int PUERTO2 = 8081;

        try{
            if(args.length == 2){
                PUERTO1          = Integer.parseInt(args[0]);
                PUERTO2          = Integer.parseInt(args[1]);
            }
        }catch (NumberFormatException e){
            System.err.println("Error en los argumentos");
            System.err.println("Configuración por defecto: Puertos 8080 8081");
            PUERTO1 = 8080;
            PUERTO2 = 8081;
        }
        /* Fin de configuración para mandar argumentos desde consola */


        //Creamos una ventana en la que vamos a graficar los puntos
        VentanaPrincipal VP = new VentanaPrincipal();

        //Abrimos el servidor en el puerto predeterminado
        System.out.println("Servidor iniciado");
        System.out.println("Esperando en puerto: "+PUERTO1);
        System.out.println("Esperando en puerto: "+PUERTO2);

        hudp1 = new HiloUDP(PUERTO1, VP);
        hudp2 = new HiloUDP(PUERTO2, VP);
        hd = new HiloDibujo(VP);

        hudp1.start();
        VP.ponNombreDispositivo(""+hudp1.numHilo,hudp1.numHilo);
        hudp2.start();
        VP.ponNombreDispositivo(""+hudp2.numHilo,hudp2.numHilo);
        hd.start();
    }
}

//Usamos un hilo de ejecución cuya única función es llamar al método .grafica() para obligar a que se grafique
//cuantas veces sea posible por segundo.
//El método grafica contempla ya el control de FPS usando intervalos de tiempo.
class HiloDibujo extends Thread{

    VentanaPrincipal VP;
    HiloDibujo(VentanaPrincipal VP){
        this.VP = VP;
    }

    @Override
    public void run() {
        int contador = 0;
        long momento = 0;
        while(true) {
            contador += VP.grafica();

            if(System.currentTimeMillis() > momento + 1000){
                System.out.println(contador);
                momento = System.currentTimeMillis();
                contador = 0;
            }
        }
    }
}