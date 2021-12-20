package Server;

import java.net.Socket;
import java.util.HashMap;

public class Users {
    private HashMap<Integer, Socket>list;

    public Users(){
        list=new HashMap<Integer,Socket>();
    }

    public HashMap<Integer, Socket> getUsers(){
        return list;
    }

    public synchronized Socket addUser(int key,Socket s){
        return list.put(key, s);
    }

    public synchronized Socket removeUser(int key){
            return list.remove(key);
    }

}





