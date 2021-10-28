package com.cloudloan.bootcamp.homework.h06;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Http Server - BIO 实现
 * <p>
 * 使用 Java Socket 实现
 *
 * @author zhaochen
 * @since 2021/10/06
 */
public class BioHttpServer {

    /**
     * launcher
     *
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        final int port = 8801;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动 HttpBioServer, 监听端口:" + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理请求
     *
     * @param socket {@link Socket}
     */
    private static void service(Socket socket) {
        try {
            System.out.println("HttpBioServer 处理请求 开始");
            String requestData = getRequestData(socket);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, I'm BioHttpServer, I received data:\n" + requestData;
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
            System.out.println("HttpBioServer 处理请求 完成, 响应内容:\n" + body);
        } catch (Exception e) {
            System.err.println("HttpBioServer 处理请求 异常, " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 提取请求中的数据
     *
     * @param socket {@link Socket}
     * @return 请求中的数据
     */
    private static String getRequestData(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()).length() != 0) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

}
