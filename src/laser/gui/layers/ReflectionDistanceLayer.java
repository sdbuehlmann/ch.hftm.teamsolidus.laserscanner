package laser.gui.layers;

import laser.gui.layers.helpers.ReferencePointView;
import laser.gui.references.GUIReference;
import references.AbsoluteReferencePoint;
import laser.scandatahandling.coordinates.CoordinatesScandata;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Simon Bühlmann
 */
public class ReflectionDistanceLayer
{

    // Constantes
    private final int DIAMETER_REFERENCE_POINT = 3;

    // objects
    private List<CoordinatesScandata> scandatas;
    private GUIReference guiReference;

    // Constructor
    public ReflectionDistanceLayer(GUIReference guiReference)
    {
        this.guiReference = guiReference;
    }

    public void setScandataSet(List<CoordinatesScandata> scandatas)
    {
        this.scandatas = scandatas;
    }
    
    public void drawMeasuredResult(Graphics g)
    {
        ReferencePointView.drawAbsoluteReferencePoint(guiReference, AbsoluteReferencePoint.getInstance(), g);
        
        if (this.scandatas != null)
        {
            for (CoordinatesScandata scandata : scandatas)
            {
                this.drawReflectionPoint(scandata, g, guiReference);
            }
        }
    }
    
    protected Color getColorOfReflectionPoint(CoordinatesScandata scandata)
    {
        return Color.blue;
    }
    
    // private methods
    private void drawReflectionPoint(CoordinatesScandata scandata, Graphics g, GUIReference guiReference)
    {
        Point newPoint = guiReference.calculatePointInGUI(AbsoluteReferencePoint.getInstance(), scandata.getPoint());

        g.setColor(this.getColorOfReflectionPoint(scandata));
        g.fillOval(newPoint.x - DIAMETER_REFERENCE_POINT / 2, newPoint.y - DIAMETER_REFERENCE_POINT / 2, DIAMETER_REFERENCE_POINT, DIAMETER_REFERENCE_POINT);
    }
}
