/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetagger;

import java.awt.MediaTracker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_OPTION;

/**
 * Handles the files operations and showing them in JFrame
 * @author fruktus
 */
public class Controller { //might rename later
    
    //maybe add and array of boxes (strings for shortcuts) or give it somehow access to jframe
    private File SrcFolder;
    private File DstFolder;
    private File[] srcImages;
    private Integer currentFile;
    private final MainWindow mw;
    private final Properties config;
    private Integer processed;
    private final Command cmd;
    
    
    public Controller(MainWindow mw){
        this.SrcFolder = null; //for error checking if not set
        this.DstFolder = null;
        this.mw = mw;
        this.cmd = new Command(20);
        this.processed = 0;
        config = new Properties();
        if(new File("./config.properties").isFile()){
            this.loadConfig();
        }
    }
    
    
    public void setSrcFolder(File src){
        this.SrcFolder = src;
        this.srcImages = SrcFolder.listFiles();
        this.currentFile = -1; //because of the way i handle getting next pictures
        this.mw.setSrcLabel(src.getAbsolutePath());
        for (File srcImage : srcImages) {
            //debug info
            System.out.println("src: " + srcImage);
        }
        //this.cmd.addCommand(new String[]{"SET_SRC", this.SrcFolder.getAbsolutePath()});
        if(this.getNextFile() != null){
            this.mw.setImage(this.getCurrentFile());
        }
    }
    

    public void setDstFolder(File dst){
        this.mw.setDstLabel(dst.getAbsolutePath());
        this.DstFolder = dst;
        //this.cmd.addCommand(new String[]{"SET_DST", this.DstFolder.getAbsolutePath()});
    }
    
    
    // actually, this one may not need to check for nulls since nextfile does that
    // optimization: make getcurrent return local variable, make getnext set it, remove checking from getcurrent completely
    /**
     * 
     * @return File with current index or null if encountered error like empty folder or no pictures
     */
    public File getCurrentFile(){
        System.out.println("currentfile: " + currentFile + " srclen:" + srcImages.length); //debug info
        if(srcImages.length == 0 || srcImages.length <= currentFile || SrcFolder == null){
            System.out.println("getcurrentfile error"); //debug info
            return null;
        }
        return srcImages[currentFile];
    }
    
    /**
     * 
     * @return next File (and moves index) or null if encountered error or directory empty or no pictures
     */
    public File getNextFile(){
        while(true){
            currentFile++;
            if(srcImages.length == 0 || currentFile >= srcImages.length || SrcFolder == null){
                System.out.println("getnextfile null");
                return null;
            }
            System.out.println("checked file: " + srcImages[currentFile].getName() + " crnt: " + currentFile + " load status: " + 
                    new ImageIcon(srcImages[currentFile].getAbsolutePath()).getImageLoadStatus()); //debug info
            if(new ImageIcon(srcImages[currentFile].getAbsolutePath()).getImageLoadStatus() == MediaTracker.COMPLETE){
                this.processed++;
                this.mw.setProcessedLabel(processed.toString());
                return srcImages[currentFile];
            } 
        }
    }
    
    /* logging errors to file
    fw = new FileWriter ("exception.txt", true);
    pw = new PrintWriter (fw);
    e.printStacktrace(pw)
    */
    
    
    private void undo(){
        Object[] params = this.cmd.undo();
        if(params == null){
            return;
        }
        switch((CommandAction)params[0]){
            case SET_SRC:
                //if someone deleted the folder before?
                //this.SrcFolder = new File((String) params[1]);
            break;
            
            case SET_DST:
                //this.DstFolder = new File((String) params[1]);
            break;
            
            case IMG_IDX:
                //params structure for this one: (mode taken from current state, as changes will be tracked as well)
                //IMG_IDX subfolder filename index
                
                //src and target as in controller, will be handled by separate actions
                //mode means copy or move, no delete since i have no idea how to handle restoring it
                //this.currentFile = (Integer) params[1];
                //this.mw.setImage(this.getCurrentFile());
                //depending on move/copy (should be in params) return the file to original folder or delete copy
                if(params[1].equals("s")){
                    this.currentFile = (Integer) params[2]; //todo: must use getprevious or smth since jumps lengths are undefined
                    this.mw.setImage(this.getCurrentFile());
                }else if(params[1].equals("c")){
                    try {
                        Files.delete(Paths.get(this.DstFolder.getAbsolutePath() + "/" + params[2] + "/" + params[3]));
                        this.currentFile = (Integer) params[4];
                        this.processed -= 1;
                        this.mw.setProcessedLabel(this.processed.toString());
                        this.mw.setImage(this.getCurrentFile());
                    } catch (NoSuchFileException x) {
                        System.err.format("%s: no such" + " file or directory%n", srcImages[currentFile].toPath());
                    } catch (IOException x) {
                        // File permission problems are caught here.
                        System.err.println(x);
                    }
                }else{ //image was moved
                    try {
                        Files.move(Paths.get(this.DstFolder.getAbsolutePath() + "/" + params[2] + "/" + params[3]), Paths.get(this.SrcFolder.getAbsolutePath() + "/" + params[3]));
                        this.currentFile = (Integer) params[4];
                        this.processed -= 1;
                        this.mw.setProcessedLabel(this.processed.toString());
                        this.mw.setImage(this.getCurrentFile());
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            break;
                    
            case  IMG_MD: //state change
                //this.mw.setMode((String) params[1]);
            break;

            default:
            break;
        }
    }
    
    private void redo(){
        Object[] params = this.cmd.redo();
        if(params == null){
            return;
        }
        switch((CommandAction)params[0]){
            //params
            //SET_SRC oldSource newSource lastIndex
            case SET_SRC:
                //this.SrcFolder = new File((String) params[2]);
                //this.srcImages = this.SrcFolder.listFiles();
                //this.currentFile = new Integer((String) params[3]);
                //if(this.getCurrentFile() != null){
                //    this.mw.setImage(this.getCurrentFile());
                //}
            break;
            
            //params
            //SET_DST oldDest newDest
            case SET_DST:
                //this.DstFolder = new File((String) params[2]);
            break;
            
            case IMG_IDX: //fix!
                if(params[1].equals("s")){
                    this.currentFile = (Integer) params[2]; //todo: must use getprevious or smth since jumps lengths are undefined
                    this.mw.setImage(this.getCurrentFile());
                }else if(params[1].equals("c")){
                    try {
                        Files.delete(Paths.get(this.DstFolder.getAbsolutePath() + "/" + params[2] + "/" + params[3]));
                        this.currentFile = (Integer) params[4];
                        this.processed += 1;
                        this.mw.setProcessedLabel(this.processed.toString());
                        this.mw.setImage(this.getCurrentFile());
                    } catch (NoSuchFileException x) {
                        System.err.format("%s: no such" + " file or directory%n", srcImages[currentFile].toPath());
                    } catch (IOException x) {
                        // File permission problems are caught here.
                        System.err.println(x);
                    }
                }else{ //image was moved
                    try {
                        Files.move(Paths.get(this.SrcFolder.getAbsolutePath() + "/" + params[3]), Paths.get(this.DstFolder.getAbsolutePath() + "/" + params[2] + "/" + params[3]));
                        this.currentFile = (Integer) params[4];
                        this.processed += 1;
                        this.mw.setProcessedLabel(this.processed.toString());
                        this.mw.setImage(this.getCurrentFile());
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            break;
            
            //params:
            //IMG_MD oldMode newMode
            case IMG_MD:
                //this.mw.setMode((String) params[2]);
            break;

            default:
            break;
        }
    }
    
    // TODO move all that to keypressaction or whatever its called in mainwindow
    // and create more public methods in controller which would handle what this method does, but separately
    public void handleKeyShortcut(int num){ //rename? 
        if(num == 10){
            //System.out.println("undo not fully implemented"); //debug info
            this.undo();
            return;
        }else if(num == 11){
            //System.out.println("redo not fully implemented"); //debug info
            this.redo();
            return;
        }
        
        String s = mw.getComboBoxValue(num);
        System.out.println("ctrl:" + s); // debug info
        if(s.equals("Nothing")){
            return;
        }
        if(SrcFolder == null || DstFolder == null){
            JOptionPane.showMessageDialog(new JFrame(),
                "Select target and source first.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        switch(s){
            case "Skip": //get next file from list
                Object[] tmp = new Object[]{CommandAction.IMG_IDX, "s", this.currentFile};
                if(this.getNextFile() != null){
                    mw.setImage(this.getCurrentFile());
                    this.cmd.addCommand(tmp);
                    //IMG_IDX mode subfolder filename 
                }else{
                    mw.setImage(null);
                    mw.setLabels("<last file>", "<no file>");
                }
                break;
                
                
            case "Delete": //prompt if really delete, maybe ask if remember for future
                if(this.getCurrentFile() == null){
                    break;
                }
                int n = JOptionPane.showConfirmDialog( //how to use it with keyboard https://stackoverflow.com/questions/22325640/closing-a-jdialog-by-hitting-the-enter-on-keyboard
                    new JFrame(),
                    "Are you sure you want to delete file?\nThis cannot be undone",
                    "Confirm deletion",
                    JOptionPane.YES_NO_OPTION);
                if(n == YES_OPTION){
                    System.out.println("file deleted (not really)"); //debug info
                    try {
                        Files.delete(this.getCurrentFile().toPath());
                        if(this.getNextFile() != null){
                            mw.setImage(this.getCurrentFile());
                        }else{
                            mw.setImage(null);
                            mw.setLabels("<last file>", "<no file>");
                        }
                    } catch (NoSuchFileException x) {
                        System.err.format("%s: no such" + " file or directory%n", srcImages[currentFile].toPath());
                    } catch (IOException x) {
                        // File permission problems are caught here.
                        System.err.println(x);
                    }
                }
            break;
                
                
            case "": //shouldn't be empty, ignore or prompt
                JOptionPane.showMessageDialog(new JFrame(),
                "The target field cannot be empty.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            break;
                
                
            default: //target is folder name
                if(this.getCurrentFile() == null){ //debug: possibly not needed
                    break;
                }
                
                File t = new File(DstFolder, s); //get File object of SrcFolder's subdirectory s
                if(!t.isDirectory()){ //if there is no folder s, make it
                    t.mkdir();
                }
                if("c".equals(mw.getMode())){
                    try { //for ex permission error
                    System.out.println("copying: " + this.getCurrentFile().getAbsolutePath() + " to " +
                            t.getPath() + "/" + this.getCurrentFile().getName() + "\n"); //debug info
                    
                    if(new File(t.getPath() + "/" +  this.getCurrentFile().getName()).isFile()){
                        int res = JOptionPane.showConfirmDialog( //how to use it with keyboard https://stackoverflow.com/questions/22325640/closing-a-jdialog-by-hitting-the-enter-on-keyboard
                            new JFrame(),
                            "File exists. Replace?",
                            "Confirm replacement",
                            JOptionPane.YES_NO_OPTION);
                        if(res == JOptionPane.YES_OPTION){
                            Files.copy(Paths.get(this.getCurrentFile().getAbsolutePath()), Paths.get(t.getPath() + "/" +  this.getCurrentFile().getName()), REPLACE_EXISTING);
                        }else{
                            return;
                        }
                    }
                    Files.copy(Paths.get(this.getCurrentFile().getAbsolutePath()), Paths.get(t.getPath() + "/" +  this.getCurrentFile().getName()));
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try { //for ex permission error
                    System.out.println("moving: " + this.getCurrentFile().getAbsolutePath() + " to " +
                            t.getPath() + "/" + this.getCurrentFile().getName() + "\n"); //debug info
                    
                    if(new File(t.getPath() + "/" +  this.getCurrentFile().getName()).isFile()){
                        int res = JOptionPane.showConfirmDialog( //how to use it with keyboard https://stackoverflow.com/questions/22325640/closing-a-jdialog-by-hitting-the-enter-on-keyboard
                            new JFrame(),
                            "File exists. Replace?",
                            "Confirm replacement",
                            JOptionPane.YES_NO_OPTION);
                        if(res == JOptionPane.YES_OPTION){
                            Files.move(Paths.get(this.getCurrentFile().getAbsolutePath()), Paths.get(t.getPath() + "/" +  this.getCurrentFile().getName()), REPLACE_EXISTING);
                        }else{
                            return;
                        }
                    }
                    
                    Files.move(Paths.get(srcImages[currentFile].getAbsolutePath()), Paths.get(t.getPath() + "/" +  this.getCurrentFile().getName()));
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //IMG_IDX mode subfolder filename 
                this.cmd.addCommand(new Object[]{CommandAction.IMG_IDX, this.mw.getMode(), s, this.getCurrentFile().getName(), this.currentFile});
                if(this.getNextFile() != null){ //if next returned null, there arent any more pictures
                    mw.setImage(this.getCurrentFile()); //we already moved indexes with nextfile
                }else{
                    mw.setImage(null);
                    mw.setLabels("<last file>", "<no file>");
                }
            break;    
        }
    }
    
    
    //possibly surround with try catch for null excp and if occurs delete config
    //or dont and trust that config is okay if exists (user error prone)
    
    
    private void loadConfig(){
        try {
            config.load(new FileInputStream("./config.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //i assume that either config doesnt exist or is okay, if someone modded it and failed itll crash
        if(!"null".equals(this.config.getProperty("SrcFolder"))){
            File src = new File(this.config.getProperty("SrcFolder"));
            if(src.isDirectory()){
                this.setSrcFolder(src);
                this.currentFile = new Integer(this.config.getProperty("CurrentFile"));   
            }
        }
        
        if(!"null".equals(this.config.getProperty("DstFolder"))){
            File dst = new File(this.config.getProperty("DstFolder"));
            if(dst.isDirectory()){
                 this.setDstFolder(dst);
            }
        }
        
        this.mw.setMode(this.config.getProperty("Mode"));
        this.processed = new Integer(this.config.getProperty("ProcessedFiles"));
        this.mw.setProcessedLabel(this.processed.toString());
        mw.setElapsedTime(new Long(this.config.getProperty("ElapsedTime")));
        
        for(int i=0; i<10; i++){
            if(this.config.getProperty(Integer.toString(i)) != null){
                this.mw.setComboBoxValue(i, this.config.getProperty(Integer.toString(i)));
            }
        }
    }

    public void saveConfig(){
        if(SrcFolder != null){
            this.config.setProperty("SrcFolder", SrcFolder.getAbsolutePath());
            this.config.setProperty("SrcSize", Integer.toString(srcImages.length));
            this.config.setProperty("CurrentFile", currentFile.toString());
        }else{
            this.config.setProperty("SrcFolder", "null");
            this.config.setProperty("SrcSize", "null");
            this.config.setProperty("CurrentFile", Integer.toString(this.currentFile));
        }
        
        if(DstFolder != null){
            this.config.setProperty("DstFolder", DstFolder.getAbsolutePath());
        }else{
            this.config.setProperty("DstFolder", "null");
        }
        
        this.config.setProperty("Mode", this.mw.getMode());
        this.config.setProperty("ProcessedFiles", processed.toString());
        this.config.setProperty("ElapsedTime", Long.toString(mw.getElapsedTime()));
        
        for(int i=0; i<10; i++){
            if(!this.mw.getComboBoxValue(i).equals("Nothing")){
                this.config.setProperty(Integer.toString(i), this.mw.getComboBoxValue(i));
            }
        }
        
        
        try {
            this.config.store(new FileWriter("./config.properties"), null);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
