package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer_Z {
    private static BufferedReader br;
    private static PrintWriter pw;
    private static ServerSocket serverSocket;
    private static  Socket socket;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(6666);
            System.out.println("服务器已开启...");
            Socket sk = serverSocket.accept();
            System.out.println("连接成功~");
            br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()));
            while(true){
                //侦听用户连接，没有用户连接的时候会在此停留
                String str = br.readLine();
                System.out.println("客户端说：" + str);
                String reply = scanner.nextLine();
                pw.println(reply);
                pw.flush();
                if("exit".equalsIgnoreCase(str)) {
                    break;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                br.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
