package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Client {
    private static DataOutputStream ou;
    private static BufferedReader in;
    private static Socket s;
    private static String message,name;
    private static boolean end;
    private static ClientThread clTCP;
    private static ClientThreadUDP clUDP;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket datagramPacket;
    private static byte[] buffer;

    public static void main(String[] args){
        inicioConexionTCP("localhost",6660);
        UDP(6001);
        try{
            System.out.print("Ingresa tu Nombre: ");
            name=in.readLine();
            clTCP=new ClientThread(s,name);
            clUDP = new ClientThreadUDP(datagramSocket,datagramPacket,name);
            clUDP.start();
            clTCP.start();
            ou.writeUTF(name);
        }catch(IOException io1){
            System.out.println("Error input text");
        }
        while(!end){
            try{
                message=in.readLine();
                ou.writeUTF(message);
                if(message.equalsIgnoreCase("exit")){
                    end=true;
                }
            }catch(IOException io2){
                System.out.println("Error in loop");
            }
        }
        return;
    }

    public static boolean inicioConexionTCP(String host, int port){
        try{
            s = new Socket(host,port);
            ou=new DataOutputStream(s.getOutputStream());
            in=new BufferedReader(new InputStreamReader(System.in));
            end=false;
            return true;
        }catch(IOException io){
            System.out.println("Error en socket TCP");
            return false;
        }
    }

    public static boolean UDP(int port){
        try {
            datagramSocket = new DatagramSocket(port);
            buffer = new byte[50];
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            return true;
        }catch (IOException e){
            System.out.println("Error en socket UDP");
            return false;
        }catch (IllegalArgumentException e2){
            System.out.println("Error en socket UDP");
            return false;
        }
    }
}