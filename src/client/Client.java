package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天客户端
 */
public class Client {
    private Socket socket;

    public Client() throws IOException {
        System.out.println("正在启动客户端...");
        socket = new Socket("localhost",8088);
        System.out.println("已连接服务端！");
    }

    public void start(){
        try{
            Scanner scanner = new Scanner(System.in);
            String nickName = null;
            while(true){
                System.out.println("请输入昵称：");
                nickName = scanner.nextLine();
                if(nickName.length() == 0){
                    System.out.println("请至少输入一个字符");
                    continue;
                }
                break;
            }

            ServerHandler serverHandler = new ServerHandler();
            Thread t = new Thread(serverHandler);
            t.start();

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
            pw.println(nickName);
            System.out.println("欢迎您：" + nickName + "，开始聊天吧！");
            String message = null;
            while(true){
                message = scanner.nextLine();
                pw.println(message);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端启动失败！");
        }

    }

    class ServerHandler implements Runnable {
        @Override
        public void run() {
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                String message = null;
                while((message = br.readLine()) != null){
                    System.out.println(message);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
