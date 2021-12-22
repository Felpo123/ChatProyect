package Server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerThreadFileTest {
    static ServerSocket ss;
    static Socket s, s2;
    static ServerThreadFile stf;

    @BeforeAll
    static void init(){
        try {
             ss = new ServerSocket(9999);
             s2 = new Socket("localhost",9999);
             s=ss.accept();
             stf = new ServerThreadFile(s);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void enviarContenidoArchivo(int i){
        String[] paths = {"src/main/java/Historial/2021.12.10","src/main/java/Historial/Noexiste","src/main/java/Historial/2021.12.11",
                          "src/main/java/Historial/2021.12.1", "src/main/java/Historial/2021.12.3"};
        boolean[] resultados = {true,false,true,false,false};
        assertEquals(resultados[i],stf.enviarContenidoArchivo(paths[i]));
    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4})
    void leerHistorial(int i) {
        File[] files = {new File("src/test/java/historialtest"),new File("src/main/java/Historial"),
                            new File("src/test/java/"), new File("src"), new File("src/main/java")};
        String[] resultados = {"\n2021.12.10\n2021.12.11\nhola","\n2021.12.10\n2021.12.11","\nClient\nhistorialtest\nServer",
                                 "\nmain\ntest","\nClient\nHistorial\nServer"};
        assertEquals(resultados[i], stf.leerHistorial(files[i]));
    }


}