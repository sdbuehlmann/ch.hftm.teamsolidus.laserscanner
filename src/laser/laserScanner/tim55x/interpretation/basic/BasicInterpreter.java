
package laser.laserScanner.tim55x.interpretation.basic;

import laser.laserScanner.tim55x.Vocabulary;

/**
 *
 * @author Simon Bühlmann
 */
public class BasicInterpreter
{
    private LaserData laserData;
    private IBasicInterpreterListener listener;
    
    public BasicInterpreter(IBasicInterpreterListener listener)
    {
        this.listener = listener;
    }
    
    public void interpret(byte value)
    {
        switch(value)
        {
            case Vocabulary.DATA_START:
                // init new object for datas
                this.laserData = new LaserData();
                break;
                
            case Vocabulary.DATA_END:
                // release data object
                this.listener.completeLaserData(laserData);
                this.laserData = null;
                break;
                
            case Vocabulary.DATA_SEPERATE:
                if(this.laserData != null)
                {
                    this.laserData.newSegment();
                }
                else
                {
                    // ilegal data from laser
                    // log!
                }
                break;
                
            default:
                // data for the active segment
                if(this.laserData != null)
                {
                    this.laserData.addData(value);
                }
                else
                {
                    // ilegal data from laser
                    // log!
                }
                break;
        }
    }
}
