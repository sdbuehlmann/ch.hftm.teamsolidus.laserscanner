
package laser.laserScanner.tim55x.interpretation.basic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Bühlmann
 */
public class LaserData
{
    private List<Byte> dataList;
    private List<Integer> segmentList;
    
    public LaserData()
    {
        this.dataList = new ArrayList<>();
        this.segmentList = new ArrayList<>();
        this.segmentList.add(0);
    }

  
    
    public synchronized void addData(byte data)
    {
        this.dataList.add(data);
    }
    
    public synchronized void newSegment()
    {
        this.segmentList.add(this.dataList.size());
    }
    
    // getter
    public int getSegmentLenght(int segmentIndex)
    {
        if(segmentIndex >= this.segmentList.size()-1)
        {
            // spezial case: last segment in list
            int temp = this.dataList.size() - this.segmentList.get(this.segmentList.size()-1);
            return temp;
        }
        {
            int temp = this.segmentList.get(segmentIndex + 1) - this.segmentList.get(segmentIndex);// Differenz des Inhalts von einem Pointer zum nächsten
            return temp;
        }
    }
    
    /**
     * 
     * @param pointer points to segment
     * @param index points to data byte in the correpoinding segment
     * @return 
     */
    public Byte getValue(int pointer, int index)
    {
        return this.dataList.get(this.segmentList.get(pointer) + index);
    }
}
