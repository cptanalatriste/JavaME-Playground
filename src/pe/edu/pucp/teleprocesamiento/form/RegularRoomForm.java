package pe.edu.pucp.teleprocesamiento.form;

import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import pe.edu.pucp.teleprocesamiento.PlaygroundApp;

/**
 *
 * Form for regular room Screens.
 *
 * @author Carlos G. Gavidia
 */
public class RegularRoomForm extends Form {

    public static final int LIGHT_IMAGE_INDEX = 0;
    private Image lightbulbImageOff = Image.createImage("/light_bulb_off.png");
    private Image lightbulbImageOn = Image.createImage("/light_bulb_on.png");

    public RegularRoomForm(String title, CommandListener commandListener)
            throws IOException {
        super(title);
        this.setCommandListener(commandListener);
        this.addCommand(new Command(PlaygroundApp.BACK_COMMAND,
                Command.ITEM, 1));
        this.addCommand(new Command(PlaygroundApp.TURN_ON_COMMAND,
                Command.ITEM, 1));
        this.addCommand(new Command(PlaygroundApp.TURN_OFF_COMMAND,
                Command.ITEM, 1));
    }

    public final void turnLightOff() {
        if (this.size() > 0) {
            this.delete(LIGHT_IMAGE_INDEX);
        }
        ImageItem imageItem = new ImageItem(null, lightbulbImageOff,
                ImageItem.LAYOUT_CENTER,
                "lightOff");
        this.insert(LIGHT_IMAGE_INDEX, imageItem);
    }

    public final void turnLightOn() {
        if (this.size() > 0) {
            this.delete(LIGHT_IMAGE_INDEX);
        }
        ImageItem imageItem = new ImageItem(null, lightbulbImageOn,
                ImageItem.LAYOUT_CENTER,
                "lightOn");
        this.insert(LIGHT_IMAGE_INDEX, imageItem);
    }
}
