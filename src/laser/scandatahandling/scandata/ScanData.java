
package laser.scandatahandling.scandata;

import laser.scandatahandling.IScanData;
import laser.scandatahandling.IScanReflectionData;
import laser.scandatahandling.IScannerData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Bühlmann
 */
public class ScanData implements IScanData
{
    private List<IScanReflectionData> scanMeasurementDatas;
    private IScannerData scannerData;
    
    public ScanData(IScannerData scannerData)
    {
        this.scannerData = scannerData;
    }
    
    @Override
    public IScannerData getScannerData()
    {
        return this.scannerData;
    }

    @Override
    public List<IScanReflectionData> getScanMeasurementData()
    {
        if(this.scanMeasurementDatas == null)
        {
            this.scanMeasurementDatas = new ArrayList<>();
        }
        
        return this.scanMeasurementDatas;
    }
    
}
