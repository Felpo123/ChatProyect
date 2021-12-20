package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientThread extends Thread{
    private Socket s;
    private DataInputStream in;
    private  boolean stop;
    private String messageTCP,name;


    public ClientThread(Socket s,String name){
        this.s = s;
        this.name = name;
    }

    public void run(){
        try{
            stop=false;
            in=new DataInputStream(s.getInputStream());
            while(!stop) {
                messageTCP = in.readUTF();
                System.out.println(messageTCP);
                if (messageTCP.equalsIgnoreCase("Adios")){
                    stop = true;
                }
            }
            s.close();
        }catch(IOException io){
            System.out.println("Error en Hilo TCP Cliente");
            io.printStackTrace();
            stop=true;
        }
    }

}

