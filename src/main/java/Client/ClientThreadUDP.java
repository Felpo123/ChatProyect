package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;


public class ClientThreadUDP  extends Thread{

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private String messageUDP, name;
    private boolean stop;

    public ClientThreadUDP(DatagramSocket datagramSocket, DatagramPacket datagramPacket, String name){
        this.datagramPacket=datagramPacket;
        this.datagramSocket=datagramSocket;
        this.name=name;
    }

    public void run() {
            while (!stop) {
                messageUDP = leerPaquete();
                System.out.println(messageUDP);
                if (messageUDP.equalsIgnoreCase("- " + name + " disconected(UDP)"))
                    stop=true;
            }
            datagramSocket.close();
            return;
    }

    public String leerPaquete(){
        try {
            datagramSocket.receive(datagramPacket);
            return new String(datagramPacket.getData(),java.nio.charset.StandardCharsets.UTF_8);
        }catch (IOException e){
            System.out.println("Error en Hilo UDP Cliente");
            return null;
        }
    }
}



