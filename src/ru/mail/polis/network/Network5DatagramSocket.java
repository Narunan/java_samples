package ru.mail.polis.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
class Network5DatagramSocket {

    private final Logger log = Logger.getLogger(Network5DatagramSocket.class.getName());

    private class Client implements Runnable {

        private final DatagramSocket datagramSocket;
        private final byte[] buf = new byte[8096];

        Client(DatagramSocket datagramSocket) {
            this.datagramSocket = datagramSocket;
        }

        @Override
        public void run() {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        datagramSocket.receive(datagramPacket);
                        log.info(Thread.currentThread().getName() + " received: " +
                                new String(
                                    datagramPacket.getData(),
                                    0,
                                    datagramPacket.getLength())
                        );
                    } catch (IOException e) {
                        log.throwing("Client", "run", e);
                    }
                }
            } finally {
                datagramSocket.close();
            }
        }
    }

    private class Server extends TimerTask {

        private final DatagramSocket datagramSocket;

        Server(DatagramSocket datagramSocket) {
            this.datagramSocket = datagramSocket;
        }

        @Override
        public void run() {
            try {
                byte[] buf;
                while (!Thread.currentThread().isInterrupted()) {
                    buf = (new Date() + " from server").getBytes();
                    datagramSocket.send(
                            new DatagramPacket(
                                    buf, buf.length, InetAddress.getLocalHost(), 2000
                            )
                    );
                }
            } catch (IOException e) {
                log.throwing("Server", "run", e);
            } finally {
                datagramSocket.close();
            }
        }
    }

    private void run() throws IOException, InterruptedException {
        DatagramSocket clientDatagramSocket = new DatagramSocket(2000);
        Thread client = new Thread(
                new Client(clientDatagramSocket)
        );
        client.start();
        DatagramSocket serverDatagramSocket = new DatagramSocket(1000);
        Timer server = new Timer("Server");
        server.schedule(
                new Server(serverDatagramSocket),
                TimeUnit.SECONDS.toMillis(1),
                TimeUnit.SECONDS.toMillis(2)
        );
        TimeUnit.SECONDS.sleep(5);
        client.interrupt();
        server.cancel();
        clientDatagramSocket.close();
        serverDatagramSocket.close();
    }

    public static void main(String[] args) throws Exception {
        new Network5DatagramSocket().run();
    }
}
