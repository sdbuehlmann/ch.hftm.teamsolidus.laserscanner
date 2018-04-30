
package laser.gui.model;

import laser.gui.settings.DefaultSettingsManager;
import laser.gui.settings.ISettingsListener;
import laser.laserScanner.ILaserscanner;
import laser.laserScanner.ILaserscannerListener;
import laser.laserScanner.ILaserscannerMeasurementData;
import laser.laserScanner.ILaserscannerOperator;
import laser.laserScanner.tim55x.TiM55x;
import laser.scandatahandling.IScanData;
import laser.scandatahandling.IScanReflectionData;
import laser.scandatahandling.coordinates.CoordinatesCalculater;
import laser.scandatahandling.coordinates.CoordinatesScandata;
import laser.scandatahandling.filters.AverageFilter;
import laser.scandatahandling.filters.MinNumberReflectionsFilter;
import laser.scandatahandling.filters.ThresholdValueFilter;
import laser.scandatahandling.lineExtraction.ExtractedLine;
import laser.scandatahandling.lineExtraction.LineExtracter;
import laser.scandatahandling.scandata.ScandataFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon Bühlmann
 */
public class LaserscannerFascade implements ILaserscannerOperator, ILaserscannerListener, ISettingsListener
{
    private ILaserscanner laserscanner;
    private String laserscannerIPAddress; // TEMP !!!!
    private int laserscannerPort; // TEMP !!!!!!
    
    private CoordinatesCalculater coordinatesCalculater;
    
    // filters
    private AverageFilter averageFilter;
    private ThresholdValueFilter thresholdValueFilter;
    
    private LineExtracter lineExtracter;
    private MinNumberReflectionsFilter minNumberReflectionsFilter;
    
    private DefaultSettingsManager defaultSettingsManager;
    
    private boolean connected;
    
    
    
    private List<CoordinatesScandata> lastPrintedReflectionData;
    private List<ILaserscannerFascadeListener> listeners;
    
    public LaserscannerFascade()
    {
        this.defaultSettingsManager = new DefaultSettingsManager();
        
        this.connected = false;
        this.coordinatesCalculater = new CoordinatesCalculater();
        this.listeners = new ArrayList<>();
        
        this.laserscannerIPAddress = this.defaultSettingsManager.getIpAddressLaserscanner();
        this.laserscannerPort = this.defaultSettingsManager.getPortLaserscanner();
        
        this.averageFilter = new AverageFilter(this.defaultSettingsManager.getBufferSizeAverageFilter(), true);
        this.thresholdValueFilter = new ThresholdValueFilter(this.defaultSettingsManager.getThresholdValue());
        
        this.lineExtracter = new LineExtracter(this.defaultSettingsManager.getToleranceLineExtracter());
        this.minNumberReflectionsFilter = new MinNumberReflectionsFilter(this.defaultSettingsManager.getMinNrReflectionsLineFilter());
    }
    
    public void connect() throws LaserscannerException
    {
        try
         {
             this.laserscanner = new TiM55x(this, this, this.laserscannerIPAddress, this.laserscannerPort);
             this.connected = true;
             this.informListenersLaserConnectionChanged(this.connected);
         }
         catch (IOException ex)
         {
             throw new LaserscannerException();
         }
    }
    
    public void disconnect()
    {
        if(this.laserscanner != null)
        {
            this.laserscanner.stopLaser();
            this.laserscanner = null;
            this.connected = false;
            this.informListenersLaserConnectionChanged(this.connected);
        }
    }
    
    public boolean isConnected()
    {
        return this.connected;
    }

    @Override
    public void newStateActice(State state)
    {
        
    }

    @Override
    public void errorOccured()
    {
       
    }

    @Override
    public void newMeasurementData(ILaserscannerMeasurementData data)
    {
        List<CoordinatesScandata> coordinates = new ArrayList<>();
        
        IScanData scanData = ScandataFactory.create(data);
        
        // filter data
        List<IScanReflectionData> calculatedAverage = this.averageFilter.calculateAverage(scanData);
        try
        {
            calculatedAverage = this.thresholdValueFilter.filter(calculatedAverage);
        }
        catch (Exception ex)
        {
            Logger.getLogger(LaserscannerFascade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // calculate coordinates
        for(IScanReflectionData measurementData : calculatedAverage)
        {
            coordinates.add(CoordinatesCalculater.calculateCoordinates(measurementData));
        }
        
        this.lastPrintedReflectionData = coordinates;
        
        this.informListenersNewLaserData(coordinates);
    }
    
    public void runSingle() throws LaserscannerException
    {
         try
         {
             this.laserscanner.runSingleMeas();
         }
         catch (Exception ex)
         {
             throw new LaserscannerException();
         }
    }
    public List<CoordinatesScandata> getData()
    {
        return lastPrintedReflectionData;
    }
    
    public void startCont()
    {
        try
        {
            this.laserscanner.startContinuousMeas();
        }
        catch (Exception ex)
        {
            Logger.getLogger(LaserscannerFascade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopCont()
    {
        try
        {
            this.laserscanner.stopContinuousMeas();
        }
        catch (Exception ex)
        {
            Logger.getLogger(LaserscannerFascade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addListener(ILaserscannerFascadeListener listener)
    {
        this.listeners.add(listener);
    }
    
    public void removeListener(ILaserscannerFascadeListener listener)
    {
        this.listeners.remove(listener);
    }
    
    public void calculateStraights()
    {
        if(this.lastPrintedReflectionData != null)
        {
            List<ExtractedLine> extractVectors = this.lineExtracter.extractLines(this.lastPrintedReflectionData); // extrahiert Linien aus gebufferten Reflektionen
            extractVectors = this.minNumberReflectionsFilter.filter(extractVectors); // filtert extrahierte Linien auf Grund der Anzahl der Reflektionen, welche die Linie definieren
            
            this.informListenersNewExtractedStraightsData(extractVectors);
        }
    }
    
    // private methods
    private synchronized void informListenersNewLaserData(List<CoordinatesScandata> dataSet)
    {
        for(ILaserscannerFascadeListener listener : this.listeners)
        {
            listener.newMeasurementData(dataSet);
        }
    }
    
    private synchronized void informListenersNewExtractedStraightsData(List<ExtractedLine> straights)
    {
        for(ILaserscannerFascadeListener listener : this.listeners)
        {
            listener.newExtractedStraightsData(straights);
        }
    }
    
    private synchronized void informListenersLaserConnectionChanged(boolean isConnected)
    {
        for(ILaserscannerFascadeListener listener : this.listeners)
        {
            listener.laserConnectionChanged(isConnected);
        }
    }

    @Override
    public synchronized void storeSettings(String ipAddress, int port, int nrDatasAverage, int nrIgnoreAverage, int thresholdValue, int toleranceLineExtraction, int minNrReflectionsLineExtraction)
    {
        if(!this.laserscannerIPAddress.equals(ipAddress) ||
                this.laserscannerPort != port)
        {
            this.laserscannerIPAddress = ipAddress;
            this.laserscannerPort = port;
        }
        
        this.averageFilter = new AverageFilter(nrDatasAverage, true); // TEMP!!!!!!
        
        if(this.thresholdValueFilter.getThresholdValue() != thresholdValue)
        {
           this.thresholdValueFilter = new ThresholdValueFilter(thresholdValue);
        }
        
        this.lineExtracter.setToleranceMax(toleranceLineExtraction);
        this.minNumberReflectionsFilter.setMinNrReflections(minNrReflectionsLineExtraction);
    }

    @Override
    public String getIPAddress()
    {
        return this.laserscannerIPAddress;
    }

    @Override
    public int getPort()
    {
        return this.laserscannerPort;
    }

    @Override
    public int getNrDatasAverage()
    {
        return this.averageFilter.getDataBufferSize();
    }

    @Override
    public int getNrIgnoreAVerage()
    {
        return 1; // TEMP!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Override
    public int getToleranceLineExtraction()
    {
        return this.lineExtracter.getToleranceMax();
    }

    @Override
    public int getMinNrReflections()
    {
        return this.minNumberReflectionsFilter.getMinNrReflections();
    }

    @Override
    public int getThresholdValue()
    {
        return this.thresholdValueFilter.getThresholdValue();
    }
    
    public static void main(String[] args)
    {
        LaserscannerFascade lsf = new LaserscannerFascade();
        lsf.calculateStraights();
    }
}
