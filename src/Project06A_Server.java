import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Project06A_Server {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = null;

        try {
            /* 임의의 Port를 열었다. */
            ss = new ServerSocket(9999);
            System.out.println("Server ready..");
        }catch (Exception e) {
            e.printStackTrace();
        }
        while (true){
            try {
                Socket socket = ss.accept();
                //Connection이 만들어지기 전까지는 blocking
                //Socket socket는 Client의 정보를 갖고 있을 것이다.
                System.out.println("client connect success ! ");
                InputStream inputStream = socket.getInputStream(); //주고 받는 데이터를 불러들이는 역할
                //그런데, inputStream은 한글 등이 들어오면 깨질 것이다. ->
                DataInputStream dataInputStream = new DataInputStream(inputStream); //한글 Data를 받아들이기 위함
                String message = dataInputStream.readUTF();

                OutputStream outputStream = socket.getOutputStream();//이제 Output, 출력하기 위함
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(" [ECHO :: ] = " + message + " (from Server !)");
                //데이터를 보냈으니, 이제 닫아야 한다.
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
                System.out.println("Client Closed");
            }catch (Exception e){
                e.printStackTrace();
            }
        }//while문 끝
    }//main 끝
}//class 끝
