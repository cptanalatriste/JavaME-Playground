package pe.edu.pucp.teleprocesamiento.command;

import java.io.IOException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import pe.edu.pucp.teleprocesamiento.PlaygroundApp;
import pe.edu.pucp.teleprocesamiento.bluetooth.BluetoothManager;
import pe.edu.pucp.teleprocesamiento.form.LivingRoomForm;
import pe.edu.pucp.teleprocesamiento.form.RegularRoomForm;
import pe.edu.pucp.teleprocesamiento.form.RoomCatalogForm;
import pe.edu.pucp.teleprocesamiento.http.HttpManager;

/**
 *
 * Command Listener for Room Selection.
 *
 * @author m523
 */
public class RoomSelectionManager implements CommandListener, DiscoveryListener {

    private static final int LIVINGROOM = 0;
    private static final int BEDROOM_1 = 1;
    private static final int BEDROOM_2 = 2;
    private static final int BEDROOM_3 = 3;
    private static final int BATHROOM = 4;
    private Display display = null;
    private final RoomCatalogForm roomCatalogForm;
    private RegularRoomForm livingRoomForm = null;
    private RegularRoomForm bedroom1Form = null;
    private RegularRoomForm bedroom2Form = null;
    private RegularRoomForm bedroom3Form = null;
    private RegularRoomForm bathroomForm = null;
    private HttpManager httpManager = null;
    private BluetoothManager bluetoothManager = null;

    public RoomSelectionManager(RoomCatalogForm roomCatalogForm, Display display)
            throws IOException {
        this.roomCatalogForm = roomCatalogForm;
        this.display = display;
        this.livingRoomForm = new LivingRoomForm(
                RoomSelectionManager.this);
        this.bedroom1Form = new RegularRoomForm("Dormitorio 1",
                RoomSelectionManager.this);
        this.bedroom2Form = new RegularRoomForm("Dormitorio 2",
                RoomSelectionManager.this);
        this.bedroom3Form = new RegularRoomForm("Dormitorio 3",
                RoomSelectionManager.this);
        this.bathroomForm = new RegularRoomForm("Baño",
                RoomSelectionManager.this);
        httpManager = new HttpManager();
        bluetoothManager = new BluetoothManager(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        String commandLabel = command.getLabel();
        try {
            if (PlaygroundApp.ROOM_SELECTED_COMMAND.equals(commandLabel)) {
                launchRoomScreen();
            } else if (PlaygroundApp.TURN_ON_COMMAND.equals(commandLabel)) {
                turnOnTheLight();
            } else if (PlaygroundApp.TURN_OFF_COMMAND.equals(commandLabel)) {
                turnOffTheLight();
            } else if (PlaygroundApp.BACK_COMMAND.equals(commandLabel)) {
                display.setCurrent(roomCatalogForm);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            display.setCurrent(new Alert("Error", "Ha ocurrido un error inesperado",
                    null, AlertType.ERROR));
        }

    }

    private void turnOnTheLight() {
        final int selectedRoom = roomCatalogForm.getRoomsChoiceGroup().
                getSelectedIndex();
        System.out.println("selectedRoom: " + selectedRoom);
        final RegularRoomForm roomForm = getRoomFromCode(selectedRoom);
        httpManager.turnOnTheLight(selectedRoom, roomForm);

    }

    private void turnOffTheLight() {
        final int selectedRoom = roomCatalogForm.getRoomsChoiceGroup().
                getSelectedIndex();
        System.out.println("selectedRoom: " + selectedRoom);
        final RegularRoomForm roomForm = getRoomFromCode(selectedRoom);
        httpManager.turnOffTheLight(selectedRoom, roomForm);
    }

    private void launchRoomScreen() throws IOException {
        final int selectedRoom = roomCatalogForm.getRoomsChoiceGroup().
                getSelectedIndex();
        System.out.println("selectedRoom: " + selectedRoom);
        final RegularRoomForm roomForm = getRoomFromCode(selectedRoom);
        httpManager.launchRoomScreen(selectedRoom, roomForm, display);

    }

    private RegularRoomForm getRoomFromCode(int selectedRoom) {
        RegularRoomForm roomForm = null;
        switch (selectedRoom) {
            case LIVINGROOM:
                roomForm = livingRoomForm;
                break;
            case BEDROOM_1:
                roomForm = bedroom1Form;
                break;
            case BEDROOM_2:
                roomForm = bedroom2Form;
                break;
            case BEDROOM_3:
                roomForm = bedroom3Form;
                break;
            case BATHROOM:
                roomForm = bathroomForm;
                startBluetoothServices();
                break;
        }
        return roomForm;
    }

    public void startBluetoothServices() {
        bluetoothManager.startDeviceSearch();
    }

    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        try {
            System.out.println("In deviceDiscovered");
            String friendlyName = btDevice.getFriendlyName(false);
            System.out.println("friendlyName: " + friendlyName);
            bluetoothManager.startServiceSearch(btDevice);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        bluetoothManager.setConnectionURL(servRecord);
    }

    public void serviceSearchCompleted(int transID, int respCode) {
        System.out.println("In servicesDiscovered");
        bluetoothManager.communicateWithServer(transID, respCode);
    }

    public void inquiryCompleted(int discType) {
        System.out.println("In IninquiryCompleted \n");
    }
}
