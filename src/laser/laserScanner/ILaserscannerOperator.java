
package laser.laserScanner;

/**
 *
 * @author Simon Bühlmann
 */
public interface ILaserscannerOperator
{
    public void newStateActice(State state);
    
    /**
     * Error occured in the running laser thread.
     */
    public void errorOccured();
    
    public enum State
    {
        INITIALIZING,
        STANDBY,
        WAITING_FOR_LASER,
        CONTINUOUSLY_MEASURING,
        STOPED
    }
}
