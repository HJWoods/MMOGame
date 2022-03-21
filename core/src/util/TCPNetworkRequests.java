package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public abstract class TCPNetworkRequests {
    protected static HashMap<String,Socket> sockets = new HashMap<String,Socket>();



    public static void sendDataToSocket(String socketName, int data) {
        try {
            Socket socket = sockets.get(socketName);
            if(socket == null){
                System.out.println("NetworkRequests: No socket open called '" + socketName + "'");
                return;
            }
            OutputStream output = socket.getOutputStream();
            output.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendDataToSocket(String socketName, byte[] data) {
        try{
            Socket socket = sockets.get(socketName);
            if(socket == null){
                System.out.println("NetworkRequests: No socket open called '" + socketName + "'");
                return;
            }
            OutputStream output = socket.getOutputStream();
            output.write(data);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void sendDataToSocket(String socketName, String data) {
        try{
            Socket socket = sockets.get(socketName);
            if(socket == null){
                System.out.println("NetworkRequests: No socket open called '" + socketName + "'");
                return;
            }
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true); //Wrap the OutputStream in a printer so
            writer.println(data);                                       // that we can send text
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Socket createNewSocket(String socketName, String hostName, int port){
        Socket socket = null;
        try {
            socket = new Socket(hostName,port);
            socket.setTcpNoDelay(false); //Disable Nagle's algorithm
            sockets.put(socketName, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static byte[] readSocketData(String socketName, int bytesToRead){
        Socket socket = sockets.get(socketName);
        InputStream input = null;
        byte[] bytes = new byte[bytesToRead];
        if(socket == null){
            System.out.println("NetworkRequests: No socket open called '" + socketName + "'");
            return null;
        }
        try {
            input = socket.getInputStream();
            int bytesRead = input.read(bytes); //WHY DOES IT WORK LIKE THIS?!?!?!?
            //TODO: provide some way to also retrieve bytesRead
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Integer readNextByteFromSocket(String socketName){
        Socket socket = sockets.get(socketName);
        InputStream input = null;
        if(socket == null){
            System.out.println("NetworkRequests: No socket open called '" + socketName + "'");
            return null;
        }
        int output = 0;
        try {
            output = input.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return output;
    }

}
