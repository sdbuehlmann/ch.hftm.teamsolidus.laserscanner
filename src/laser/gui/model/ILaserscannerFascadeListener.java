
package laser.gui.model;

import laser.scandatahandling.coordinates.CoordinatesScandata;
import laser.scandatahandling.lineExtraction.ExtractedLine;
import laser.scandatahandling.lineExtraction.Straight;
import java.util.List;

/**
 *
 * @author Simon Bühlmann
 */
public interface ILaserscannerFascadeListener
{
    public void newMeasurementData(List<CoordinatesScandata> datas);
    
    public void newExtractedStraightsData(List<ExtractedLine> datas);
    
    public void laserConnectionChanged(boolean isConnected);
}
