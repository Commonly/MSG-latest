package me.jdog.msg.other.network;

import me.jdog.msg.other.network.type.ConnectionType;
import org.bukkit.Bukkit;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Muricans on 11/17/16.
 */
public class ServerUtils {
    private static ServerUtils instance = new ServerUtils();
    private String server = Bukkit.getServer().getIp();
    private int port = Bukkit.getServer().getPort();

    private ServerUtils() {
    }

    public static ServerUtils getInstance() {
        return instance;
    }

    public String getDataType(ConnectionType connection) {
        try {
            Socket socket = new Socket();
            OutputStream outputStream;
            DataOutputStream dataOutputStream;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            socket.setSoTimeout(2500);
            socket.connect(new InetSocketAddress(server, port), 2500);
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
            dataOutputStream.write(new byte[]{(byte) (0xFE), (byte) 0x01});
            int packetID = inputStream.read();
            int length = inputStreamReader.read();

            if (packetID == -1)
                Bukkit.getServer().getLogger().severe("End of stream");

            if (packetID != 0xFF)
                Bukkit.getServer().getLogger().severe("Invalid ID! " + packetID);

            if (length == -1)
                Bukkit.getServer().getLogger().severe("End of stream");
            if (length == 0)
                Bukkit.getServer().getLogger().severe("Invalid length");
            char[] chars = new char[length];
            if (inputStreamReader.read(chars, 0, length) != length)
                Bukkit.getServer().getLogger().severe("End of stream");

            String string = new String(chars);
            String[] data = string.split("\0");

            if (connection == ConnectionType.SV_ONLINE)
                return Integer.parseInt(data[4]) + "/" + Integer.parseInt(data[5]);

            if (connection == ConnectionType.SV_MOTD)
                return data[3];

            if (connection == ConnectionType.SV_VERSION)
                return data[2];

        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
