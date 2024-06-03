import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Project06A_Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9999);// ----------------------> 서버 쪽 정보가 담겨있다
            System.out.println("Connection Success !!");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            OutputStream outputStream = socket.getOutputStream();

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF(message);

            InputStream inputStream = socket.getInputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            System.out.println("Receive :: " + dataInputStream.readUTF());  //Echo message 등이 출력될 것

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
