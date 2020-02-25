package imagetagger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class FrontController implements IFrontController {
    @FXML
    private MenuItem MenuSave;
    @FXML
    private MenuItem MenuSaveAs;
    @FXML
    private MenuItem MenuShowInExplorer;
    @FXML
    private MenuItem MenuPreferences;
    @FXML
    private MenuItem MenuQuit;

    @FXML
    private Label FolderSourceLabel;
    @FXML
    private Button FolderSourceButton;
    @FXML
    private Label FolderDestinationLabel;
    @FXML
    private Button FolderTargetButton;
    @FXML
    private RadioButton ModeMove;
    @FXML
    private RadioButton ModeCopy;

    @FXML
    private VBox ActionVBox;  //contains all defined actions
    @FXML
    private Button AddActionButton;


    @FXML
    private Label ImageLabel;  // used for showing information about no file loaded or displaying the image

    @FXML
    private Label StatusFilePath;
    @FXML
    private Label StatusFilesProcessed;
    @FXML
    private Label StatusTimeElapsed;

    private File SourceFolder = null;
    private File DestinationFolder = null;


    public FrontController(){

    }

    @FXML
    private void initialize(){ // called after initialization of fxml doc
        // possibly unnecessary
    }

    // # # # # # # #
    // # Handlers  #
    // # # # # # # #

    private File chooseFolder(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Source Directory");
        return chooser.showDialog(ActionVBox.getScene().getWindow());
    }

    @FXML
    private void chooseSrcDir(){
        File result = chooseFolder();  // TODO logging?
        this.SourceFolder = result != null ? result : this.SourceFolder;
        this.FolderSourceLabel.setText(result != null ? result.getAbsolutePath() : "None");

    }

    @FXML
    private void chooseDstDir(){
        File result = chooseFolder();  // TODO logging?
        this.DestinationFolder = result != null ? result : this.DestinationFolder;
        this.FolderDestinationLabel.setText(result != null ? result.getAbsolutePath() : "None");
    }





    // # # # # # # # # # # #
    // # Interface methods #
    // # # # # # # # # # # #
    @Override
    public Mode getMode() {  // TODO enum instead?
        RadioButton res = (RadioButton) ModeCopy.getToggleGroup().getSelectedToggle();
        return res.getText().equals(ModeMove.getText()) ? Mode.MOVE : Mode.COPY;
    }

    @Override
    public void displayImageFromPath(String path) {
        ImageLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(path))));
    }

    @Override
    public void displayTime(long time) {
        String formattedTime = "";  // TODO formatting function is in old files
        StatusTimeElapsed.setText("Time spent: " + formattedTime);
    }

    @Override
    public void displayFilesProcessed(long count) {
        StatusFilesProcessed.setText("Files processed: " + count);
    }

    @Override
    public void displayFilePath(String path) {
        StatusFilePath.setText("File: " + path);
    }

    @Override
    public File getSourceFolder() {
        return this.SourceFolder;
    }

    @Override
    public File getDestinationFolder() {
        return this.DestinationFolder;
    }
}
