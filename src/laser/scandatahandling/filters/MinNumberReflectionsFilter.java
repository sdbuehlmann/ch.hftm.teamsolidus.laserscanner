
package laser.scandatahandling.filters;

import laser.scandatahandling.lineExtraction.ExtractedLine;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sdb
 */
public class MinNumberReflectionsFilter
{
    
    private int minNrReflections;
    
    public MinNumberReflectionsFilter(int minNrReflections)
    {
        this.minNrReflections = minNrReflections;
    }
    
    public List<ExtractedLine> filter(List<ExtractedLine> extractedLines)
    {
        List<ExtractedLine> filteredLines = new ArrayList<>();
        
        for(ExtractedLine extractedLine : extractedLines)
        {
            if(extractedLine.getAllRelatedReflections().size() >= this.minNrReflections)
            {
                filteredLines.add(extractedLine);
            }
            else
            {
                /*System.out.println("Extracted line filtered. Reason: Not enough related reflections." 
                        + " Min reflections: " 
                        + this.minNrReflections 
                        + " Related reflections: " 
                        + extractedLine.getAllRelatedReflections().size());*/
            }
        }
        
        return filteredLines;
    }
    
    // getter & setter

    public int getMinNrReflections()
    {
        return minNrReflections;
    }

    public synchronized void setMinNrReflections(int minNrReflections)
    {
        this.minNrReflections = minNrReflections;
    }
    
}
