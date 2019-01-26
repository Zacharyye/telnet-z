package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader br;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String msg = "Zachary";
        try{
            socket = new Socket(InetAddress.getLocalHost(),6666);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                out.println(msg);
                out.flush();
                String str = br.readLine();
                System.out.println("服务器说：" + str);
                if("exit".equalsIgnoreCase(msg)){
                    break;
                }
                msg = scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                out.close();
                br.close();
            } catch (Exception t){
                t.printStackTrace();
            }
        }
    }
}
