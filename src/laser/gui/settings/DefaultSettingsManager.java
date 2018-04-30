
package laser.gui.settings;

/**
 *
 * @author sdb
 */
public class DefaultSettingsManager
{
    private String ipAddressLaserscanner;
    private int portLaserscanner;
    
    private int bufferSizeAverageFilter;
    private int nrIgnoreAverageFilter;
    
    private int thresholdValue;
    
    private int toleranceLineExtracter;
    private int minNrReflectionsLineFilter;
    
    public DefaultSettingsManager()
    {
        this.ipAddressLaserscanner = "192.168.137.2";
        this.portLaserscanner = 2112;
        
        this.bufferSizeAverageFilter = 10;
        this.nrIgnoreAverageFilter = 1;
        
        this.thresholdValue = 5;
        
        this.toleranceLineExtracter = 10;
        this.minNrReflectionsLineFilter = 5;
    }

    public String getIpAddressLaserscanner()
    {
        return ipAddressLaserscanner;
    }

    public int getPortLaserscanner()
    {
        return portLaserscanner;
    }

    public int getBufferSizeAverageFilter()
    {
        return bufferSizeAverageFilter;
    }

    public int getNrIgnoreAverageFilter()
    {
        return nrIgnoreAverageFilter;
    }

    public int getThresholdValue()
    {
        return thresholdValue;
    }

    public int getToleranceLineExtracter()
    {
        return toleranceLineExtracter;
    }

    public int getMinNrReflectionsLineFilter()
    {
        return minNrReflectionsLineFilter;
    }
}
