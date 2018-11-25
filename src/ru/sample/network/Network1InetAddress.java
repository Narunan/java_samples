package ru.sample.network;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
class Network1InetAddress {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress;

        inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress /* == inetAddress.getHostName()+ "/" + inetAddress.getHostAddress() */);

        inetAddress = InetAddress.getByName("yandex.ru");
        System.out.println(inetAddress);

        try {
            inetAddress = InetAddress.getByName("unknown");
            System.out.println("false == " + inetAddress.isReachable(1000)); //1s
        } catch (IOException expected) {
            System.err.println(expected.getMessage());
        }

        InetAddress[] inetAddresses = InetAddress.getAllByName("www.google.com");
        System.out.println("Google--");
        for (InetAddress ia : inetAddresses) {
            System.out.println(ia + " : " + ia.getCanonicalHostName());
            byte[] ip = ia.getAddress();
            InetAddress inetAddressByIP = InetAddress.getByAddress(ip);
            System.out.println(
                    inetAddressByIP + " : " + inetAddressByIP.getCanonicalHostName() + ". "
                            + "ipv6 = " + (inetAddressByIP instanceof Inet6Address)
            );
        }
        System.out.println("--Google");
    }
}
