package net.starkenberg.training.lasertagapi.game;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

@Service
public class UdpServer extends Thread {
    private final DatagramSocket incomingSocket;
    private final DatagramSocket outgoingSocket;
    private final Game game;
    private boolean running;
    private byte[] inbound = new byte[256];
    private byte[] outbound = new byte[256];

    public UdpServer(Game game) throws SocketException {
        this.game = game;
        outgoingSocket = new DatagramSocket(7500);
        incomingSocket = new DatagramSocket(7501);
    }

    @PostConstruct
    public void init() {
        this.start();
    }


    @SneakyThrows
    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(inbound, inbound.length);
            incomingSocket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(inbound, inbound.length, address, port);
            // incoming data int:int (player sending : player hit)
            String received
                    = new String(packet.getData(), 0, packet.getLength());
            int sender = Integer.parseInt(received.substring(0, received.indexOf(":")));
            int playerHit = Integer.parseInt(received.substring(received.indexOf(":")+1));
            sendUdpReply(address, playerHit);
            System.out.println(received);
        }
        incomingSocket.close();
        outgoingSocket.close();
    }

    private void sendUdpReply(InetAddress address, int player) throws IOException {
        inbound = ByteBuffer.allocate(Integer.SIZE/8).putInt(player).array();
        DatagramPacket packet
                = new DatagramPacket(outbound, outbound.length, address, 4445);
        outgoingSocket.send(packet);
    }

    private void close() {
        this.running = false;
    }
}
