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
 * @author carlos
 */
public class SmsManager {

    private static final String PORT = "1234";

    public static void startListening(final Display display)
            throws IOException {
        Thread clientThread = new Thread() {
            public void run() {
                try {
                    MessageConnection rx = (MessageConnection) Connector.open("sms://:" + PORT);
                    Message msgRxSMS = rx.receive();

                    while ((msgRxSMS != null)) {
                        if (msgRxSMS instanceof TextMessage) {
                            String mensajeRx = ((TextMessage) msgRxSMS).getPayloadText();
                            System.out.println(mensajeRx);
                            display.setCurrent(new Alert("Alerta!",
                                    mensajeRx,
                                    null, AlertType.ERROR));
                        }
                        msgRxSMS = rx.receive();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clientThread.start();
    }

    public static void notifyIncomingMessage(final MessageConnection connection,
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
}
