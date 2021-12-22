package Server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerThreadTest {

    static ServerSocket ss;
    static Socket s, s2;
    static Users list;
    static ServerThread st;


    @BeforeAll
    static void init(){
            list = new Users();
            try {
                ss = new ServerSocket(9999);
                for (int i = 0; i < 5; i++) {
                    s2 = new Socket("localhost",9999);
                    s=ss.accept();
                    list.addUser(i, s);
                }
            st=new ServerThread(s,list,4);
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    @Test
    void sendMessage() {
        assertEquals(true,st.sendMessage("msg"));
    }

    @Test
    void UDP() {
        assertEquals(true,st.UDP(s,"msg"));
    }

    @Test
    void escribirHistorial() {
        assertEquals(true,st.escribirHistorial("msg",new File("src/test/java/historialtest/hola")));
    }
}