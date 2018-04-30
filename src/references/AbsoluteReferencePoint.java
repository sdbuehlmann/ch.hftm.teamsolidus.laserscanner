
package references;

/**
 *
 * @author simon.buehlmann
 */
public class AbsoluteReferencePoint extends AReferencePoint
{
    private static AbsoluteReferencePoint instance;

    private AbsoluteReferencePoint()
    {
        super(0, 0, 0);
    }
    
    public static AbsoluteReferencePoint getInstance()
    {
        if(instance == null)
        {
            instance = new AbsoluteReferencePoint();
        }
        
        return instance;
    }

    @Override
    public int getAbsolutX()
    {
        return super.getX();
    }

    @Override
    public int getAbsolutY()
    {
        return super.getY();
    }

    @Override
    public int getAbsolutAngle()
    {
        return super.getAngle();
    }
}
