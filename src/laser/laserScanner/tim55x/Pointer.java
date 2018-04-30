
package laser.laserScanner.tim55x;

/**
 *
 * @author Simon Bühlmann
 */
public class Pointer
{
    // generic
    public final static int COMMAND_TYPE = 0;
    public final static int COMMAND = 1;
    
    // continuous measurement
    public final static int CONT_MEASUREMENT_START_STOP = 2;
    
    // measurement data
    public final static int FIRMWARE_VERSION = 2;
    public final static int DEVICE_NR = 3;
    public final static int SICK_SERIAL_NR = 4;
    public final static int TELEGRAM_COUNTER = 6;
    public final static int SCAN_COUNTER = 7;
    public final static int DEVICE_POWER_ON_DURATION = 8;
    public final static int DEVICE_TRANSMISSION_DURATION = 9;
    public final static int SCAN_FREQUENCY = 16;
    public final static int SCALING_FACTOR_DISTANCE_DATAS = 21;
    public final static int SCAN_START_ANGLE = 23;
    public final static int NR_DISTANCE_DATAS = 25;
    public final static int START_DISTANCE_DATAS = 26;
    
    // rssi pointer are relative to the start of the distance datas
    public final static int NR_RSSI_DATAS = 6;
    public final static int START_RSSI_DATAS = 7;
}
