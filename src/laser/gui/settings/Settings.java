
package laser.gui.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author sdb
 */
public class Settings implements ActionListener
{
    private SettingsView settings;
    private ISettingsListener listener;
    
    public Settings(ISettingsListener listener, boolean editingConnectingData)
    {
        this.listener = listener;
        
        this.settings = new SettingsView();
        this.settings.buttonOK.addActionListener(this);
        this.settings.buttonCancel.addActionListener(this);
        
        this.settings.textFieldIPAddress.setText(listener.getIPAddress());
        this.settings.textFieldPort.setText(listener.getPort()+"");
        this.settings.spinnerNrData.setValue(listener.getNrDatasAverage());
        this.settings.spinnerNrIgnore.setValue(listener.getNrIgnoreAVerage());
        this.settings.spinnerThreshold.setValue(listener.getThresholdValue());
        this.settings.spinnerTolerance.setValue(listener.getToleranceLineExtraction());
        this.settings.spinnerMinReflections.setValue(listener.getMinNrReflections());
        
        this.settings.textFieldIPAddress.setEnabled(editingConnectingData);
        this.settings.textFieldPort.setEnabled(editingConnectingData);
        
        this.settings.setLocationRelativeTo(null);
        this.settings.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals(this.settings.buttonOK.getActionCommand()))
        {
            this.listener.storeSettings(
                    this.settings.textFieldIPAddress.getText(), 
                    Integer.parseInt(this.settings.textFieldPort.getText()), 
                    (int)this.settings.spinnerNrData.getValue(),
                    (int)this.settings.spinnerNrIgnore.getValue(),
                    (int)this.settings.spinnerThreshold.getValue(),
                    (int)this.settings.spinnerTolerance.getValue(),
                    (int)this.settings.spinnerMinReflections.getValue());
            
            this.settings.dispose();
        }
        else if(e.getActionCommand().equals(this.settings.buttonCancel.getActionCommand()))
        {
            this.settings.dispose();
        }
    }
}
