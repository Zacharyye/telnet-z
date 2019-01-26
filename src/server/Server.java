package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 聊天室服务端
 */
public class Server {
    private ServerSocket server;
    private Map<String, PrintWriter> allOut;

    private Server() throws IOException {
        server = new ServerSocket(8088);
        allOut = new HashMap<>();
    }

    private synchronized void addOut(String nickName,PrintWriter out){
        allOut.put(nickName,out);
    }

    private synchronized void removeOut(String nickName){
        allOut.remove(nickName);
    }

    private synchronized void sendMessage(String message){
        for(PrintWriter out : allOut.values()){
            out.println(message);
        }
    }

    private synchronized boolean sendMessageToNickName(String nickName,String message){
        String targetNickName = message.substring(1,message.indexOf(":"));
        if(allOut.containsKey(targetNickName)){
            PrintWriter pw = allOut.get(targetNickName);
            pw.println(nickName + "对你说：" + message.substring(message.indexOf(":") + 1));
            return true;
        }
        return false;
    }

    public void start(){
        try{
            while(true){
                System.out.println("等待客户端连接...");
                Socket socket = server.accept();
                System.out.println("一个客户端连接了...");
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败...");
        }

    }

    class ClientHandler implements Runnable {
        private Socket socket;
        private String host;
        private String nickName;

        public ClientHandler (Socket socket) {
            this.socket = socket;
            InetAddress address = socket.getInetAddress();
            host = address.getHostAddress();
        }

        @Override
        public void run() {
            PrintWriter pw = null;
            try {
                InputStream in = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                nickName = br.readLine();
                OutputStream out = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
                pw = new PrintWriter(osw,true);
                addOut(nickName,pw);
                sendMessage(nickName + "上线了！当前在线人数为：" + allOut.size());
                String message = null;
                while((message = br.readLine()) != null){
//                    System.out.println(host + "说" + message);
//                    pw.println(host + "说" + message);
                      if(message.startsWith("@")){
                          sendMessageToNickName(nickName,message);
                      } else {
                          sendMessage(nickName + "说：" + message);
                      }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                removeOut(nickName);
                sendMessage(nickName + "下线了！当前在线人数为：" + allOut.size());
                if(socket != null){
                    try{
                        socket.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
