package pe.edu.pucp.teleprocesamiento;

import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;
import pe.edu.pucp.teleprocesamiento.command.RoomSelectionManager;
import pe.edu.pucp.teleprocesamiento.form.InitialScreenForm;
import pe.edu.pucp.teleprocesamiento.form.RoomCatalogForm;

/**
 * MIDlet for the Java ME Application.
 *
 * @author Carlos G. Gavidia
 */
public class PlaygroundApp extends MIDlet implements CommandListener {

    public static final String NEXT_SCREEN_COMMAND = "Siguiente";
    public static final String ROOM_SELECTED_COMMAND = "Configurar";
    public static final String BACK_COMMAND = "Regresar";
    public static final String TURN_ON_COMMAND = "Encender";
    public static final String TURN_OFF_COMMAND = "Apagar";

    public void startApp() {
        initialize();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    private void initialize() {
        Displayable initialScreen = null;
        try {
            initialScreen = getInitialScreen();
        } catch (Exception ex) {
            initialScreen = new Alert("Error", "Ha ocurrido un error inesperado",
                    null, AlertType.ERROR);
            ex.printStackTrace();
        }
        Display.getDisplay(this).setCurrent(initialScreen);

    }

    private Form getInitialScreen() throws Exception {
        Form initialScreenForm = new InitialScreenForm();
        initialScreenForm.setCommandListener(this);
        return initialScreenForm;
    }

    private void showRoomCatalog() {
        RoomCatalogForm roomCatalogForm = new RoomCatalogForm();
        final Display display = Display.getDisplay(this);
        try {
            roomCatalogForm.setCommandListener(new RoomSelectionManager(roomCatalogForm, display));
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alertScreen = new Alert("Error", "Ha ocurrido un error inesperado",
                    null, AlertType.ERROR);
            Display.getDisplay(this).setCurrent(alertScreen);
        }
        display.setCurrent(roomCatalogForm);
    }

    public void commandAction(Command command, Displayable displayable) {
        String commandLabel = command.getLabel();
        if (NEXT_SCREEN_COMMAND.equals(commandLabel)) {
            showRoomCatalog();
        }
    }
}