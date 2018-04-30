
package laser.scandatahandling.lineExtraction;

import laser.scandatahandling.coordinates.CoordinatesScandata;
import java.util.List;

/**
 *
 * @author sdb
 */
public class ExtractedLine
{
    private List<CoordinatesScandata> relatedReflections;
    
    public ExtractedLine(List<CoordinatesScandata> relatedReflections)
    {
        this.relatedReflections = relatedReflections;
    }
    
    public CoordinatesScandata getStartReflection()
    {
        return this.relatedReflections.get(0);
    }
    
    public CoordinatesScandata getEndReflection()
    {
        return this.relatedReflections.get(this.relatedReflections.size() - 1);
    }
    
    public List<CoordinatesScandata> getAllRelatedReflections()
    {
        return this.relatedReflections;
    }
}
