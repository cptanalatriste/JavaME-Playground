package pe.edu.pucp.teleprocesamiento.form;

import java.io.IOException;
import javax.microedition.lcdui.CommandListener;

/**
 * Form for Living Room Screens.
 *
 * @author Carlos G. Gavidia
 */
public class LivingRoomForm extends RegularRoomForm {

    private static final String SCREEN_TITLE = "Sala";

    public LivingRoomForm(CommandListener commandListener) throws IOException {
        super(SCREEN_TITLE, commandListener);
    }
}
