package Server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    static Users users;
    static ServerSocket ss;
    static Socket[] sockets;

    @BeforeAll
    static void init(){
        sockets = new Socket[5];
        users = new Users();
        try{
            ss = new ServerSocket(7777);
            for (int i = 0; i< sockets.length; i++){
                Socket s = new Socket("localhost",7777);
                sockets[i] = ss.accept();
            }
        }catch (Exception e ){e.printStackTrace();}

    }

    @Test
    void getUsers() {
        HashMap<Integer, Socket> hashMap = new HashMap<>();
        assertInstanceOf(hashMap.getClass(),users.getUsers());
    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void addUser(int i) {
        Socket[] resultados = {null,sockets[0],sockets[1],sockets[2],sockets[3],sockets[4]};
        assertEquals(resultados[i],users.addUser(0,sockets[i]));

    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void removeUser(int i) {
        Socket[] resultados = {sockets[4],null,null,null,null};
        assertEquals(resultados[i],users.removeUser(i));

    }
}