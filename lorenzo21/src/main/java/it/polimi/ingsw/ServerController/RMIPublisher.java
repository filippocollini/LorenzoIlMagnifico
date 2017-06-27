package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 18/06/17.
 */
public class RMIPublisher<M,T> implements RMIPublisherInterface<M, T>{

    ConnectionInterface<M, T> broker;
    T topic;

    public RMIPublisher(ConnectionInterface<M, T> b, T t) {
        broker = b;
        topic = t;
    }

    @Override
    public ConnectionInterface<M, T> getBroker() {
        return broker;
    }

    /*

    public static void main(String[] argv){
        try {

            System.out.println("Type the topic you want to publish about:");
            Scanner sc = new Scanner(System.in);
            String topic = sc.nextLine();

            Registry reg = LocateRegistry.getRegistry(7772);

            ConnectionInterface<String,Stanza> broker = (ConnectionInterface<String,Stanza>) reg.lookup("RMIBrokerInt");


            Thread publisherThread = new Thread(){
                private RMIPublisher<String, Stanza> publisher = new RMIPublisher<String, Stanza>(broker, topic);

                @Override
                public void run(){
                    Random r = new Random(1);
                    int num = r.nextInt(10000);
                    try {
                        while(true){
                            publisher.publish("Message "+ num, topic);
                            Thread.sleep(5000L+num);
                            num = r.nextInt(10000);
                        }
                    } catch (InterruptedException e) {

                    }
                }

            };

            publisherThread.start();




            System.out.println("Publisher started");
            System.out.println("(Type 'exit' to stop...)");

            String command = "";
            while(!command.equals("exit")){
                command = sc.nextLine();
            }

            publisherThread.interrupt();
            sc.close();


        } catch (RemoteException | NotBoundException e) {

        }
    }


    */
}
