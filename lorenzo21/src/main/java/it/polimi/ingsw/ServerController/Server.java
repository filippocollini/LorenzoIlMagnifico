package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * proxy to SocketServer and RMIServer
 */
public class Server<M extends Serializable,T extends Serializable> implements IServer<M,T> {

    private boolean doneSub = false;
    private ServerSocket socketSubscriber = null;
    private RMIServer rmiSubscriber = null;
    private Thread subscriberListener;
    private Set<Thread> publishers = new HashSet<Thread>();
    private static ArrayList<Stanza> stanze;
    private static final Object ROOMS_MUTEX = new Object();
    private Map<T,Set<PlayerInterface<M>>> subscriptions  = new HashMap<T, Set<PlayerInterface<M>>>();

    public static final int MAXPLAYERS = 4;


    /**
     * Default constructor
     */
    public Server() {
        stanze = new ArrayList<>();
    }

    public static void main(String[] args) {
        //Server mi associa un messaggio ad una stanza
        Server<String, Stanza> broker = new Server<>();
        broker.startSocketSubscriberListener(7771);
        //broker.startPublishers(broker, stanze.size()); //lo deve gestire il turn handler

        System.out.println("Server started");
        System.out.println("(Type 'exit' to stop...)");

        Scanner sc = new Scanner(System.in);
        String command = "";
        while (!command.equals("exit")) {
            command = sc.nextLine();
        }


        broker.stopSubscriberListener();
        broker.stopPublishers();

        try {
            broker.socketSubscriber.close();
        } catch (IOException e) {
        }
        sc.close();

    }

    private void startSocketSubscriberListener(int socketPort) {
        subscriberListener = new Thread() {
            @Override
            public void run() {
                try {
                    socketSubscriber = new ServerSocket(socketPort);
                    while (!doneSub) {
                        try {
                            Socket client = socketSubscriber.accept();
                            SubscriberHandler<M, T> handler = new SubscriberHandler<M, T>(Server.this, new SocketPlayer<Serializable>(client));
                            handler.start();
                            System.out.println("Adding new Player");
                        } catch (SocketTimeoutException | SocketException e) {

                        }
                    }
                    socketSubscriber.close();
                } catch (IOException e) {
                    throw new AssertionError("Error closing the socket", e);
                } finally {
                    if (socketSubscriber != null) {
                        try {
                            socketSubscriber.close();
                        } catch (IOException e) {
                            throw new AssertionError("Error closing the socket", e);
                        }
                    }
                }
            }
        };
        subscriberListener.start();

    }

    //quando si manderà al server un EndTurn si attiverà questo metodo che manderà a tutti l'aggiornamento
    //dello stato del gioco (Game)

    /*private void startPublishers(Server<String, Stanza> b, int N) {
        for (int i = 1; i <= N; i++) {
            final int a = i;
            Thread p = new Thread() {
                Publisher<String, Stanza> publisher = new Publisher<String, Stanza>(b);

                @Override
                public void run() {

                    Random r = new Random(1);
                    int num = r.nextInt(10000);
                    try {
                        while (true) {
                            publisher.publish("Message " + num, "game" + a);
                            Thread.sleep(5000L + num);
                            num = r.nextInt(10000);
                        }
                    } catch (InterruptedException e) {

                    }
                }
            };
            p.start();
            publishers.add(p);
        }

    }*/

    private void stopSubscriberListener() {
        doneSub = true;
        subscriberListener.interrupt();
    }

    private void stopPublishers() {
        for (Thread thread : publishers) {
            thread.interrupt();
        }
    }


    @Override
    public void subscribe(PlayerInterface<M> s, T room) {
        synchronized (subscriptions) {
            if(!subscriptions.containsKey(room)){
                subscriptions.put(room, new HashSet<PlayerInterface<M>>());
            }
            subscriptions.get(room).add(s);
        }
    }

    @Override
    public void unsubscribe(PlayerInterface<M> s, T topic) {

    }

    @Override
    public void publish(M msg, T room) {
        Set<PlayerInterface<M>> subs = subscriptions.get(room);
        if(subs!=null){
            for (PlayerInterface<M> sub: subs) {
                sub.dispatchMessage(msg); //mando l'aggiornamento del Game a tutti i giocatori
            }
        }
    }

    public Stanza joinRoom(SocketPlayer<Serializable> player, String username) {
        Stanza room = null;
            try {
                room=joinLastRoom(player, username);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return room;
    }

    private Stanza createNewRoom(SocketPlayer player, String username) throws IOException {
                Stanza room = new Stanza();
                stanze.add(room);
            return room;

    }

    private Stanza joinLastRoom(SocketPlayer player, String username) throws IOException {
        Stanza lastRoom = stanze.isEmpty() ? null : stanze.get(stanze.size() - 1);
        if (lastRoom != null && lastRoom.nPlayers()<MAXPLAYERS) {
            /*AbstractPlayer abstractPlayer= */
            lastRoom.joinPlayer(player, username);
            //abstractPlayer.setRoom(lastRoom);
        } else {
            lastRoom = createNewRoom(player, username);
        }
        return lastRoom;

    }
}