package imagetagger;

import java.io.File;

public interface ILogicController {
    public void processAction(String filePath, String Action);  // TODO: Actions will possibly be enums
    public void setSource(File path);
    public void setDestination(File path);
    public void setMode(Mode mode);

    public void showInExplorer(String path);
    // change preferences?
    public void save(String path);
    public void cutToClipboard(String path); //https://stackoverflow.com/questions/3591945/copying-to-the-clipboard-in-java
    public void copyToClipboard(String path);
    public void deleteFile(String path); // TODO should those operations (cut, copy, delete) be here or in front?

    public void undo(); // won't be void, need to return new set of parameters or can be void and just set them manually
    public void redo(); //same as above

    // save actions list
    // save current state
}
