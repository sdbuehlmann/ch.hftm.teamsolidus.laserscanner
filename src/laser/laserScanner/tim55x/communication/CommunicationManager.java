/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laser.laserScanner.tim55x.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Simon Bühlmann
 */
public class CommunicationManager implements Runnable
{

    //Variablen
    private boolean running;

    //Objekte
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ICommunicationListener listener;
    private Thread thread;
    private BufferedReader buff;

    public CommunicationManager(ICommunicationListener listener, String ipAddress, int port) throws IOException
    {
        this.listener = listener;
        this.start(ipAddress, port);
    }

    public void sendCommand(byte[] command) throws CommunicationNotRunningException, IOException
    {
        if (this.running)
        {
            outputStream.write(command);
            outputStream.flush();
        } else
        {
            throw new CommunicationNotRunningException();
        }
    }

    public void stop()
    {
        this.running = false;
    }

    private synchronized void start(String ipAddress, int port) throws IOException
    {
        // open socket
        this.socket = new Socket(ipAddress, port);
        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
        this.buff = new BufferedReader(new InputStreamReader(this.inputStream));

        // starting com threat
        this.running = true;

        this.thread = new Thread(this);
        this.thread.setName("Laser_Communication");

        this.thread.start();
    }

    @Override
    public void run()
    {
        this.listener.communicationStarted();

        while (running)
        {
            try
            {
                this.listener.newData((byte) buff.read());
            } 
            catch (IOException ex)
            {
                // read values failed. Stop communication
                this.running = false;
                this.listener.communicationFailed(ex);
            }
        }

        this.listener.communicationStoped();
    }
}
