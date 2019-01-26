package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 一个简单的HTTP服务器实现
 */
public class HttpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(8080,10);
            while(true){
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                byte[] tmp = new byte[2048];
                is.read(tmp);
                System.out.println(new String(tmp,"UTF-8"));
                OutputStream os = socket.getOutputStream();
                os.write("HTTP/1.1 200OK\r\n".getBytes());
                os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
                os.write("Content-Length:38\r\n".getBytes());
                os.write("Server:gybs\r\n".getBytes());
                os.write(("Date:" + new Date() + "\r\n").getBytes());
                os.write("\r\n".getBytes());
                os.write("<h1>hello!</h1>".getBytes());
                os.write("<h3>HTTP服务器!</h3>".getBytes());
                os.close();
                is.close();
                socket.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
