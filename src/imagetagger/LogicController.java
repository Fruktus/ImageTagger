package imagetagger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogicController implements ILogicController {
    private IFrontController model;
    private final CommandManager commandManager;
    private DirectoryStream<Path> sourceStream = null;
    private File destination = null;

    public LogicController(){
        this.commandManager = new CommandManager(20);
    }

    public void setModel(IFrontController model){
        this.model = model;
    }

    private void handleCommand(Class<?> cls, Object obj, Object value){
        //based on the type of modified object decide how to change state
        if(cls == javafx.scene.control.Label.class){
            ((javafx.scene.control.Label) obj).setText((String) value);
        }
    }

    // previous implementation for iterating over files in folder
//    public File getNextFile(){
//        while(true){
//            currentFile++;
//            if(srcImages.length == 0 || currentFile >= srcImages.length || SrcFolder == null){
//                System.out.println("getnextfile null");
//                return null;
//            }
//            System.out.println("checked file: " + srcImages[currentFile].getName() + " crnt: " + currentFile + " load status: " +
//                    new ImageIcon(srcImages[currentFile].getAbsolutePath()).getImageLoadStatus()); //debug info
//            if(new ImageIcon(srcImages[currentFile].getAbsolutePath()).getImageLoadStatus() == MediaTracker.COMPLETE){
//                this.processed++;
//                this.mw.setProcessedLabel(processed.toString());
//                return srcImages[currentFile];
//            }
//        }
//    }

    //# # # # # # # # # # #
    //# Interface methods #
    //# # # # # # # # # # #
    @Override
    public void processAction(String filePath, String Action) {

    }

    @Override
    public void setSource(File path) {
        try{
            this.sourceStream = Files.newDirectoryStream(path.toPath());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setDestination(File path) {
        this.destination = path;
    }

    @Override
    public void setMode(Mode mode) {

    }

    @Override
    public void showInExplorer(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(String path) {

    }

    @Override
    public void cutToClipboard(String path) {

    }

    @Override
    public void copyToClipboard(String path) {

    }

    @Override
    public void deleteFile(String path) {
        //popups: https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx

    }

    @Override
    public void undo() {
        Object actionSet = this.commandManager.undo();
        // TODO further processing, figure out how to store the actions
    }

    @Override
    public void redo() {
        Object actionSet = this.commandManager.undo();
        // TODO process the object
    }
}
