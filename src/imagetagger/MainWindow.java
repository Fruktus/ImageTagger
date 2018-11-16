/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetagger;

import com.sun.glass.events.KeyEvent;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author fruktus
 */
public class MainWindow extends javax.swing.JFrame implements ActionListener {
    private Controller ctrl;
    private ImageIcon currentImage;
    private final long startTime;
    private long elapsedTime;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        
        Timer timer = new Timer(1000, this);
        timer.setInitialDelay(1);
        timer.start();
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        
    
        currentImage = null;
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) { //or simply autosave
                if (JOptionPane.showConfirmDialog(new JFrame(), 
                    "Do you want to save progress and settings?", "Close Window?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.out.println("saving config");
                    ctrl.saveConfig();
                }
            }
        });        
    }
        
    @Override
    public void actionPerformed(ActionEvent e){
        //System.out.println(e.getSource());
        LabelTime.setText("Time spent: " + this.getFormattedTime());
    }
    
    public long getElapsedTime(){
        return System.currentTimeMillis() - this.startTime;
    }
    
    public void setElapsedTime(long t){
        this.elapsedTime = t;
    }
    
    private String getFormattedTime(){
        String h,m,s;
        long time = System.currentTimeMillis() - this.startTime + this.elapsedTime;
        time /= 1000;
        int second = (int) time % 60;
        time /= 60;
        int minute = (int) time % 60;
        time /= 60;
        int hour = (int) time;
        if(second < 10){
            s = "0"+second;
        }else{
            s = Integer.toString(second);
        }
        if(minute < 10){
            m = "0"+minute;
        }else{
            m = Integer.toString(minute);
        }
        if(hour < 10){
            h = "0"+hour;
        }else{
            h = Integer.toString(hour);
        }
        return h + ":" + m + ":" + s;
    }

    public void setController(Controller c){
        this.ctrl = c;
        
        //<editor-fold defaultstate="collapsed" desc=" KeyBindings setup ">
        // for some reason numkeys are in 96-105 for 0..9 keys
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("0"), "num_0");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(96, 0), "num_0");
        MenuPanel.getActionMap().put("num_0", new KeyAction(0, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("1"), "num_1");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(97, 0), "num_1");
        MenuPanel.getActionMap().put("num_1", new KeyAction(1, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("2"), "num_2");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(98, 0), "num_2");
        MenuPanel.getActionMap().put("num_2", new KeyAction(2, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("3"), "num_3");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(99, 0), "num_3");
        MenuPanel.getActionMap().put("num_3", new KeyAction(3, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("4"), "num_4");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(100, 0), "num_4");
        MenuPanel.getActionMap().put("num_4", new KeyAction(4, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("5"), "num_5");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(101, 0), "num_5");
        MenuPanel.getActionMap().put("num_5", new KeyAction(5, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("6"), "num_6");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(102, 0), "num_6");
        MenuPanel.getActionMap().put("num_6", new KeyAction(6, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("7"), "num_7");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(103, 0), "num_7");
        MenuPanel.getActionMap().put("num_7", new KeyAction(7, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("8"), "num_8");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(104, 0), "num_8");
        MenuPanel.getActionMap().put("num_8", new KeyAction(8, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("9"), "num_9");
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(105, 0), "num_9");
        MenuPanel.getActionMap().put("num_9", new KeyAction(9, ctrl));
        
        //inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK), "foo"); for ctrlz 
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "ctrlz");
        MenuPanel.getActionMap().put("ctrlz", new KeyAction(10, ctrl));
        MenuPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK), "ctrlshz");
        MenuPanel.getActionMap().put("ctrlshz", new KeyAction(11, ctrl));
        
        //to override when typing
        ComboBox0.getInputMap().put(KeyStroke.getKeyStroke("0"), "none");
        ComboBox0.getInputMap().put(KeyStroke.getKeyStroke(96, 0), "none");
        ComboBox1.getInputMap().put(KeyStroke.getKeyStroke("1"), "none");
        ComboBox1.getInputMap().put(KeyStroke.getKeyStroke(97, 0), "none");
        ComboBox2.getInputMap().put(KeyStroke.getKeyStroke("2"), "none");
        ComboBox2.getInputMap().put(KeyStroke.getKeyStroke(98, 0), "none");
        ComboBox3.getInputMap().put(KeyStroke.getKeyStroke("3"), "none");
        ComboBox3.getInputMap().put(KeyStroke.getKeyStroke(99, 0), "none");
        ComboBox4.getInputMap().put(KeyStroke.getKeyStroke("4"), "none");
        ComboBox4.getInputMap().put(KeyStroke.getKeyStroke(100, 0), "none");
        ComboBox5.getInputMap().put(KeyStroke.getKeyStroke("5"), "none");
        ComboBox5.getInputMap().put(KeyStroke.getKeyStroke(101, 0), "none");
        ComboBox6.getInputMap().put(KeyStroke.getKeyStroke("6"), "none");
        ComboBox6.getInputMap().put(KeyStroke.getKeyStroke(102, 0), "none");
        ComboBox7.getInputMap().put(KeyStroke.getKeyStroke("7"), "none");
        ComboBox7.getInputMap().put(KeyStroke.getKeyStroke(103, 0), "none");
        ComboBox8.getInputMap().put(KeyStroke.getKeyStroke("8"), "none");
        ComboBox8.getInputMap().put(KeyStroke.getKeyStroke(104, 0), "none");
        ComboBox9.getInputMap().put(KeyStroke.getKeyStroke("9"), "none");
        ComboBox9.getInputMap().put(KeyStroke.getKeyStroke(105, 0), "none");
        //</editor-fold>
    }
    
    /**
     * Used only for KeyBindings
     */
    private class KeyAction extends AbstractAction { 
        private final int number;
        private final Controller ctrl; 

        KeyAction(int number, Controller ctrl) {
            this.number = number;
            this.ctrl = ctrl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // pass the number to controller so it can look for the appropriate string itself
            System.out.println(number); //debug info
            ctrl.handleKeyShortcut(number);
        }
    }
    
    public void setProcessedLabel(String s){
        this.LabelProcessed.setText("Files processed: " + s);
        this.LabelProcessed.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    
    // optimization: store the generated icon instead of file
    // and create second method which would be private and launch after setting or resizing
    public void setImage(File img){
        if(img == null){
            System.out.println("mw: image was null");
            LabelImage.setIcon(null);
            return;
        }
        
        LabelImage.setText("");
        LabelName.setText("file: " + img.getName());
        currentImage = new ImageIcon(img.getAbsolutePath());
        this.rescale();
    }
    
    private void rescale(){
        int newheight = this.getWidth();
        int newwidth = this.getHeight();
        System.out.println("icn status: " + currentImage.getImageLoadStatus()); //debug info
        

        if (currentImage.getIconWidth() > this.getWidth() - this.MenuPanel.getWidth()) {
            //scale width to fit
            newwidth = this.getWidth() - this.MenuPanel.getWidth();
            //scale height to maintain aspect ratio
            newheight = (newwidth * currentImage.getIconHeight()) / currentImage.getIconWidth();
        }
        
        // then check if we need to scale even with the new height
        if (newheight > this.getHeight() - this.InfoPanel.getHeight()) {
            //scale height to fit instead
            newheight = this.getHeight() - this.InfoPanel.getHeight();
            //scale width to maintain aspect ratio
            newwidth = (newheight * currentImage.getIconWidth()) / currentImage.getIconHeight();
        }
        
        //LabelImage.setIcon(new ImageIcon(dimg));
        LabelImage.setIcon(new ImageIcon(currentImage.getImage().getScaledInstance(
                newwidth-100, newheight-100, Image.SCALE_SMOOTH)));
    }
    
    
    /**
     * obtains value of given ComboBox
     * @param n number 0-9 denoting accessed ComboBox
     * @return string value of given ComboBox
     */
    public String getComboBoxValue(int n){
        switch(n){
            case 0:
                return ComboBox0.getSelectedItem().toString();
            case 1:
                return ComboBox1.getSelectedItem().toString();
            case 2:
                return ComboBox2.getSelectedItem().toString();
            case 3:
                return ComboBox3.getSelectedItem().toString();
            case 4:
                return ComboBox4.getSelectedItem().toString();
            case 5:
                return ComboBox5.getSelectedItem().toString();
            case 6:
                return ComboBox6.getSelectedItem().toString();
            case 7:
                return ComboBox7.getSelectedItem().toString();
            case 8:
                return ComboBox8.getSelectedItem().toString();
            case 9:
                return ComboBox9.getSelectedItem().toString();
            default:
                return null;
                //throw new Exception("invalid number");
        }
    }
    
    public void setComboBoxValue(int n, String s){
        switch(n){
            case 0:
                ComboBox0.setSelectedItem(s);
            case 1:
                ComboBox1.setSelectedItem(s);
            case 2:
                ComboBox2.setSelectedItem(s);
            case 3:
                ComboBox3.setSelectedItem(s);
            case 4:
                ComboBox4.setSelectedItem(s);
            case 5:
                ComboBox5.setSelectedItem(s);
            case 6:
                ComboBox6.setSelectedItem(s);
            case 7:
                ComboBox7.setSelectedItem(s);
            case 8:
                ComboBox8.setSelectedItem(s);
            case 9:
                ComboBox9.setSelectedItem(s);
        }
    }

    
    /**
     * returns current file transfer method
     * @return "c" when radio set to copy or "m" when set to move
     */
    public String getMode(){
        if(CopyRadio.getSelectedObjects() != null){
            return "c";
        }else{
            return "m";
        }
    }
    
    /**
     * sets file transfer method
     * @param s if "c" sets to copy, if "m" sets to move 
     */
    // TODO Add listener waiting for change and if it occurs, should push appropriate action to controllers command
    public void setMode(String s){
        if(s.equals("c")){
            this.CopyRadio.setSelected(true);
            this.MoveRadio.setSelected(false);
        }else{
            this.CopyRadio.setSelected(false);
            this.MoveRadio.setSelected(true);
        }
    }
    
    /**
     * sets image and filename labels
     * @param imgs new image label text
     * @param files new filename label text
     */
    public void setLabels(String imgs, String files){
        LabelImage.setText(imgs);
        LabelName.setText(files);
    }
    
    public void setSrcLabel(String s){
        this.SrcLabel.setText("Src: " + s);
    }
    
    public void setDstLabel(String s){
        this.DstLabel.setText("Dst: " + s);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ModeGroup = new javax.swing.ButtonGroup();
        MenuPanel = new javax.swing.JPanel();
        CopyRadio = new javax.swing.JRadioButton();
        MoveRadio = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        ComboBox0 = new javax.swing.JComboBox<>();
        ComboBox1 = new javax.swing.JComboBox<>();
        ComboBox2 = new javax.swing.JComboBox<>();
        ComboBox3 = new javax.swing.JComboBox<>();
        ComboBox4 = new javax.swing.JComboBox<>();
        ComboBox5 = new javax.swing.JComboBox<>();
        ComboBox6 = new javax.swing.JComboBox<>();
        ComboBox7 = new javax.swing.JComboBox<>();
        ComboBox8 = new javax.swing.JComboBox<>();
        ComboBox9 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        SrcButton = new javax.swing.JButton();
        DstButton = new javax.swing.JButton();
        SrcLabel = new javax.swing.JLabel();
        DstLabel = new javax.swing.JLabel();
        ImagePanel = new javax.swing.JPanel();
        LabelImage = new javax.swing.JLabel();
        InfoPanel = new javax.swing.JPanel();
        LabelName = new javax.swing.JLabel();
        LabelTime = new javax.swing.JLabel();
        LabelProcessed = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ImageTagger");
        setBackground(new java.awt.Color(30, 30, 30));
        setMinimumSize(new java.awt.Dimension(991, 594));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        MenuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ModeGroup.add(CopyRadio);
        CopyRadio.setSelected(true);
        CopyRadio.setToolTipText("Will copy current file to target directory");
        CopyRadio.setLabel("Copy");

        ModeGroup.add(MoveRadio);
        MoveRadio.setToolTipText("Will move current file to target directory");
        MoveRadio.setLabel("Move");

        ComboBox0.setEditable(true);
        ComboBox0.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox1.setEditable(true);
        ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox2.setEditable(true);
        ComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox3.setEditable(true);
        ComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox4.setEditable(true);
        ComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox5.setEditable(true);
        ComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox6.setEditable(true);
        ComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox7.setEditable(true);
        ComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox8.setEditable(true);
        ComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        ComboBox9.setEditable(true);
        ComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nothing", "Skip", "Delete" }));

        jLabel1.setText("0");

        jLabel2.setText("1");

        jLabel3.setText("2");

        jLabel4.setText("3");

        jLabel5.setText("4");

        jLabel6.setText("5");

        jLabel7.setText("6");

        jLabel8.setText("7");

        jLabel9.setText("8");

        jLabel10.setText("9");

        SrcButton.setText("...");
        SrcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SrcButtonActionPerformed(evt);
            }
        });

        DstButton.setText("...");
        DstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DstButtonActionPerformed(evt);
            }
        });

        SrcLabel.setText("Source Folder: not set");

        DstLabel.setText("Target Folder: not set");

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBox0, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DstLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SrcLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SrcButton)
                            .addComponent(DstButton)))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(MoveRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                        .addComponent(CopyRadio))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBox5, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBox9, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboBox8, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboBox7, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SrcButton)
                    .addComponent(SrcLabel))
                .addGap(2, 2, 2)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DstLabel)
                    .addComponent(DstButton))
                .addGap(18, 18, 18)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CopyRadio)
                    .addComponent(MoveRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        ImagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        LabelImage.setText("<no file>");

        javax.swing.GroupLayout ImagePanelLayout = new javax.swing.GroupLayout(ImagePanel);
        ImagePanel.setLayout(ImagePanelLayout);
        ImagePanelLayout.setHorizontalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelImage)
                .addContainerGap(667, Short.MAX_VALUE))
        );
        ImagePanelLayout.setVerticalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        InfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        LabelName.setText("File:");

        LabelTime.setText("Time Spent");

        LabelProcessed.setText("Files processed");

        javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
        InfoPanel.setLayout(InfoPanelLayout);
        InfoPanelLayout.setHorizontalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelProcessed, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        InfoPanelLayout.setVerticalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(LabelName)
                .addComponent(LabelTime)
                .addComponent(LabelProcessed))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(InfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SrcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SrcButtonActionPerformed
        //obtain directory and pass it to controller
        //works
        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Source Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            System.out.println("getSelectedFile() : " 
             +  chooser.getSelectedFile());//debug info
          
            ctrl.setSrcFolder(chooser.getSelectedFile());
        }else {
            System.out.println("No Selection ");
        }      
    }//GEN-LAST:event_SrcButtonActionPerformed

    private void DstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DstButtonActionPerformed
        // TODO modify properly
        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Destination Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            System.out.println("getSelectedFile() : " 
                +  chooser.getSelectedFile()); //debug info
          
            ctrl.setDstFolder(chooser.getSelectedFile());
        }else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_DstButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if(currentImage != null){
            this.rescale();
        }
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        MenuPanel.grabFocus();
    }//GEN-LAST:event_formMouseClicked


    //<editor-fold defaultstate="collapsed" desc=" Variables declarations ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBox0;
    private javax.swing.JComboBox<String> ComboBox1;
    private javax.swing.JComboBox<String> ComboBox2;
    private javax.swing.JComboBox<String> ComboBox3;
    private javax.swing.JComboBox<String> ComboBox4;
    private javax.swing.JComboBox<String> ComboBox5;
    private javax.swing.JComboBox<String> ComboBox6;
    private javax.swing.JComboBox<String> ComboBox7;
    private javax.swing.JComboBox<String> ComboBox8;
    private javax.swing.JComboBox<String> ComboBox9;
    private javax.swing.JRadioButton CopyRadio;
    private javax.swing.JButton DstButton;
    private javax.swing.JLabel DstLabel;
    private javax.swing.JPanel ImagePanel;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JLabel LabelImage;
    private javax.swing.JLabel LabelName;
    private javax.swing.JLabel LabelProcessed;
    private javax.swing.JLabel LabelTime;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.ButtonGroup ModeGroup;
    private javax.swing.JRadioButton MoveRadio;
    private javax.swing.JButton SrcButton;
    private javax.swing.JLabel SrcLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
//</editor-fold>
}
