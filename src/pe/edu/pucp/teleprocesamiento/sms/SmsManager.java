package pe.edu.pucp.teleprocesamiento.sms;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

/**
 *
 * Manager of SMS Connections.
 *
 * @author Carlos G. Gavidia
 */
public class SmsManager {

    private static final String PORT = "1234";
    private MessageConnection connection = null;

    public void startListening(final MessageListener listener)
            throws IOException {
        Thread clientThread = new Thread() {
            public void run() {
                System.out.println("Starting startListening ...");
                try {
                    connection =
                            (MessageConnection) Connector.open("sms://:" + PORT);
                    connection.setMessageListener(listener);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clientThread.start();

    }

    public void notifyIncomingMessage(final MessageConnection connection,
            final Display display) {
        System.out.println("Starting notifyIncomingMessage ...");

        try {
            Message message = connection.receive();
            if (message != null) {
                TextMessage textMessage = (TextMessage) message;
                display.setCurrent(new Alert("Alerta!",
                        textMessage.getPayloadText(),
                        null, AlertType.ERROR));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            display.setCurrent(new Alert("Error", "Ha ocurrido un error inesperado",
                    null, AlertType.ERROR));
        }
    }

    public void stopListening() {

        if (connection != null) {
            try {
                connection.setMessageListener(null);
                connection.close();
                connection = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}