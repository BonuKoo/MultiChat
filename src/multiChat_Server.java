import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class multiChat_Server {

    HashMap clients;

    public multiChat_Server(HashMap clients) {
        clients = new HashMap<>();
        Collections.synchronizedMap(clients);
    }

    public void start(){
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("start Server....");
            while (true){
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress()+":"+socket.getPort()+"connect!");
                //TODO : ServerReceiver 구현


            }
        } catch (Exception e ){e.printStackTrace();}
    }


    void sendToAll(String message){
        Iterator iterator = clients.keySet().iterator();
        while (iterator.hasNext()){
            try {
                DataOutputStream dataOutputStream = (DataOutputStream) clients.get(iterator.next());
                dataOutputStream.writeUTF(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {}//main Method

        class ServerReceiver extends Thread {
            Socket socket;
            DataInputStream dataInputStream;
            DataOutputStream dataOutputStream;

            ServerReceiver(Socket socket){
                this.socket = socket;
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }//생성자

            public void run(){
                String name = "";
                try {
                    name = dataInputStream.readUTF(); //park, lee
                    if (clients.get(name) != null){
                        dataOutputStream.writeUTF("#Already exist name : " + name);
                        dataOutputStream.writeUTF("Please reconnect by other name");
                        System.out.println(socket.getInetAddress()+":" + socket.getPort()
                        +"disconnect!");
                        dataInputStream.close();
                        dataOutputStream.close();
                        socket.close();
                        socket=null;

                    } else {
                        sendToAll("#"+name+"joind");
                        clients.put(name,dataOutputStream);
                        while (dataInputStream!=null){
                            sendToAll(dataInputStream.readUTF());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (socket!=null){
                        sendToAll("#" + name + "exit");
                        clients.remove(name);
                        System.out.println(socket.getInetAddress()+":"
                        + socket.getPort()+"disconnect!");
                    }
                }
            }

        }//ServerReceiver


}
