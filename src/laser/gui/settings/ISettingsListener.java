
package laser.gui.settings;

/**
 *
 * @author sdb
 */
public interface ISettingsListener
{
    public void storeSettings(
            String ipAddress, 
            int port,
            int nrDatasAverage,
            int nrIgnoreAverage,
            int thresholdValue,
            int toleranceLineExtraction,
            int minNrReflectionsLineExtraction);
    
    public String getIPAddress();
    public int getPort();
    public int getNrDatasAverage();
    public int getNrIgnoreAVerage();
    public int getThresholdValue();
    public int getToleranceLineExtraction();
    public int getMinNrReflections();
}
