package pe.edu.pucp.teleprocesamiento.form;

import java.io.IOException;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

/**
 * Form for Living Room Screens.
 *
 * @author Carlos G. Gavidia
 */
public class LivingRoomForm extends RegularRoomForm {

    private static final String SCREEN_TITLE = "Sala";
    public static final int WIFI_IMAGE_INDEX = 1;
    private Image wifiImageOff = Image.createImage("/wifi_off.png");
    private Image wifiImageOn = Image.createImage("/wifi_on.png");
    private ImageItem wifiImageItem = null;

    public LivingRoomForm(CommandListener commandListener) throws IOException {
        super(SCREEN_TITLE, commandListener);
    }

    public final void disableWifi() {
        if (this.size() > 1) {
            this.delete(WIFI_IMAGE_INDEX);
        }
        ImageItem imageItem = new ImageItem(null, wifiImageOff,
                ImageItem.LAYOUT_CENTER,
                "wifiOff");
        this.insert(WIFI_IMAGE_INDEX, imageItem);
    }

    public final void enableWifi() {
        if (this.size() > 1) {
            this.delete(WIFI_IMAGE_INDEX);
        }
        ImageItem imageItem = new ImageItem(null, wifiImageOn,
                ImageItem.LAYOUT_CENTER,
                "wifiOn");
        this.insert(WIFI_IMAGE_INDEX, imageItem);
    }
}
