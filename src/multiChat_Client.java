import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class multiChat_Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",7777 );
            Scanner scanner = new Scanner(System.in);
            System.out.println("User Name :: ");
            String name = scanner.nextLine();

            Thread sender = new Thread(new ClientSender(socket,name));
            //TODO
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();

        } catch (Exception e){ e.printStackTrace();}
    }//main Method


    static class ClientSender extends Thread{
        Socket socket;
        String name;
        DataOutputStream dataOutputStream;

        ClientSender(Socket socket, String name){
            this.socket = socket;
            this.name = name;
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                // getOutputStream 오류 생겼을 때
                e.printStackTrace();
            }
        } //생성자

        public void run(){
            Scanner scanner = new Scanner(System.in);

            try {

                if (dataOutputStream != null)
                    dataOutputStream.writeUTF(name);

                while (dataOutputStream !=null){
                String message = scanner.nextLine();
                if (message.equals("quit")) break;
                dataOutputStream.writeUTF("["+name+"]"+message);
                }//while
                dataOutputStream.close();
                socket.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }//run
    }//ClientSender

    static class ClientReceiver extends Thread{
        Socket socket;
        DataInputStream dataInputStream;
        ClientReceiver(Socket socket){
            this.socket = socket;
            try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }
        }//생성자
    public void run(){
        while (dataInputStream != null) {
            try {
                System.out.println(dataInputStream.readUTF());
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }//while문
        try {
            dataInputStream.close();
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    }
}
