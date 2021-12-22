package Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static ServerSocket ss;
    private static boolean salir;
    private static int cont;
    private static Users list;
    private static File fileHistorial;

    public static void main(String[] args) {
        try{
            cont=0;
            list = new Users();
            salir=false;
            ss=new ServerSocket(6662);
            System.out.println("Server running");
            fileHistorial = new File("src/main/java/Historial");

            while(!salir){
                Socket s=ss.accept();
                list.addUser(cont, s);
                ServerThread st=new ServerThread(s,list,cont,fileHistorial);
                st.start();
                cont++;
            }
        }catch(IOException io){
            System.out.println("Error in server");
            io.printStackTrace();
        }
    }

}
