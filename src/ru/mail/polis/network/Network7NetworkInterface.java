package ru.mail.polis.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
public class Network7NetworkInterface {

    public static void main(String args[]) throws SocketException {
        NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining((networkInterface -> {
            System.out.println("Display name: " + networkInterface.getDisplayName());
            System.out.println("Name: " + networkInterface.getName());

            try {
                System.out.println("Up: " + networkInterface.isUp());
                System.out.println("Loopback: " + networkInterface.isLoopback());
                System.out.println("PointToPoint: " + networkInterface.isPointToPoint());
                System.out.println("Supports multicast: " + networkInterface.supportsMulticast());
                System.out.println("Virtual: " + networkInterface.isVirtual());
                System.out.println("Hardware address: " + Arrays.toString(networkInterface.getHardwareAddress()));
                System.out.println("MTU: " + networkInterface.getMTU());
            } catch (SocketException e) {
                System.err.println(e.getMessage());
            }
            networkInterface.getInetAddresses().asIterator().forEachRemaining(System.out::println);
        }));
        /*
        Также есть фабричные методы
        - NetworkInterface.getByName(String name)
        - NetworkInterface.getByIndex(int index)
        - NetworkInterface.getByInetAddress(InetAddress addr)
         */
    }
}
