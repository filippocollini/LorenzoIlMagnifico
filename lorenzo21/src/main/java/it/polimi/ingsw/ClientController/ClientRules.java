package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.EventInputStream;
import it.polimi.ingsw.ServerController.EventOutputStream;
import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.TurnHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by filippocollini on 11/06/17.
 */
public class ClientRules {

    private final HashMap<String, Handler> answerMap;

    private TurnHandler turn;

    public static final String ERROR = "error, cannot answer";

    private Scanner scanner;

    public ClientRules(){
        scanner = new Scanner(System.in);
        answerMap= new HashMap<>();
        createMapping();
    }

    private void createMapping() {
        answerMap.put(Message.ESEMPIO_REPLY, this::esempio);

    }

    public String esempio(){
        System.out.println("Cosa vuoi dal market?");
        String answer = scanner.nextLine();
        return answer;
    }

    //----------------------------------------------------------


    public String responseHandler(Object object) {
        String response = ERROR;
        Handler handler = answerMap.get(object);
        if (handler != null) {
            response = handler.handle();
        }
        return response;
    }

    @FunctionalInterface
    private interface Handler {
        String handle();
    }

}
