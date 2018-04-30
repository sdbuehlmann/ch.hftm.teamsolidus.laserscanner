
package laser.laserScanner;

import laser.laserScanner.tim55x.communication.CommunicationNotRunningException;
import java.io.IOException;

/**
 *
 * @author simon.buehlmann
 */
public interface ILaserscanner
{
    public void runSingleMeas() throws IOException, CommunicationNotRunningException, LaserscannerStateException;
    
    public void startContinuousMeas() throws IOException, CommunicationNotRunningException, LaserscannerStateException;
    
    public void stopContinuousMeas() throws IOException, CommunicationNotRunningException, LaserscannerStateException;
    
    public void stopLaser();
}
