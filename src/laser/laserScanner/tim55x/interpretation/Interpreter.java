
package laser.laserScanner.tim55x.interpretation;

import laser.laserScanner.tim55x.Pointer;
import laser.laserScanner.tim55x.Translator;
import laser.laserScanner.tim55x.interpretation.basic.LaserData;
import laser.laserScanner.tim55x.Vocabulary;
import laser.laserScanner.tim55x.interpretation.basic.BasicInterpreter;
import laser.laserScanner.tim55x.interpretation.basic.IBasicInterpreterListener;

/**
 *
 * @author Simon Bühlmann
 */
public class Interpreter implements IBasicInterpreterListener
{
    private BasicInterpreter basicInterpreter;
    private IInterpreterListener listener;
    
    public Interpreter(IInterpreterListener listener)
    {
        this.listener = listener;
        this.basicInterpreter = new BasicInterpreter(this);
    }
    
    public void interpret(byte value)
    {
        this.basicInterpreter.interpret(value);
    }

    @Override
    public void completeLaserData(LaserData data)
    {
        String temp = Translator.interpretAsString(Pointer.COMMAND_TYPE ,data);
        switch(temp)
        {
            case Vocabulary.COMMAND_TYPE_IN_EVENT_REQUSET_RESPONSE:
                String startStop = Translator.interpretAsString(Pointer.CONT_MEASUREMENT_START_STOP ,data);
                switch(startStop)
                {
                    case Vocabulary.START_CONT_MEAS_VALUE:
                        this.listener.event(IInterpreterListener.Event.CONTINUOUS_MEAS_STARTED);
                        break;
                        
                    case Vocabulary.STOP_CONT_MEAS_VALUE:
                        this.listener.event(IInterpreterListener.Event.CONTINUOUS_MEAS_STOPPED);
                        break;
                        
                    default:
                        break;
                }
                break;
                
            case Vocabulary.COMMAND_TYPE_IN_MEASURED_VALUES_EVENT:
                this.listener.mesurementData(data, IInterpreterListener.RequestType.EVENT);
                break;
                
            case Vocabulary.COMMAND_TYPE_IN_MEASURED_VALUES_SIMPLE:
                this.listener.mesurementData(data, IInterpreterListener.RequestType.SIMPLE);
                break;
                
            default:
                // unknown data type
                break;
        }
    }
}
