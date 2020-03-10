package main.java.com.fan.dubbo.rpc.simple.provide;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 江蓠
 * @date 2020/3/10 9:09 下午
 */
public class SimpleExampleProvide {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=new ServerSocket(8898);
        while (true){
            Socket socket=serverSocket.accept();
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
            String className=inputStream.readUTF();
            String methodName=inputStream.readUTF();
            Class<?>[] parameterTypes=(Class<?>[]) inputStream.readObject();
            Object[] arguments=(Object[]) inputStream.readObject();
            Class serviceClass=Class.forName(className);
            Object object=serviceClass.newInstance();
            System.out.println(arguments);
            Method method= serviceClass.getMethod(methodName,parameterTypes);
            Object result=method.invoke(object,arguments);

            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            socket.close();
        }
    }
}
