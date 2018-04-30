package laser.gui;

import laser.gui.settings.Settings;
import laser.gui.layers.LayerArea;
import laser.gui.model.ILaserscannerFascadeListener;
import laser.gui.model.LaserscannerException;
import laser.gui.model.LaserscannerFascade;
import laser.gui.operator.OperatorGroup;
import laser.scandatahandling.coordinates.CoordinatesScandata;
import laser.scandatahandling.lineExtraction.ExtractedLine;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Simon Bühlmann
 */
public class GraphicalLaserscannerTester extends JFrame implements ILaserscannerFascadeListener, ActionListener
{

    // modell
    public  LaserscannerFascade laserscanner;

    // gui elements
    private  LayerArea layerArea;

    private OperatorGroup manager;
    private OperatorGroup laserhandling;
    private OperatorGroup lineExtraction;
    private OperatorGroup maphandling;

    private JSplitPane horizontalSplit;
    
    private int zoomStep;
    private double zoomFactor;

    // action commands
    private final static String ACTION_SETTINGS = "settings";
    private final static String ACTION_CONNECT = "connect";
    private final static String ACTION_DISCONNECT = "disconnect";

    private final static String ACTION_RUN_SINGLE = "run single";
    private final static String ACTION_START = "start";
    private final static String ACTION_STOP = "stop";

    private final static String ACTION_EXTRACT_LINES = "extract lines";

    private final static String ACTION_ZOOM_PLUS = "zoom +";
    private final static String ACTION_ZOOM_MINUS = "zoom -";

    public GraphicalLaserscannerTester() throws Exception
    {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.laserscanner = new LaserscannerFascade();
        this.laserscanner.addListener(this);
        this.setTitle("Laserscanner Manager");
        this.setSize(800, 500);

        // init gui objects
        this.horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.layerArea = new LayerArea();

        JPanel leftSplitPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // init operator groups
        this.manager = new OperatorGroup("Manager");
        this.manager.addButton(ACTION_SETTINGS, this);
        this.manager.addButton(ACTION_CONNECT, this);
        this.manager.addButton(ACTION_DISCONNECT, this);
        menuPanel.add(this.manager);

        this.laserhandling = new OperatorGroup("laserhandling");
        this.laserhandling.addButton(ACTION_RUN_SINGLE, this);
        this.laserhandling.addButton(ACTION_START, this);
        this.laserhandling.addButton(ACTION_STOP, this);
        menuPanel.add(this.laserhandling);

        this.lineExtraction = new OperatorGroup("line extraction");
        this.lineExtraction.addButton(ACTION_EXTRACT_LINES, this);
        menuPanel.add(this.lineExtraction);

        this.maphandling = new OperatorGroup("map handling");
        this.maphandling.addButton(ACTION_ZOOM_PLUS, this);
        this.maphandling.addButton(ACTION_ZOOM_MINUS, this);
        menuPanel.add(this.maphandling);

        leftSplitPanel.add(menuPanel, BorderLayout.PAGE_START);

        this.horizontalSplit.setLeftComponent(leftSplitPanel);
        this.horizontalSplit.setRightComponent(this.layerArea);

        this.add(horizontalSplit);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        // init other objects
        new Settings(this.laserscanner, true);
        this.zoomStep = 200;
        this.zoomFactor = 0.2;
        this.laserConnectionChanged(false);
    }

    @Override
    public void newMeasurementData(List<CoordinatesScandata> datas)
    {
        this.layerArea.getMeasurementDataLayer().setScandataSet(datas);
        this.layerArea.repaint();
    }

    @Override
    public void laserConnectionChanged(boolean isConnected)
    {
        this.manager.setIsEnabledButton(ACTION_CONNECT, !isConnected);
        this.manager.setIsEnabledButton(ACTION_DISCONNECT, isConnected);
        
        this.laserhandling.setIsEnabledAllButtons(isConnected);
    }

    @Override
    public void newExtractedStraightsData(List<ExtractedLine> datas)
    {
        this.layerArea.getStraightLayer().setStraights(datas);
        this.layerArea.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case ACTION_SETTINGS:
                new Settings(this.laserscanner, !this.laserscanner.isConnected());
                break;

            case ACTION_CONNECT:
                try
                {
                    this.laserscanner.connect();
                }
                catch (LaserscannerException ex)
                {
                    JOptionPane.showMessageDialog(null, "Can not connect", "Connection error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(GraphicalLaserscannerTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case ACTION_DISCONNECT:
                this.laserscanner.disconnect();
                break;

            case ACTION_RUN_SINGLE:
                try
                {
                    this.laserscanner.runSingle();
                }
                catch (LaserscannerException ex)
                {
                    Logger.getLogger(GraphicalLaserscannerTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
                
            case ACTION_START:
                this.laserscanner.startCont();
                break;
                
            case ACTION_STOP:
                this.laserscanner.stopCont();
                break;
                
            case ACTION_EXTRACT_LINES:
                this.laserscanner.calculateStraights();
                break;
                
            case ACTION_ZOOM_PLUS:
                this.layerArea.getGUIReference().setScale((int)(((float)this.layerArea.getGUIReference().getGuiScale())*2));
                this.layerArea.repaint();
                break;
                
            case ACTION_ZOOM_MINUS:
                this.layerArea.getGUIReference().setScale((int)(((float)this.layerArea.getGUIReference().getGuiScale())/2));
                this.layerArea.repaint();
                break;
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        GraphicalLaserscannerTester graphicalLaserscannerTester = new GraphicalLaserscannerTester();
    }
}
