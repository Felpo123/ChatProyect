package Client;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {

    static ServerSocket ss;
    static Socket s,s2;
    static DataOutputStream ou;
    static ClientThread clientThread;

    @BeforeAll
    static void init(){
        try {
            ss = new ServerSocket(9999);
            s2 = new Socket("localhost",9999);
            s=ss.accept();
            ou = new DataOutputStream(s.getOutputStream());
            clientThread = new ClientThread(s2,"test");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void leerSegmento(int i) {
        try{
            String[] msgs = {"msg1","msg2","msg3","msg4","msg5"};
            ou.writeUTF(msgs[i]);
            String[] lecturas = new String[5];
            lecturas[i] = clientThread.leerSegmento();
            assertEquals(msgs[i],lecturas[i]);
        }catch (IOException e){
            System.out.println("Fallo el test");
        }


    }
}