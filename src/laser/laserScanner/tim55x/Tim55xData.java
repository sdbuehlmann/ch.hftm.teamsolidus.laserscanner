
package laser.laserScanner.tim55x;

import laser.laserScanner.ILaserscannerMeasurementData;
import laser.laserScanner.tim55x.interpretation.basic.LaserData;

/**
 *
 * @author Simon Bühlmann
 */
public class Tim55xData implements ILaserscannerMeasurementData
{
    private LaserData data;
    
    public Tim55xData(LaserData data)
    {
        this.data = data;
    }

    @Override
    public int getFirmwareVersion()
    {
        return Translator.interpretAsInt(Pointer.FIRMWARE_VERSION, data);
    }

    @Override
    public int getDeviceNr()
    {
        return Translator.interpretAsInt(Pointer.DEVICE_NR, data);
    }

    @Override
    public long getSickSerialNr()
    {
        return Translator.interpretAsLong(Pointer.SICK_SERIAL_NR, data);
    }

    @Override
    public int getTelegrammCounter()
    {
        return Translator.interpretAsInt(Pointer.TELEGRAM_COUNTER, data);
    }

    @Override
    public long getDevicePowerONDuration()
    {
        return Translator.interpretAsLong(Pointer.DEVICE_POWER_ON_DURATION, data);
    }

    @Override
    public long getDeviceTransmissionDuration()
    {
        return Translator.interpretAsLong(Pointer.DEVICE_TRANSMISSION_DURATION, data);
    }

    @Override
    public long getScanFrequency()
    {
        return Translator.interpretAsLong(Pointer.SCAN_FREQUENCY, data);
    }

    @Override
    public float getScalingFactorDistanceDatas()
    {
        String scalingFactorAsString = Translator.interpretAsString(Pointer.SCALING_FACTOR_DISTANCE_DATAS, data);
        
        int decimal = Integer.parseInt(scalingFactorAsString, 16);
        
        return Float.intBitsToFloat(decimal);
    }

    @Override
    public long getScanStartAngle()
    {
        return Translator.interpretAsLong(Pointer.SCAN_START_ANGLE, data);
    }

    @Override
    public int getNrDistanceDatas()
    {
        return Translator.interpretAsInt(Pointer.NR_DISTANCE_DATAS, data);
    }

    @Override
    public int getDistanceValue(int index)
    {
        return Translator.interpretAsInt(Pointer.START_DISTANCE_DATAS + index, data);
    }

    @Override
    public int[] getDistanceValues(int startIndex, int endIndex)
    {
        int[] temp = new int[endIndex - startIndex];
        for(int countFor = 0; countFor < temp.length; countFor++)
        {
            temp[countFor] = this.getDistanceValue(startIndex + countFor);
        }
        return temp;
    }

    @Override
    public int[] getAllDistanceValues()
    {
        return getDistanceValues(0, this.getNrDistanceDatas()-1);
    }
    
    @Override
    public int getNrRSSIValues()
    {
        return Translator.interpretAsInt(this.calculateAbsoluteRSSIPointer(Pointer.NR_RSSI_DATAS), data);
    }

    @Override
    public int getRSSIValue(int index)
    {
        return Translator.interpretAsInt(this.calculateAbsoluteRSSIPointer(Pointer.START_RSSI_DATAS + index), data);
    }

    @Override
    public int[] getRSSIValues(int startIndex, int endIndex)
    {
        int[] temp = new int[endIndex - startIndex];
        for(int countFor = 0; countFor < temp.length; countFor++)
        {
            temp[countFor] = this.getRSSIValue(startIndex + countFor);
        }
        return temp;
    }

    @Override
    public int[] getAllRSSIValues()
    {
        return getRSSIValues(0, this.getNrRSSIValues()-1);
    }
    
    // private methods
    private int calculateAbsoluteRSSIPointer(int relativePointer)
    {
        return Pointer.START_DISTANCE_DATAS + this.getNrDistanceDatas() + relativePointer;
    }

    @Override
    public int getScanAngleIncrement()
    {
        return 10000; // allways the same in the tim55x
    }
}
