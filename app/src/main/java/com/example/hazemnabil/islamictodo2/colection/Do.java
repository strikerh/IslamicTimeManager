package com.example.hazemnabil.islamictodo2.colection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by hazem.nabil on 4/27/2017.
 */

public class Do {
    static public String to2Digits(int i){
        return  ((i < 10 ? "0" : "") + i);

    }
    static public boolean notNull(Object obj){
        if(obj != null)return true;
        return false;
    }


    static public byte[] getBytes(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }




    public static final int sizeOf(Object object){

        if (object == null)
            return -1;

        // Special output stream use to write the content
        // of an output stream to an internal byte array.
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();

        // Output stream that can write object
        ObjectOutputStream objectOutputStream =
                null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        // Write object and close the output stream
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Get the byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // TODO can the toByteArray() method return a
        // null array ?
        return byteArray == null ? 0 : byteArray.length;


    }

}
