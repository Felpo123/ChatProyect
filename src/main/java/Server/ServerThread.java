package Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

public class ServerThread extends Thread {
    private Socket s;
    private int id;
    private DataInputStream in;
    private DataOutputStream ou;
    private Users list;
    private boolean exit;
    private String message, name, fecha;
    private File archivo;

    public ServerThread(Socket s, Users list, int id, File historial) throws IOException {
        this.s = s;
        this.id = id;
        this.list = list;
        this.fecha = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
        this.archivo = new File(historial, fecha+".txt");
        this.in = new DataInputStream(s.getInputStream());
        this.ou = new DataOutputStream(s.getOutputStream());
    }

    public ServerThread(Socket s, Users list, int id) throws IOException {
        this.s = s;
        this.id = id;
        this.list = list;
        this.in = new DataInputStream(s.getInputStream());
        this.ou = new DataOutputStream(s.getOutputStream());
    }

    public void run() {
        try {
            exit = false;
            name = in.readUTF();
            sendMessageUDP(name + " connected(UDP)");
            escribirHistorial(name + " connected",archivo);
        } catch (IOException io1) {
            System.out.println("Error Server Thread ");
        }
        while (!exit) {
            try {
                message = in.readUTF();
                if (message.equalsIgnoreCase("exit")) {
                    exit = true;
                    sendMessageUDP("- " + name + " disconected(UDP)");
                    sendMessage("Adios");
                    escribirHistorial("- " + name + " disconected",archivo);
                    removeClient();
                } else if (message.equalsIgnoreCase("/h")) {
                    ServerThreadFile serverThreadFile = new ServerThreadFile(s);
                    serverThreadFile.run();
                } else {
                    sendMessage("- " + name + " : " + message);
                    escribirHistorial("- " + name + " : " + message,archivo);
                }

            } catch (IOException io2) {
                System.out.println("Error Server Thread");
                io2.printStackTrace();
            }
        }
    }

    public boolean sendMessage(String message){
        try {
            Iterator it = list.getUsers().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                ou = new DataOutputStream(((Socket) pair.getValue()).getOutputStream());
                ou.writeUTF(message);
                return true;
            }
        } catch (IOException io) {
            System.out.println("Error send menssage");
        }
        return false;
    }

    public void removeClient() {
        list.removeUser(id);
    }

    public boolean UDP(Socket socket, String msg) {
        try {
            DatagramSocket socketUDP = new DatagramSocket(6667);
            byte[] buffer = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, socket.getInetAddress(), 6001);
            socketUDP.send(respuesta);
            socketUDP.close();
            return true;
        } catch (Exception e) {
             e.printStackTrace();
        }
        return false;
    }

    public void sendMessageUDP(String msg) {
        Iterator it = list.getUsers().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UDP((Socket) pair.getValue(),msg);
        }
    }

    public boolean escribirHistorial(String msg, File archivo) {
        try {
            FileWriter fichero = new FileWriter(archivo,true);
            BufferedWriter bw = new BufferedWriter(fichero);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(msg);
            bw.close();
            pw.close();
            return true;
        }catch (IOException e){
            System.out.println("Error en escritura del archivo");
            return false;
        }

    }

}
