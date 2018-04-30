
package references;

/**
 *
 * @author simon.buehlmann
 */
public abstract class AReferencePoint
{
    private int x, y, angle;
    
    public AReferencePoint()
    {
        
    }
    
    public AReferencePoint(int x, int y, int angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    
    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getAngle()
    {
        return this.angle;
    }
    
    public void setAngle(int angle)
    {
        this.angle = angle;
    }
    
    public abstract int getAbsolutX();
    public abstract int getAbsolutY();
    public abstract int getAbsolutAngle();
    
    protected static int calculateAngle(int angle)
    {
        int tempAngle = angle;
        
        while(tempAngle >= 360 || tempAngle < 0)//Angle is not in the 360 Degres Zone
        {
            if(tempAngle >= 360)
            {
                tempAngle = tempAngle - 360;
            }
            if(tempAngle < 0)
            {
                tempAngle = tempAngle + 360;
            }
        }
        
        return tempAngle;
    }
}
