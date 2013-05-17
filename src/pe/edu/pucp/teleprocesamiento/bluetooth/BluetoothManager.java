package pe.edu.pucp.teleprocesamiento.bluetooth;

import java.io.IOException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author m523
 */
public class BluetoothManager {

    public static final String CLASS_IDENTIFIER = "11111111111111111111111111111111";
    private final DiscoveryAgent discoveryAgent;
    private final DiscoveryListener discoveryListener;

    public BluetoothManager(DiscoveryListener discoveryListener)
            throws BluetoothStateException {
        this.discoveryListener = discoveryListener;
        this.discoveryAgent = LocalDevice.getLocalDevice().getDiscoveryAgent();
    }

    public void startDeviceSearch() {
        System.out.print("In startDeviceSearch \n");
        try {
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

    public void communicateWithServer(int transID, int respCode) {
        if (DiscoveryListener.SERVICE_SEARCH_COMPLETED == transID) {
            try {
                StreamConnection streamConnection =
                        (StreamConnection) Connector.open(CLASS_IDENTIFIER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
