package com.whu.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by anyuan on 2016/11/10.
 */
public class Server {
    public static void main(String[] args) {

        try {
            //创建一个服务器端的Socket
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = null;
            //调用accept方法，等待连接
            System.out.println("***服务器正在启动，等待客户经理的连接***");
            while(true){
                socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端IP："+address.getHostAddress()+" 已处理客户端请求");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
