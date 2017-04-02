/**
 * Created by Japo on 30/03/2017.
 */

package com.redes.japo.updmulticast;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;


public class CommunicationManager extends Observable implements Runnable {
    private static final String TAG = "CommunicationManager";
    private static CommunicationManager ref;
    // Default destination address - emulator host IP address
    public static final String DEFAULT_ADDRESS = "10.0.2.2";
    // A multicast IP address
    public static final String MULTI_GROUP_ADDRESS = "224.2.2.5";
    // Default destination port
    public static  final int DEFAULT_PORT = 5000;


    private MulticastSocket ms;
    private DatagramSocket ds;
    private InetAddress group;
    private boolean running;
    private boolean connecting;
    private boolean reset;
    private boolean errorNotified;

    private CommunicationManager() {


        running = true;
        connecting = true;
        reset = false;
        errorNotified = false;
        Log.d(TAG, "[ CommunicationManager Instance Built ]");
        attemptConnection();
    }

    //Instance Creation
    public static CommunicationManager getInstance() {
        if (ref == null) {
            ref = new CommunicationManager();
            Thread runner = new Thread(ref);
            runner.start();
        }
        return ref;
    }

    @Override
    public void run() {
        Log.e(TAG, "Comms Started");
        while (running) {
            if (connecting) {
                if (reset) {
                    if (ms != null) {
                        ms.close();
                        Log.d(TAG, "[Communication was reset]");
                    }
                    reset = false;
                }
                connecting = !attemptConnection();
            } else {
                if (ms != null) {
                    Log.e(TAG,"Socket Active, Awaiting for inbound");
                    DatagramPacket p = receiveMessage();
                    Log.e(TAG,"Got Something");
                    if (p != null) {

                        Object recivedObject = deserialize(p.getData());
                        Log.e(TAG,"Objeto Recibido");
                        // Transform packet bytes to understandable data
                        //String message = new String(p.getData(), 0, p.getLength());

                        // Notify the observers that new data has arrived and pass the data to them
                        setChanged();
                        notifyObservers(recivedObject);
                        clearChanged();
                    }
                }
            }
        }
        ms.close();
    }



    private boolean attemptConnection() {
        try {
            ms = new MulticastSocket(DEFAULT_PORT);
            group = InetAddress.getByName(MULTI_GROUP_ADDRESS);
            ms.setBroadcast(true);
            ms.joinGroup(group);
            Log.e(TAG,"New MulticastSocket Created");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "[ Error starting Communication]");
            return false;
        }
    }

    /*****************************************************************
     * Send and Recive Methods
     *****************************************************************/

    public void sendMessage(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ms != null) {

                    try {

                        if(message != null) {
                            // Validate destAddress

                            byte[] data = serialize(message);
                            DatagramPacket packet = new DatagramPacket(data, data.length, group, DEFAULT_PORT);
                            System.out.println("Sending data to " + group.getHostAddress() + ":" + DEFAULT_PORT);
                            ms.send(packet);
                            System.out.println("Data was sent");
                        }

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    setChanged();
                    notifyObservers("Not connected");
                    clearChanged();
                }
            }
        }).start();

    }

    public DatagramPacket receiveMessage() {
        try {
            Log.d(TAG, "[ Recived Message Called]");
            byte[] buffer = new byte[1024];
            Log.d(TAG, "[ Buffer Created]");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            Log.d(TAG, "[DatagramPacket created]");
            Log.d(TAG, "[ Gonna try and recive packet]");
            ms.receive(packet);
            Log.d(TAG, "[ Recived  The Data]");
            return packet;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    /***********************************************************************************************
     * Serialize ande Deserialize Methods
    ************************************************************************************************/


    private byte[] serialize(Object data) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baots = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baots);
            oos.writeObject(data);
            bytes = baots.toByteArray();

            // Close streams
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private Object deserialize(byte[] bytes) {
        Object data = null;
        try {
            ByteArrayInputStream baits = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(baits);
            data = ois.readObject();

            // close streams
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

}