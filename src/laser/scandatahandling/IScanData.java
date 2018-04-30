
package laser.scandatahandling;

import java.util.List;

/**
 *
 * @author Simon Bühlmann
 */
public interface IScanData
{
    public IScannerData getScannerData();
    public List<IScanReflectionData> getScanMeasurementData();
}
