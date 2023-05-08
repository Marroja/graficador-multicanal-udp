import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BuzonUDP {

    //Este clase existe únicamente para simplificar la creación del socket del puerto.

    byte[] buffer = new byte[16];

    DatagramSocket socketUDP;
    DatagramPacket paquete;

    BuzonUDP(int puerto){
        try {
            socketUDP = new DatagramSocket(puerto);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] recibePaquete(){

        try {
            paquete = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(paquete);
        } catch (IOException e) {
            System.out.println("Fallo al recibir paquete");
            return new byte[16];
        }

        return paquete.getData();
    }
}
