
package laser.laserScanner.tim55x.communication;

import java.io.IOException;

/**
 *
 * @author Simon Bühlmann
 */
public interface ICommunicationListener
{
    public void newData(byte value);
    
    public void communicationFailed(IOException exception);
    
    public void communicationStarted();
    
    public void communicationStoped();
}
