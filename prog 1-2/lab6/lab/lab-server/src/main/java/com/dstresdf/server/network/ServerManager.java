package com.dstresdf.server.network;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.commands.Command;
import com.dstresdf.server.commands.CommandManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerManager {
    private static final int len = 10000;
    private static final Logger LOG = LoggerFactory.getLogger(ServerManager.class);
    private final DatagramChannel channel;
    public ServerManager(String host, int serverPort) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
        this.channel.bind(new InetSocketAddress(host, serverPort));
    }

    public void start(CommandManager commandManager) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(len);
        LOG.info("Сервер запущен.");
        while (true) {
            try {
                buffer.clear();
                SocketAddress clientAddress = channel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }
                LOG.info("Получен запрос.");
                buffer.flip();
                byte[] requestData = new byte[buffer.remaining()];
                buffer.get(requestData);

                Request request = deserializeRequest(requestData);
                LOG.info("Запрос десериализован.\n" + request.toString() + "\n");
                Response response = handle(request, commandManager);
                LOG.info("Запрос обработан.");
                byte[] responseData = serializeResponse(response);
                ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
                channel.send(responseBuffer, clientAddress);
                LOG.info("Ответ отправлен.");
            } catch (IOException e) {
                System.out.println("Ошибка сервера: " + e.getMessage());
            }
        }
    }

    private byte[] serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(response);
        objectStream.flush();
        return byteStream.toByteArray();
    }

    private Request deserializeRequest(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        try (ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            try {
                return (Request) objectStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Не удалось десериализовать запрос клиента.", e);
            }
        }
    }

    private Response handle(Request request, CommandManager commandManager) throws IOException {
        if (request == null || request.getCommandType() == null) {
            return new Response(false, "Некорректный запрос.", null);
        }

        Command command = commandManager.getCommands().get(request.getCommandType().getName());
        if (command == null) {
            return new Response(false, "Неизвестная команда.", null);
        }
        LOG.info("Выполняется команда: " + request.getCommandType().getName() + "\n");
        return command.execute(request);
    }
}