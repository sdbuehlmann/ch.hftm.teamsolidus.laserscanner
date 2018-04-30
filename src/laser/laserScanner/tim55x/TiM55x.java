package laser.laserScanner.tim55x;

import laser.laserScanner.ILaserscanner;
import laser.laserScanner.ILaserscannerListener;
import laser.laserScanner.ILaserscannerOperator;
import laser.laserScanner.ILaserscannerOperator.State;
import laser.laserScanner.LaserscannerStateException;
import laser.laserScanner.tim55x.communication.CommunicationNotRunningException;
import laser.laserScanner.tim55x.communication.CommunicationManager;
import laser.laserScanner.tim55x.communication.ICommunicationListener;
import laser.laserScanner.tim55x.interpretation.IInterpreterListener;
import laser.laserScanner.tim55x.interpretation.Interpreter;
import laser.laserScanner.tim55x.interpretation.basic.LaserData;
import java.io.IOException;

/**
 *
 * @author Simon Bühlmann
 */
public class TiM55x implements ICommunicationListener, IInterpreterListener, ILaserscanner
{

    private final ILaserscannerListener listener;
    private final ILaserscannerOperator operator;

    private final CommunicationManager communicationManager;
    private final Interpreter interpreter;

    private State state;

    public TiM55x(ILaserscannerListener listener, ILaserscannerOperator operator, String ipAddress, int port) throws IOException
    {
        this.listener = listener;
        this.operator = operator;

        this.changeState(State.INITIALIZING);
        
        this.communicationManager = new CommunicationManager(this, ipAddress, port); // Can throw a exception
        this.interpreter = new Interpreter(this);
    }

    @Override
    public void newData(byte value)
    {
        this.interpreter.interpret(value);
    }

    @Override
    public void communicationFailed(IOException exception)
    {
        this.operator.errorOccured();
    }

    @Override
    public void event(Event event)
    {
        switch (event)
        {
            case CONTINUOUS_MEAS_STARTED:
                this.changeState(State.CONTINUOUSLY_MEASURING);
                break;

            case CONTINUOUS_MEAS_STOPPED:
                this.changeState(State.STANDBY);
                break;
        }
    }

    @Override
    public void mesurementData(LaserData data, RequestType type)
    {
        // change to supported format
        this.listener.newMeasurementData(new Tim55xData(data));
    }

    @Override
    public void runSingleMeas() throws IOException, LaserscannerStateException, CommunicationNotRunningException
    {
        if (this.state == State.STANDBY)
        {
            this.communicationManager.sendCommand(
                    Translator.translateToCommand(
                            Vocabulary.RUN_SINGLE_MEASUREMENT));
        } else
        {
            throw new LaserscannerStateException();
        }
    }

    @Override
    public void startContinuousMeas() throws IOException, CommunicationNotRunningException, LaserscannerStateException
    {
        if (this.state == State.STANDBY)
        {
            this.communicationManager.sendCommand(
                    Translator.translateToCommand(
                            Vocabulary.START_CONTINUOUS_MEASUREMENT));
        } else
        {
            throw new LaserscannerStateException();
        }
    }

    @Override
    public void stopContinuousMeas() throws IOException, CommunicationNotRunningException, LaserscannerStateException
    {
        if (this.state == State.CONTINUOUSLY_MEASURING)
        {
            this.communicationManager.sendCommand(
                    Translator.translateToCommand(
                            Vocabulary.STOP_CONTINUOUS_MEASUREMENT));
        } else
        {
            throw new LaserscannerStateException();
        }
    }

    @Override
    public void communicationStarted()
    {
        this.changeState(State.STANDBY);
    }

    @Override
    public void communicationStoped()
    {
        this.changeState(State.STOPED);
    }

    // private methods
    private void changeState(State state)
    {
        this.state = state;
        this.operator.newStateActice(state);
    }

    @Override
    public void stopLaser()
    {
        this.communicationManager.stop();
    }
}
