package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


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

        try {
            while (!stop) {
                datagramSocket.receive(datagramPacket);
                messageUDP = new String(datagramPacket.getData());
                System.out.println(messageUDP);
                if (messageUDP.equalsIgnoreCase("- "+name+" disconected(UDP)"))
                    stop=true;
            }
            datagramSocket.close();

        }catch (IOException e){
            System.out.println("Error en Hilo UDP Cliente");
        }
    }
}
