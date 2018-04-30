
package laser.laserScanner.tim55x;

import laser.laserScanner.tim55x.interpretation.basic.LaserData;
import java.nio.charset.Charset;

/**
 *
 * @author Simon Bühlmann
 */
public class Translator
{
    /**
     * Interpretiert einen normalen String und gibt ihn als Byte-Array im
     * ASCII-Zeichensatz aus. Ergänzt zudem den String mit einem vorangestellten
     * STX und einem ETX am Schluss. Kann verwendet werden um einen Befehl für
     * den Scanner in dies passende Form zu transformieren.
     *
     * @param interpret String der interpretiert werden soll
     * @return ASCII Byte-Array des Strings mit angehängetm & nachfolgenden "STX"
     * rsp. "ETX"
     */
    public static byte[] translateToCommand(String interpret)
    {
        byte[] asciiTemp = interpret.getBytes(Charset.forName("US-ASCII"));
        byte[] ascii = new byte[asciiTemp.length + 2];

        for (int n = 1; n < (ascii.length - 1); n++)//Alle ausser erstes & letztes Byte
        {
            ascii[n] = asciiTemp[n - 1];
        }

        ascii[0] = Vocabulary.DATA_START;
        ascii[ascii.length - 1] = Vocabulary.DATA_END;

        return ascii;
    }
    
    public static char interpretAsChar(byte data)
    {
        return (char)data;
    }
    
    public static int interpretAsInt(int pointer, LaserData data)
    {
        int tempInt = 0;
        int dezimalValency = 1;//Wertigkeit der Stellen (Hexadez)
        char currentChar;//Repräsentiert das ausgelesene Byte an der position des currentDataPointer
        
        for (int cnt = data.getSegmentLenght(pointer) - 1; cnt >= 0; cnt--)//Beginnen mit letztem Feld in Segment
        {
            currentChar = (char)((byte) data.getValue(pointer, cnt));
            tempInt = tempInt + Character.getNumericValue(currentChar) * dezimalValency;
            dezimalValency = dezimalValency * 16;
        }
        
        return tempInt;
    }
    
    public static long interpretAsLong(int pointer, LaserData data)
    {
        long tempLong = 0;
        int dezimalValency = 1;//Wertigkeit der Stellen (Hexadez)
        char currentChar;//Repräsentiert das ausgelesene Byte an der position des currentDataPointer
        
        for (int cnt = data.getSegmentLenght(pointer) - 1; cnt >= 0; cnt--)//Beginnen mit letztem Feld in Segment
        {
            currentChar = (char)((byte) data.getValue(pointer, cnt));
            tempLong = tempLong + Character.getNumericValue(currentChar) * dezimalValency;
            dezimalValency = dezimalValency * 16;
        }
        
        return tempLong;
    }
    
    public static String interpretAsString(int pointer, LaserData data)
    {
        String tempType = "";
        
        for(int cnt = 0; cnt < data.getSegmentLenght(pointer); cnt++)
        {
            tempType = tempType + (char)((byte)data.getValue(pointer, cnt));
        }
        
        return tempType;
    }
}
