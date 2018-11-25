package ru.sample.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
class Network6Socket {

    private static final int PORT = 1234;

    private void log(String message) {
        System.out.println(Thread.currentThread().getName() + "% " + message);
    }

    class Client implements Runnable {

        private final long sleepTimeout;

        Client(long sleepTimeout) {
            this.sleepTimeout = sleepTimeout;
        }

        @Override
        public void run() {
            log("I'am born!");
            String name = Thread.currentThread().getName();
            try (Socket socket = new Socket(InetAddress.getLocalHost(), PORT)) {
                socket.setSoTimeout(1000);//1s in millis

                OutputStream outputStream = socket.getOutputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                for (int i = 0; i < 6; i++) {
                    // \n — здесь участвует ещё и как разделитель данных
                    String sendLine = name + " send " + i + "\n";
                    log("send: " + sendLine);
                    outputStream.write(sendLine.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();

                    String receivedLine = bufferedReader.readLine();
                    if (receivedLine == null) {
                        return;
                    }
                    log("receive: " + receivedLine);
                    try {
                        Thread.sleep(sleepTimeout);
                    } catch (InterruptedException e) {
                        log(e.getMessage());
                        break;
                    }
                }
                outputStream.write("exit\n".getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            } catch (IOException e) {
                log(e.getMessage());
            }
        }
    }

    class Server implements Runnable {

        private final DateTimeFormatter formatter = DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.MEDIUM)
                        .withZone(ZoneId.systemDefault());
        private final ServerSocket serverSocket;
        private final int nThreads;

        @SuppressWarnings("SameParameterValue")
        Server(ServerSocket serverSocket, int nThreads) {
            this.serverSocket = serverSocket;
            this.nThreads = nThreads;
        }

        @Override
        public void run() {
            ExecutorService executor = Executors.newFixedThreadPool(nThreads);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    executor.execute(new Task(serverSocket.accept()));
                }
            } catch (IOException e) {
                log(e.getMessage());
            } finally {
                executor.shutdownNow();
            }
        }

        class Task implements Runnable {

            private final Socket socket;

            Task(Socket socket) {
                this.socket = socket;
            }

            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                    );
                    PrintWriter printWriter = new PrintWriter(
                            socket.getOutputStream(), false, StandardCharsets.UTF_8
                    );
                    String line;
                    while (!Thread.currentThread().isInterrupted()) {
                        line = br.readLine();
                        if (null == line || "exit".equals(line)) {
                            return;
                        }
                        log("receive: " + line);
                        printWriter.println("accept <" + line + "> in " + formatter.format(Instant.now()));
                        printWriter.flush();
                    }
                } catch (IOException e) {
                    log(e.getMessage());
                } finally {
                    try {
                        log("interrupted before close = " + Thread.interrupted());
                        socket.close();
                    } catch (IOException e) {
                        log(e.getMessage());
                    }
                }
            }
        }

    }

    private void run() throws InterruptedException, IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 2)) {
            Thread server = new Thread(new Server(serverSocket, 2), "Server");
            server.start();

            Thread client1 = new Thread(new Client(1000), "C1");
            Thread client2 = new Thread(new Client(2000), "C2");
            Thread client3 = new Thread(new Client(3000), "C3");
            client1.start();
            client2.start();
            client3.start();

            client1.join();
            client2.join();
            client3.join();

            Thread client4 = new Thread(new Client(2000), "C4");
            client4.start();
//            {
//                TimeUnit.SECONDS.sleep(1);
//                server.interrupt();
//                serverSocket.close();
//            }
            client4.join();

            server.interrupt();
        }//serverSocket.close();
    }



    public static void main(String[] args) throws Exception {
        //Если на клиенте и сервере будут разные кодировки, то они друг друга не поймут
        System.out.println("Default charset: " + Charset.defaultCharset());
        new Network6Socket().run();
    }

}
