
package laser.laserScanner.tim55x;

/**
 *
 * @author Simon Bühlmann
 */
public class Vocabulary
{
    // command types
    public final static String COMMAND_TYPE_OUT_EVENT_REQUEST = "sEN";
    public final static String COMMAND_TYPE_IN_EVENT_REQUSET_RESPONSE = "sEA";
    
    public final static String COMMAND_TYPE_OUT_SIMPLE_REQUEST = "sRN";
    
    public final static String COMMAND_TYPE_IN_MEASURED_VALUES_EVENT = "sSN";
    public final static String COMMAND_TYPE_IN_MEASURED_VALUES_SIMPLE = "sRA";
    
    public final static String START_CONT_MEAS_VALUE = "1";
    public final static String STOP_CONT_MEAS_VALUE = "0";
    
    // Commands
    public final static String START_CONTINUOUS_MEASUREMENT = "sEN LMDscandata 1";
    public final static String STOP_CONTINUOUS_MEASUREMENT = "sEN LMDscandata 0";
    public final static String RUN_SINGLE_MEASUREMENT = "sRN LMDscandata";
    
    // Character to seperate
    public final static byte DATA_START = 2;//ASCII Zeichen STX
    public final static byte DATA_END = 3;//ASCII Zeichen ETX
    public final static byte DATA_SEPERATE = 32;//ASCII Zeichen SPACE
}
