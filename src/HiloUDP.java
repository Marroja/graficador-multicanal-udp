import java.nio.ByteBuffer;

/*
    En este hilo de ejecución se revisa constantemente el puerto de UDP. Por defecto se abren los puertos 8080 y 8081
    Eso se puede cambiar desde los argumentos de arranque.

    Los valores leídos en el puerto de UDP son siempre paquetes de 16 bytes.
    8 son de un double (el valor de voltaje)
    8 son de un long (el valor del tiempo en nanosegundos cuando se generó ese valor)

    Esos valores llegan a este puerto, se cambian de bytes -> double/long y luego se guardan en los ArrayList del
    Lienzo.

 */

public class HiloUDP extends Thread {

    BuzonUDP budp;
    int contadorRecibidos = 0;
    long momentoMilis = 0;
    byte[] paqueteRecibido;
    static int contadorHilo = 0;
    int numHilo;
    VentanaPrincipal vp = null;
    HiloUDP(int puerto, VentanaPrincipal vp){

        this(puerto);
        this.vp = vp;
    }
    HiloUDP(int puerto) {
        numHilo = contadorHilo;
        System.out.println("Nombre: "+numHilo);
        contadorHilo ++;
        budp = new BuzonUDP(puerto);
    }

    @Override
    public void run() {
        while (true) {
            //Se reciben 16 bytes en la lectura del puerto UDP
            paqueteRecibido = budp.recibePaquete();
            byte[] bytesLong = new byte[8];
            byte[] bytesDouble = new byte[8];

            System.arraycopy(paqueteRecibido, 0, bytesLong, 0, 8);
            System.arraycopy(paqueteRecibido, 8, bytesDouble, 0, 8);

            //Se realiza la conversión de los primeros 8 bytes en el Long (momentoNanos)
            //Y la conversión de los otros 8 bytes en Double (el voltaje)

            if(vp != null){
                //Aquí hay una decisión de diseño, el valor de tiempo que usamos podría ser el valor del tiempo
                //Cuando se generó el valor de voltaje pero también podría ser el valor de en el que llegó a
                //este puerto UDP.
                //La realidad es que se obtienen mejores resultados cuando se usa el tiempo de generación de
                //la señal. El tiempo en que llega por medio de UDP genera muchas muchas irregularidades
                vp.agregaValor(String.valueOf(numHilo), bytesToLong(bytesLong), bytesToDouble(bytesDouble));
                //vp.agregaValor(String.valueOf(numHilo), System.nanoTime(), bytesToDouble(bytesDouble));
            }
            else{
                System.out.println(bytesToLong(bytesLong));
                System.out.println(bytesToDouble(bytesDouble));
            }

            contadorRecibidos++;

            //Para imprimir el número de valores recibidos por segundo en este puerto.
            if (System.currentTimeMillis() > momentoMilis + 1000) {
                momentoMilis = System.currentTimeMillis();
                System.out.println("Recibidos por segundo:" + contadorRecibidos);
                contadorRecibidos = 0;
            }
        }
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static double bytesToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
