package com.kimaskebris.hangman;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Kim on 12/12/2017.
 */

public class ServerHandler extends AsyncTask<Void,Void,Void> {

    private Socket socket;
    private static ObjectOutputStream toServer;
    private static ObjectInputStream fromServer;
    private final int PORT = 5000;
    private final String HOST = "192.168.0.8";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            socket = new Socket(HOST, PORT);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return null;
    }

    public static ObjectOutputStream getToServerStream() {
        return toServer;
    }

    public static ObjectInputStream getFromServerStream() {
        return fromServer;
    }

}
