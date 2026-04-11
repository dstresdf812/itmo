package com.dstresdf.client.network;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class ClientManager {
    private static final int len = 10000; // считать динамически
    private static final int timeout = 3000;
    private static final int retryes = 3;

    private final InetAddress serverAddress;
    private final int serverPort;
    private final DatagramSocket socket;

    public ClientManager(String host, int port) throws Exception {
        this.serverAddress = InetAddress.getByName(host);
        this.serverPort = port;
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(timeout);
    }

    public Response sendRequest(Request request) {
        try {
            byte[] requestData = serialize(request);

            DatagramPacket sendPacket = new DatagramPacket(requestData, requestData.length, serverAddress, serverPort);

            for (int i = 0; i < retryes; i++) {
                try {
                    socket.send(sendPacket);

                    byte[] buffer = new byte[len];
                    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(receivePacket);

                    byte[] responseData = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
                    return deserialize(responseData);

                } catch (SocketTimeoutException e) {
                    System.out.println("Сервер временно недоступен. Попытка " + (i + 1) + " из " + retryes);
                }
            }

            return new Response(false, "Не удалось получить ответ от сервера.", null);

        } catch (IOException e) {
            return new Response(false, "Ошибка сети: " + e, null);
        }
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(request);
        objectStream.flush();
        return byteStream.toByteArray();
    }

    private Response deserialize(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        try (ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            try {
                return (Response) objectStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Не удалось десериализовать ответ сервера.", e);
            }
        }
    }
}