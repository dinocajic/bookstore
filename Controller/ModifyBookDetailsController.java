package Controller;

import Controller.ControllerInterface.ModifyBookDetailsControllerInterface;
import GUIobjects.CMPanel;
import Model.ModifyBookDetailsModel;
import View.ModifyBookDetailsView;

/**
 * Created by Dino Cajic on 10/29/2016.
 *
 * Directs the communication between the ModifyBookDetailsModel and ModifyBookDetailsView
 */
public class ModifyBookDetailsController implements ModifyBookDetailsControllerInterface {

    CMPanel display;

    public ModifyBookDetailsController() {}

    /**
     * @param display The display object that was instantiated in the ApplicationWindow class
     */
    public ModifyBookDetailsController(CMPanel display) {
        this.display = display;
    }

    /**
     * Display Modify Book Details Form
     */
    public void modifyBookDetails() {
        ModifyBookDetailsView view = new ModifyBookDetailsView(this.display);
        view.modifyBookDetailsForm();
    }

    /**
     * Display Adjust Book Details Form
     * Since Modify Book details also has qty, it just calls that form.
     */
    public void adjustBookQty() {
        ModifyBookDetailsView view = new ModifyBookDetailsView(this.display);
        view.modifyBookQtyForm();
    }

    /**
     * Checks to see if the form that was filled out is empty
     * @param isbn The ISBN of the book
     * @return boolean : True if empty field
     */
    public boolean checkIfSearchFormEmpty(String isbn) {
        ModifyBookDetailsModel model = new ModifyBookDetailsModel();
        return model.checkIfSearchFormEmpty(isbn);
    }

    /**
     * Retrieves the book details
     * @param isbn The ISBN of the book
     * @return String[] book details or null if no results.
     */
    public String[] getBook(String isbn) {
        ModifyBookDetailsModel model = new ModifyBookDetailsModel();
        return model.getBook(isbn);
    }

    /**
     * Checks to see if any errors occurred on entry, such as not selecting the appropriate fields.
     * @param book The book array
     * @return String Error message if any
     */
    public String checkFormFields(String[] book) {
        ModifyBookDetailsModel model = new ModifyBookDetailsModel();
        return model.checkFormFields(book);
    }

    /**
     * Applies the updated changes
     * @param book The book array
     * @return String success message
     */
    public String insertBookChanges(String[] book, String newImagePath) {
        ModifyBookDetailsModel model = new ModifyBookDetailsModel();
        return model.insertBookChanges(book, newImagePath);
    }
}