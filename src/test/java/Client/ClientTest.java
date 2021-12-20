package Client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    static ServerSocket ss;


    @BeforeAll
    static void init(){
        try {
            ss = new ServerSocket(6666);
        }catch (IOException e){e.printStackTrace();}

    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void inicioConexionTCP(int i)
    {
        String[] hosts = {"localhost","127.0.0.1"," ","localhost","192.168.1.170"};
        int[] ports = {6667,6666,34324,54321,6666};
        boolean[] resultados = {false,true,false,false,true};
        assertEquals(resultados[i],Client.inicioConexionTCP(hosts[i],ports[i]));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4})
    void UDP(int i){
        int[] ports = {-1,6666,34324,5432122,6666};
        boolean[] resultados = {false,true,true,false,false};
        assertEquals(resultados[i],Client.UDP(ports[i]));
    }
}