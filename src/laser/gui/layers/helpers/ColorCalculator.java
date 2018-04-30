package laser.gui.layers.helpers;

import laser.gui.layers.ILayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon Bühlmann
 */
public class ColorCalculator implements ILayer
{

    private int max;
    private int min;

    private int lenght;
    private int height;

    private float proportionMaxToLenght;

    private Point position;

    public ColorCalculator()
    {
        this.max = 255;
        this.min = 0;

        this.lenght = 600;
        this.height = 20;

        this.position = new Point(10, 10);

        this.proportionMaxToLenght = (float) max / (float) lenght;
    }

    @Override
    public void draw(Graphics g)
    {
        for (int cnt = 0; cnt < lenght; cnt++)
        {
            try
            {
                int x = (int) (this.proportionMaxToLenght * (float) cnt);
                //System.out.println("Count: " + cnt + " Value: " + x);
                g.setColor(this.calculateColor(x));
                g.drawLine(position.x + cnt, position.y, position.x + cnt, position.y + height);
            }
            catch (Exception ex)
            {
                Logger.getLogger(ColorCalculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Color calculateColor(int value) throws Exception
    {
        int sectorSize = (max - min) / 4;

        int redLimit = min;
        int yellowLimit = sectorSize;
        int greenLimit = yellowLimit + sectorSize;
        int turqouisLimit = greenLimit + sectorSize;
        int blueLimit = max;

        float gradient = (float) 255 / (float) sectorSize;

        int turnedValue = 255 - value;
        
        if (turnedValue >= redLimit && turnedValue < yellowLimit)
        {
            int g = (int) ((turnedValue - redLimit) * gradient);
            return new Color(255, this.checkValueInByteRange(g), 0);
        }
        else if (turnedValue >= yellowLimit && turnedValue < greenLimit)
        {
            int r = 255 + (int) ((turnedValue - yellowLimit) * (gradient * -1));
            return new Color(this.checkValueInByteRange(r), 255, 0);
        }
        else if (turnedValue >= greenLimit && turnedValue < turqouisLimit)
        {
            int b = (int) ((turnedValue - greenLimit) * gradient);
            return new Color(0, 255, this.checkValueInByteRange(b));
        }
        else if (turnedValue >= turqouisLimit && turnedValue <= blueLimit)
        {
            int g = 255 + (int) ((turnedValue - turqouisLimit) * (gradient * -1));
            return new Color(0, this.checkValueInByteRange(g), 255);
        }
        else
        {
            throw new Exception("Value outside of enabled range. Value: " + value + " Turned Value: " + turnedValue);
        }
    }

    private int checkValueInByteRange(int value)
    {
        if (value < 0)
        {
            return 0;
        }
        else if (value > 255)
        {
            return 255;
        }
        else
        {
            return value;
        }
    }
}
