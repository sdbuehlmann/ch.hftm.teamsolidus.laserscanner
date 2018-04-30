
package laser.scandatahandling.coordinates;

import java.awt.Point;

/**
 *
 * @author Simon Bühlmann
 */
public class CoordinatesScandata
{
    private Point point;
    private int reflection;
    
    public CoordinatesScandata(int x, int y, int reflection)
    {
        this.point = new Point(x, y);
        this.reflection = reflection;
    }
    
    // getter
    public Point getPoint()
    {
        return point;
    }

    public int getReflection()
    {
        return reflection;
    }
}
