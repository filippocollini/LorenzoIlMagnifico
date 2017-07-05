package it.polimi.ingsw.ServerController;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.rmi.RMIServer;
import it.polimi.ingsw.ServerController.socket.SocketServer;

/**
 * proxy to SocketServer and RMIServer
 */
public class Server<M extends Serializable,T extends Serializable> implements ConnectionInterface<M,T> {

    private boolean doneSub = false;
    private ServerSocket socketSubscriber = null;
    private Rules rules;
    private RMIServer rmiServer;
    private SocketServer socketServer;
    private Thread subscriberListener;
    private Set<Thread> publishers = new HashSet<Thread>();
    private static ArrayList<Stanza> stanze;
    private Map<T,Set<PlayerInterface<M>>> subscriptions  = new HashMap<T, Set<PlayerInterface<M>>>();
    public static final int MAXPLAYERS = 4;
    public static final String EVENT_FAILED="fail";
    public static final String CLIENT_NEEDED="need";
    public static final String EVENT_DONE="done";

    /**
     * Default constructor
     */
    public Server() {
        rmiServer = new RMIServer(this);
        socketServer = new SocketServer(this);
        stanze = new ArrayList<>();
        rules= new Rules();
    }

    public static void main(String[] args) {
        //Server mi associa un messaggio ad una stanza
        //broker.startPublishers(broker, stanze.size()); //lo deve gestire il turn handler
        Server server = new Server();
        server.startServers(7771, 7772);

        System.out.println("Server started");
        /*System.out.println("(Type 'exit' to stop...)");

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
        sc.close();*/

        Scanner sc = new Scanner(System.in);
        while(true){
            if(sc.nextLine().equals("show")){
                for(Stanza s: stanze) {
                    System.out.println("------------");
                    for (String user : s.players.keySet())
                        System.out.println(user);
                }
        }

        }

    }

    public void startServers(int socketPort, int rmiPort){
        socketServer.startSocketSubscriberListener(socketPort);
        rmiServer.startRMIServer(rmiPort);
    }





    //quando si manderà al server un EndTurn si attiverà questo metodo che manderà a tutti l'aggiornamento
    //dello stato del gioco (Game)

    /*private void startPublishers(Server<String, Stanza> b, int N) {
        for (int i = 1; i <= N; i++) {
            final int a = i;
            Thread p = new Thread() {
                SocketSocketPublisher<String, Stanza> publisher = new SocketSocketPublisher<String, Stanza>(b);

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
                try {
                    sub.dispatchMessage(msg); //mando l'aggiornamento del Game a tutti i giocatori
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean joinPlayer(AbstractPlayer player, String username) {
        Stanza lastRoom = stanze.isEmpty() ? null : stanze.get(stanze.size() - 1);
        if(lastRoom!= null && lastRoom.players.containsKey(username))
            return false;
        else {
            try {
                joinLastRoom(player, username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void createNewRoom(AbstractPlayer player, String username) throws IOException {
        Stanza room = new Stanza();
        stanze.add(room);
        room.joinPlayer(player, username);
        player.setRoom(room);
        System.out.println("Il giocatore è nella stanza (con creazione di stanza)");
    }

    private void joinLastRoom(AbstractPlayer player, String username) throws IOException {
        Stanza lastRoom = stanze.isEmpty() ? null : stanze.get(stanze.size() - 1);
        if (lastRoom != null && lastRoom.nPlayers()<MAXPLAYERS && !lastRoom.matchStarted) {
            lastRoom.joinPlayer(player, username);
            player.setRoom(lastRoom);
            System.out.println("Il giocatore è nella stanza");
        } else {
            System.out.println("Sto creando una nuova stanza");
            createNewRoom(player, username);
        }

    }

    @Override
    public String handleRequest(String request) {
        /*Event event = rules.eventMap.get(request);
        String result=EVENT_FAILED;
        if(event!=null)
            System.out.println("trovato l'evento");
        else
            System.out.println("evento non trovato");
        if(event.isLegal())
            result = event.eventHappened();
        return result;*/
        return null;
    }

    
}