package com.rabbit.example.consumer;

import com.rabbit.example.data.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientSocket {
    public static ClientSocket clientSocket;

    private static Selector selector;

    public static void main(String[] args) throws Exception {
        ClientSocket.getInstance().init("localhost", 8001).listen();
    }

    public static ClientSocket getInstance() {
        if (clientSocket == null) {
            synchronized (ClientSocket.class) {
                if (clientSocket == null) {
                    clientSocket = new ClientSocket();
                }
            }
        }

        return clientSocket;
    }

    public ClientSocket init(String ip, int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(ip, port));
        socketChannel.configureBlocking(false);

        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        return this;
    }

    public void listen() throws Exception {
        System.out.println("client start");

        while (true) {
            selector.select();

            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();

            while (selectionKeys.hasNext()) {
                SelectionKey selectionKey = selectionKeys.next();
                selectionKeys.remove();

                if (selectionKey.isConnectable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.configureBlocking(false);

                    Data data = new Data();
                    data.setClassName("com.test.Test");
                    data.setMethod("hello");
                    Class[] argumentsType = {String.class, String.class};
                    Object[] arguments = {"阳阳1号", "阳阳2号"};
                    data.setArguments(arguments);
                    data.setArguments(argumentsType);






                    /**
                     * 数据定义 参数 method class
                     */
/*
                   ByteBuffer buffer = ByteBuffer.wrap(ObjectAndByteUtils.toByteArray(data));

                    socketChannel.write(buffer);*/

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("client write");
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.configureBlocking(false);

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int length = socketChannel.read(buffer);

                    System.out.println("client read: " + socketChannel + new String(buffer.array(), 0, length));

                    socketChannel.write(ByteBuffer.wrap(("client " + System.currentTimeMillis()).getBytes()));

                    Thread.sleep(10000);
                }
            }

        }
    }
}