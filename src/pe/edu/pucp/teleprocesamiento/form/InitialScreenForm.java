/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.pucp.teleprocesamiento.form;

import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;
import pe.edu.pucp.teleprocesamiento.PlaygroundApp;

/**
 * Initial Screen Form.
 *
 *
 * @author Carlos G. Gavidia
 */
public class InitialScreenForm extends Form {

    public static final String SCREEN_TITLE = "Java ME Playground";

    public InitialScreenForm() throws IOException {
        super(SCREEN_TITLE);
        this.append("Bienvenido");

        StringItem studentStringItem = new StringItem("Alumno:", "Carlos G. Gavidia");
        this.append(studentStringItem);

        StringItem programStringItem = new StringItem("Programa:", "Maestría en Informática");
        this.append(programStringItem);

        Image logoImage = Image.createImage("/logo.jpg");
        ImageItem logoImageItem = new ImageItem(null, logoImage, ImageItem.LAYOUT_CENTER, "logo");
        this.append(logoImageItem);
        this.addCommand(new Command(PlaygroundApp.NEXT_SCREEN_COMMAND, Command.OK, 1));
    }
}
