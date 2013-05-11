package pe.edu.pucp.teleprocesamiento.http;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Display;
import pe.edu.pucp.teleprocesamiento.form.RegularRoomForm;

/**
 *
 * Manager of HTTP Connections.
 *
 * @author Carlos G. Gavidia
 */
public class HttpManager {

    private static final String SERVER_URL = "http://127.0.0.1:8000";
    public static final String ROOM_ID_PARAM = "roomId";
    public static final String ACTION_PARAM = "action";
    public static final String STATUS_ACTION = "STATUS";
    public static final String TURN_ON_ACTION = "ON";
    public static final String TURN_OFF_ACTION = "OFF";
    public static final String LIGHT_IS_ON = "ON";

    public void turnOnTheLight(final int selectedRoom,
            final RegularRoomForm roomForm) {
        Thread clientThread = new Thread() {
            public void run() {
                try {
                    String dataFromServer = getDataFromServer(
                            SERVER_URL + "?" + ROOM_ID_PARAM + "="
                            + selectedRoom + "&" + ACTION_PARAM + "=" + TURN_ON_ACTION);
                    System.out.println("dataFromServer: " + dataFromServer);
                    if (dataFromServer != null) {
                        roomForm.turnOn();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clientThread.start();
    }

    public void turnOffTheLight(final int selectedRoom,
            final RegularRoomForm roomForm) {
        Thread clientThread = new Thread() {
            public void run() {
                try {
                    String dataFromServer = getDataFromServer(
                            SERVER_URL + "?" + ROOM_ID_PARAM + "="
                            + selectedRoom + "&" + ACTION_PARAM + "=" + TURN_OFF_ACTION);
                    System.out.println("dataFromServer: " + dataFromServer);
                    if (dataFromServer != null) {
                        roomForm.turnOff();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clientThread.start();
    }

    public void launchRoomScreen(final int selectedRoom,
            final RegularRoomForm roomForm, final Display display) {
        Thread clientThread = new Thread() {
            public void run() {
                try {
                    String dataFromServer = getDataFromServer(
                            SERVER_URL + "?" + ROOM_ID_PARAM + "="
                            + selectedRoom + "&" + ACTION_PARAM + "=" + STATUS_ACTION);
                    System.out.println("dataFromServer: " + dataFromServer);;
                    final boolean isLightOn = LIGHT_IS_ON.equals(dataFromServer.trim());
                    System.out.println("isLightOn: " + isLightOn);
                    if (isLightOn) {
                        roomForm.turnOn();
                    } else {
                        roomForm.turnOff();
                    }
                    display.setCurrent(roomForm);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clientThread.start();
    }

    private String getDataFromServer(String url) throws IOException {
        String dataFromServer = null;
        HttpConnection connection = (HttpConnection) Connector.open(url);
        if (connection.getResponseCode() == HttpConnection.HTTP_OK) {
            int length = (int) connection.getLength();
            InputStream inputStream = connection.openInputStream();
            if (length == -1) {
                int chunkSize = 1500;
                byte[] data = new byte[chunkSize];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int dataSizeRead = 0;
                while ((dataSizeRead = inputStream.read(data)) != -1) {
                    baos.write(data, 0, dataSizeRead);
                }
                dataFromServer = new String(baos.toByteArray());
                baos.close();
            } else {
                DataInputStream dis = new DataInputStream(inputStream);
                byte[] data = new byte[length];
                dis.readFully(data);
                dataFromServer = new String(data);
            }
        }
        return dataFromServer;
    }
}
