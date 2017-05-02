package com.example.ryan.lifttracker.socketio;

/**
 * Created by Andrey on 3/19/2017.
 */

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by andrey on 12/19/16.
 */

// Singleton containing the SocketIO connection.
public class SocketIO extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://128.173.239.242/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //this method is called from various Android components that want to use SocketIO
    public Socket getSocket() {
        return mSocket;
    }

}
