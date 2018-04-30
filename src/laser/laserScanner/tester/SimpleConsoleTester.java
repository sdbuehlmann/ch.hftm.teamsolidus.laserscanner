package laser.laserScanner.tester;

import laser.laserScanner.ILaserscanner;
import laser.laserScanner.ILaserscannerListener;
import laser.laserScanner.ILaserscannerMeasurementData;
import laser.laserScanner.ILaserscannerOperator;
import laser.laserScanner.LaserscannerStateException;
import laser.laserScanner.tim55x.TiM55x;
import laser.laserScanner.tim55x.communication.CommunicationNotRunningException;
import java.io.IOException;

/**
 *
 * @author Simon Bühlmann
 */
public class SimpleConsoleTester implements ILaserscannerListener, ILaserscannerOperator
{
    private ILaserscanner tim;
    private int cnt;
    
    public SimpleConsoleTester()
    {
        cnt = 0;
        
        try
        {
            tim = new TiM55x(this, this, "192.168.137.2", 2112);
            System.out.println("Laser found and connected");
            
            //tim.startContinuousMeas();
            tim.runSingleMeas();
        } 
        catch (CommunicationNotRunningException ex)
        {
            System.out.println("Lasercommunication not running");
        } 
        catch (LaserscannerStateException ex)
        {
            System.out.println("Laser in invalid state");
        } 
        catch (IOException ex)
        {
            System.out.println("Can not reach laser. Exception: " + ex.getMessage());
        }
    }

    @Override
    public void newMeasurementData(ILaserscannerMeasurementData data)
    {
        cnt++;
        
        System.out.println("Measurement data arrived!");
        System.out.println("Scanfrequency: " + data.getScanFrequency());
        System.out.println("Scan startangle: " + data.getScanStartAngle());
        System.out.println("Scan nr distance datas: " + data.getNrDistanceDatas());
        System.out.println("Distance datas scale factor: " + data.getScalingFactorDistanceDatas());
        System.out.println("Scan nr rssi datas: " + data.getNrRSSIValues());
        
        for(int cnt = 0; cnt < data.getNrDistanceDatas() && cnt < data.getNrRSSIValues(); cnt++)
        {
            System.out.println(cnt + ". Distance:  " + data.getDistanceValue(cnt) + "      RSSI:  " + data.getRSSIValue(cnt));
        }
        
        if(cnt > 50)
        {
            this.tim.stopLaser();
        }
    }

    @Override
    public void errorOccured()
    {
        System.out.println("Laser ERROR");
    }
    
    public static void main(String[] args)
    {
        SimpleConsoleTester simpleConsoleTester = new SimpleConsoleTester();
    }

    @Override
    public void newStateActice(State state)
    {
        System.out.println("New laser state active: " + state);
    }
}
