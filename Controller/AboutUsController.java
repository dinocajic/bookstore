package Controller;

import Controller.ControllerInterface.AboutUsControllerInterface;
import GUIobjects.CMPanel;
import View.AboutUsView;

/**
 * Created by Zachary Shoults on 11/22/2016.
 *
 * Calls the AboutUsView
 */
public class AboutUsController implements AboutUsControllerInterface {

    private CMPanel display;

    /**
     * Initializes the display property
     * @param display_panel The main display
     */
    public AboutUsController(CMPanel display_panel){
        this.display = display_panel;
    }

    /**
     * Call AboutUsView and displays the contact information for the client
     * and the development team as set in the Config file.
     */
    public void displayAboutUsInformation(){
        AboutUsView view = new AboutUsView(this.display);
        view.displayAboutUsDetails();
    }
}