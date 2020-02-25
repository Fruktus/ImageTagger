package imagetagger;

import java.io.File;

public interface IFrontController {
    public Mode getMode();
    public void displayImageFromPath(String path);
    public void displayTime(long time);
    public void displayFilesProcessed(long count);
    public void displayFilePath(String path);
    public File getSourceFolder();
    public File getDestinationFolder();
}
