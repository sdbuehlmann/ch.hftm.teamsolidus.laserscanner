
package laser.gui.layers;

import laser.gui.layers.helpers.ColorCalculator;
import laser.gui.references.GUIReference;
import laser.scandatahandling.coordinates.CoordinatesScandata;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdb
 */
public class ReflectionDistanceAndRSSILayer extends ReflectionDistanceLayer
{
    private ColorCalculator colorBar;
    
    public ReflectionDistanceAndRSSILayer(GUIReference guiReference, ColorCalculator colorBar)
    {
        super(guiReference);
        
        this.colorBar = colorBar;
    }

    @Override
    protected Color getColorOfReflectionPoint(CoordinatesScandata scandata)
    {
        try
        {
            return this.colorBar.calculateColor(scandata.getReflection());
        }
        catch (Exception ex)
        {
            Logger.getLogger(ReflectionDistanceAndRSSILayer.class.getName()).log(Level.SEVERE, null, ex);
            return Color.black;
        }
    }
}
