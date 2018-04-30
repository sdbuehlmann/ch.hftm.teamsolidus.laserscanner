
package laser.gui.layers.helpers;

import laser.gui.references.GUIReference;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import references.AReferencePoint;

/**
 *
 * @author simon.buehlmann
 */
public class ReferencePointView
{
    private static int DIAMETER = 20;

    public static void drawReferencePoint(GUIReference guiReference, AReferencePoint drawingReference, Graphics g)
    {        
        Point calculatePointInGUI = guiReference.calculatePointInGUI(drawingReference, new Point(0,0));
        int x = calculatePointInGUI.x - DIAMETER/2;
        int y = calculatePointInGUI.y - DIAMETER/2;

        g.setColor(Color.BLACK);
        g.fillOval(x, y, DIAMETER, DIAMETER);
        
        g.setColor(Color.PINK);
        System.out.println(g.getColor());
        g.fillArc(x+2, y+2, DIAMETER-4, DIAMETER-4, 0, 90);
        g.fillArc(x+2, y+2, DIAMETER-4, DIAMETER-4, 180, 90);
    }
    
    public static void drawAbsoluteReferencePoint(GUIReference guiReference, AReferencePoint drawingReference, Graphics g)
    {        
        Point calculatePointInGUI = guiReference.calculatePointInGUI(drawingReference, new Point(0,0));
        int x = calculatePointInGUI.x - DIAMETER/2;
        int y = calculatePointInGUI.y - DIAMETER/2;
        
        g.setColor(Color.BLACK);
        g.fillOval(x-2, y-2, DIAMETER+4, DIAMETER+4);
        
        g.setColor(Color.WHITE);
        g.fillOval(x-1, y-1, DIAMETER+2, DIAMETER+2);
        
        g.setColor(Color.BLACK);
        g.fillOval(x, y, DIAMETER, DIAMETER);
        
        g.setColor(Color.WHITE);
        g.fillArc(x+2, y+2, DIAMETER-4, DIAMETER-4, 0, 90);
        g.fillArc(x+2, y+2, DIAMETER-4, DIAMETER-4, 180, 90);
    }
}
