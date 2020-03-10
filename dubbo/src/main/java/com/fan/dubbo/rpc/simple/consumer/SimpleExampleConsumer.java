package main.java.com.fan.dubbo.rpc.simple.consumer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author 江蓠
 * @date 2020/3/10 9:08 下午
 */
public class SimpleExampleConsumer {

    public static void main(String[] args) throws Exception {
        String className = "main.java.com.fan.dubbo.rpc.simple.SimpleTest";
        String methodName = "hello";
        Class[] argumentsType = {String.class, String.class};
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();

        Object[] arguments = {"hello1号", "hello2号"};

        while (true) {
            Socket socket = new Socket("127.0.0.1", 8898);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(className);
            outputStream.writeUTF(methodName);
            outputStream.writeObject(argumentsType);
            outputStream.writeObject(arguments);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object result = inputStream.readObject();
            System.out.println(Thread.currentThread().getName() + "" + result);
            socket.close();
        }

    }

}
