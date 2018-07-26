package ru.ittrans.zp.client.def;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 21.10.2010
 * Time: 17:24:05
 * To change this template use File | Settings | File Templates.
 */
public class DefFile
{
    public static synchronized byte[] getBytesFromFile(File file) throws IOException
    {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE)
        {
            System.out.println("File is to larger");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
        {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length)
        {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static synchronized void writeBytesToFile(byte[] bytes,
                                                     String path,
                                                     boolean append) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(path, append);
        BufferedOutputStream output = new BufferedOutputStream(fos);

        output.write(bytes, 0, bytes.length);
        output.flush();
        output.close();
    }

    public static synchronized void writeBytesToFile(byte[] bytes, String path) throws IOException
    {
        writeBytesToFile(bytes, path, false);
    }


}
