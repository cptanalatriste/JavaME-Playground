package pe.edu.pucp.teleprocesamiento.bluetooth;

import java.io.OutputStream;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import pe.edu.pucp.teleprocesamiento.form.LivingRoomForm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author m523
 */
public class BluetoothManager {

    public static final String ENABLE_COMMAND = "ENABLE";
    public static final String DISABLE_COMMAND = "DISABLE";
    public static final String CLASS_IDENTIFIER = "11111111111111111111111111111111";
    private final DiscoveryAgent discoveryAgent;
    private final DiscoveryListener discoveryListener;
    private String connectionURL;
    private String message;
    private LivingRoomForm livingRoomForm;

    public BluetoothManager(DiscoveryListener discoveryListener)
            throws BluetoothStateException {
        this.discoveryListener = discoveryListener;
        this.discoveryAgent = LocalDevice.getLocalDevice().getDiscoveryAgent();
    }

    public void startDeviceSearch(String message, LivingRoomForm livingRoomForm) {

        try {
            System.out.print("In startDeviceSearch \n");
            this.livingRoomForm = livingRoomForm;
            this.message = message;
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, discoveryListener);
        } catch (BluetoothStateException ex) {
            ex.printStackTrace();
        }
    }

    public void startServiceSearch(RemoteDevice remoteDevice) {
        UUID[] searchList = new UUID[1];
        searchList[0] = new UUID(CLASS_IDENTIFIER, false);
        try {
            discoveryAgent.searchServices(new int[]{0x100}, searchList,
                    remoteDevice, discoveryListener);
        } catch (BluetoothStateException ex) {
            ex.printStackTrace();
        }

    }

    public void communicateWithServer(int transID, final int respCode) {
        Thread clientThread = new Thread() {
            public void run() {
                System.out.println("respCode:" + respCode);
                System.out.println("connectionURL:" + connectionURL);


                if (DiscoveryListener.SERVICE_SEARCH_COMPLETED == respCode) {
                    System.out.println("(DiscoveryListener.SERVICE_SEARCH_COMPLETED:"
                            + DiscoveryListener.SERVICE_SEARCH_COMPLETED);

                    try {
                        StreamConnection streamConnection =
                                (StreamConnection) Connector.open(connectionURL);
                        System.out.println("streamConnection:" + streamConnection);

                        OutputStream outputStream = streamConnection.openOutputStream();
                        System.out.println("outputStream:" + outputStream);

                        System.out.println("Sending message ...");
                        outputStream.write(message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                        System.out.println("Message sent ...");
                        if (ENABLE_COMMAND.equals(message)) {
                            livingRoomForm.enableWifi();
                        } else {
                            livingRoomForm.disableWifi();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        clientThread.start();
    }

    public void setConnectionURL(ServiceRecord[] servRecord) {
        if (servRecord != null && servRecord.length > 0) {
            this.connectionURL = servRecord[0].getConnectionURL(0, false);
        }
    }
}
