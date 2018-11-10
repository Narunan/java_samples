package ru.mail.polis.network;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
public class Network2URL {

    /*
        protected String toExternalForm(URL u) {
            String s;
            return u.getProtocol()
                    + ':'
                    + (((s = u.getAuthority()) != null && s.length() > 0)
                    ? "//" + s : "")
                    + (((s = u.getPath()) != null) ? s : "")
                    + (((s = u.getQuery()) != null) ? '?' + s : "")
                    + (((s = u.getRef()) != null) ? '#' + s : "");
        }
        */

    public static void main(String args[]) throws MalformedURLException {
        URL url = new URL("https://mikhail@non.existent.site:9012/ru/index.html?id=10#anchor");
        /*
         * authority = [user-info@]host[:port]
         * file = path[?query]
         * url protocol://authority/file[#ref]
         */
        System.out.println("authority = " + url.getAuthority());
        System.out.printf("userInfo = %s\nhost = %s\nport = %s\n",
                url.getUserInfo(),
                url.getHost(),
                url.getPort()
        );
        System.out.println();

        System.out.println("file = " + url.getFile());
        System.out.printf("path = %s\nquery = %s\n",
                url.getPath(),
                url.getQuery()
        );
        System.out.println();

        System.out.println("url = " + url);
        System.out.printf("protocol = %s\nauthority = %s\nfile = %s\nref = %s\n",
                url.getProtocol(),
                url.getAuthority(),
                url.getFile(),
                url.getRef()
        );
    }
}
