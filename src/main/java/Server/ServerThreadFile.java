package Server;

import java.io.*;
import java.net.Socket;

public class ServerThreadFile  extends Thread {
    private static Socket s;
    private static DataInputStream in;
    private static DataOutputStream ou;
    private static File historial;
    private static FileReader fr;
    private static BufferedReader br;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public ServerThreadFile(Socket s,DataInputStream in, DataOutputStream ou){
        this.s = s;
        this.in = in;
        this.ou = ou;
        this.historial = new File("src/main/java/Historial");
    }

    @Override
    public void run() {
        try {
            enviarMensaje(ANSI_RED +leerHistorial(historial)+ ANSI_RESET);
            String msg = in.readUTF();
            String path = historial.getPath()+"/"+msg;
            leerArchivo(path);
        }catch (IOException e){
            System.out.println("Error en hilo historial");
            e.printStackTrace();
        }
    }

    public static String leerHistorial(File file){
        String[] contenido = file.list();
        String archivos = "";
        for (int i=0; i<contenido.length; i++)
            archivos = archivos +"\n"+contenido[i];

        return archivos;
    }

     public static boolean leerArchivo(String path) {
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine())!= null)
                enviarMensaje(ANSI_RED + linea + ANSI_RESET);
            return true;
        }catch (FileNotFoundException f){
            enviarMensaje("Archivo no encontrado");
            return false;
        }catch (IOException f2){
            f2.printStackTrace();
            return false;
        }
    }

    public static boolean enviarMensaje(String msg){
        try{
            ou.writeUTF(msg);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}

