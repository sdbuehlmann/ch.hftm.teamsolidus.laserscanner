
package laser.laserScanner.tim55x.interpretation;

import laser.laserScanner.tim55x.interpretation.basic.LaserData;

/**
 *
 * @author Simon Bühlmann
 */
public interface IInterpreterListener
{
    public void event(Event event);
    
    public void mesurementData(LaserData data, RequestType type);
    
    public enum Event
    {
        CONTINUOUS_MEAS_STARTED,
        CONTINUOUS_MEAS_STOPPED
    }
    
    public enum RequestType
    {
        SIMPLE,
        EVENT
    }
}
