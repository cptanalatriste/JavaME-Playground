package pe.edu.pucp.teleprocesamiento.form;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import pe.edu.pucp.teleprocesamiento.PlaygroundApp;

/**
 *
 * Room Catalog Form.
 *
 * @author Carlos G. Gavidia
 */
public class RoomCatalogForm extends Form {

    public static final String FORM_TITLE = "Selecione habitación";
    private static String[] avaiableRooms = {"Sala", "Dormitorio 1",
        "Dormitorio 2", "Dormitorio 3", "Baño"};
    private ChoiceGroup roomsChoiceGroup = new ChoiceGroup("Habitaciones disponibles:",
            Choice.EXCLUSIVE, avaiableRooms, null);

    public RoomCatalogForm() {
        super(FORM_TITLE);
        this.append(roomsChoiceGroup);
        this.addCommand(new Command(PlaygroundApp.ROOM_SELECTED_COMMAND, Command.ITEM, 1));
    }

    public ChoiceGroup getRoomsChoiceGroup() {
        return roomsChoiceGroup;
    }
}
