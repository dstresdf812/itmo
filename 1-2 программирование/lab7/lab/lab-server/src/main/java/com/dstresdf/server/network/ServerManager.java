package com.dstresdf.server.network;

import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.commands.Command;
import com.dstresdf.server.commands.CommandManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dstresdf.server.db.DatabaseManager;
import com.dstresdf.server.util.RequestKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ServerManager {
    private static final int len = 10000;
    private static final Logger LOG = LoggerFactory.getLogger(ServerManager.class);
    private final DatagramChannel channel;
    private final DatabaseManager databaseManager;
    private final ExecutorService requestReaders = Executors.newCachedThreadPool();
    private final ForkJoinPool requestHandlers = new ForkJoinPool();
    private ConcurrentHashMap<RequestKey, CompletableFuture<Response>> requestsList = new ConcurrentHashMap<>();
    public ServerManager(String host, int serverPort, DatabaseManager databaseManager) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
        this.channel.bind(new InetSocketAddress(host, serverPort));
        this.databaseManager = databaseManager;
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
                requestReaders.submit(() -> readRequest(requestData, clientAddress, commandManager));
            } catch (IOException e) {
                System.out.println("Ошибка сервера: " + e.getMessage());
            }
        }
    }

    private void readRequest(byte[] requestData, SocketAddress clientAddress, CommandManager commandManager) {
        try {
            Request request = deserializeRequest(requestData);
            LOG.info("Запрос десериализован.\n" + request.toString() + "\n");
            String clientAddressString = clientAddress.toString();
            requestHandlers.submit(() -> {
                try {
                    concurrentHandle(request, commandManager, clientAddressString, clientAddress);
                } catch (Exception e) {
                    Response response = new Response(false, "Ошибка обработки:" + e.getMessage(), null);
                    sendResponse(response, clientAddress);
                }
            });

        } catch (IOException e) {

        }
    }

    private void sendResponse(Response response, SocketAddress clientAddress) {
        try {
            LOG.info("Запрос обработан.");
            byte[] responseData = serializeResponse(response);
            byte[] responseLen = serializeNum(responseData.length);
            ByteBuffer responseLenBuffer = ByteBuffer.wrap(responseLen);
            ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
            synchronized (channel) {
                channel.send(responseLenBuffer, clientAddress);
                channel.send(responseBuffer, clientAddress);
            }
            LOG.info("Ответ отправлен.");
        } catch (Exception e) {
            LOG.info("Ошибка отправик ответа: " + e.getMessage());
        }
    }
    private byte[] serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(response);
        objectStream.flush();
        return byteStream.toByteArray();
    }

    private byte[] serializeNum(Integer len) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(len);
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


    private Response handle(Request request, CommandManager commandManager, String clientAddressString) throws IOException, SQLException, NoSuchAlgorithmException {
        if (request == null || request.getCommandType() == null) {
            return new Response(false, "Некорректный запрос.", null);
        }
        if (!request.getCommandType().equals(CommandType.LOGIN) && !request.getCommandType().equals(CommandType.REGISTER)) {
            if (!databaseManager.checkUser(request.getLogin(), request.getPassword())) {
                return new Response(false, "Не выполнена авторизация. Авторизуйтесь!", null);
            }
        }
        Command command = commandManager.getCommands().get(request.getCommandType().getName());
        if (command == null) {
            return new Response(false, "Неизвестная команда.", null);
        }

        LOG.info("Выполняется команда: " + request.getCommandType().getName() + "\n");
        request.setClient(clientAddressString);
        commandManager.addToHistory(command, clientAddressString);
        LOG.info("Команда " + command.getName() + "добавлена в историю.");
        return command.execute(request);
    }

    private void concurrentHandle(Request request, CommandManager commandManager, String clientAddressString, SocketAddress clientAddress) {
        RequestKey requestKey = new RequestKey(clientAddressString, request.getRequestId());
        AtomicBoolean created = new AtomicBoolean(false);

        CompletableFuture<Response> future = requestsList.computeIfAbsent(requestKey, key -> {
            created.set(true);
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return handle(request, commandManager, clientAddressString);
                } catch (Exception e) {
                    return new Response(false, "Ошибка сервера:" + e.getMessage(), null);
                }
            });
        });

        if (created.get()) {
            future.thenAccept(response -> sendResponse(response, clientAddress));
            return;
        }

        if (future.isDone()) {
            future.thenAccept(response -> sendResponse(response, clientAddress));
        }
    }
}