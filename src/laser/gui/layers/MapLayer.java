
package laser.gui.layers;

import laser.gui.references.GUIReference;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import references.AbsoluteReferencePoint;

/**
 *
 * @author Simon Bühlmann
 */
public class MapLayer implements ILayer
{
    private GUIReference guiReference;
    
    private int diameter;
    
    public MapLayer(GUIReference guiReference)
    {
        this.guiReference = guiReference;
        this.diameter = 200;
    }
    
    @Override
    public void draw(Graphics g)
    {
        this.drawCirclePixelDiameter(g, 200);
        this.drawCirclePixelDiameter(g, 400);
        this.drawCirclePixelDiameter(g, 600);
        this.drawCirclePixelDiameter(g, 800);
        this.drawCirclePixelDiameter(g, 1000);
    }
    
    // private methods
    /**
     * 
     * @param g
     * @param diameter unscaled!
     */
    private void drawCircle(Graphics g, int diameter)
    {
        Point guiReferencePoint = guiReference.calculatePointInGUI(AbsoluteReferencePoint.getInstance(), new Point(0, 0));
        int tempDiameter = guiReference.calculateDistanceMMToGUIPx(diameter);
        
        g.setColor(Color.BLACK);
        g.drawOval(guiReferencePoint.x - tempDiameter/2, 
                guiReferencePoint.y - tempDiameter/2, 
                tempDiameter, 
                tempDiameter);
        int radius = diameter/2;
        
        g.drawString(radius + "mm", guiReferencePoint.x, guiReferencePoint.y - tempDiameter/2);
    }
    
    private void drawCirclePixelDiameter(Graphics g, int diameter)
    {
        Point guiReferencePoint = guiReference.calculatePointInGUI(AbsoluteReferencePoint.getInstance(), new Point(0, 0));
        
        g.setColor(Color.BLACK);
        g.drawOval(guiReferencePoint.x - diameter/2, 
                guiReferencePoint.y - diameter/2, 
                diameter, 
                diameter);
        int radius = guiReference.calculateGUIPxToDistanceMM(diameter/2);
        
        g.drawString(radius + "mm", guiReferencePoint.x, guiReferencePoint.y - diameter/2);
    }
}
