
package laser.scandatahandling.filters;

import laser.scandatahandling.IScanReflectionData;
import java.util.List;

/**
 *
 * @author sdb
 */
public interface IScanReflectionsFilter
{
    public  List<IScanReflectionData> filter(List<IScanReflectionData> data) throws Exception;
}
