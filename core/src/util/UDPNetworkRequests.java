package util;


import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class UDPNetworkRequests {

    private DatagramSocket socket;



    public UDPNetworkRequests(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String hostName, int port, String message){
        byte[] buf;
        buf = message.getBytes(StandardCharsets.UTF_8);
        InetAddress address = null;

        try {
            address = InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port); //Message is delimited by its length

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendEcho(String hostName, int port, String msg){
        byte[] buf;
        buf = msg.getBytes();

        InetAddress address = null;

        try {
            address = InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        packet = new DatagramPacket(buf, buf.length);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }


}
