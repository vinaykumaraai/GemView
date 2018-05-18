/**
 * COPYRIGHT @ Lure Technologies, LLC.
 * ALL RIGHTS RESERVED
 *
 * Developed by Lure Technologies, LLC. (www.luretechnologies.com)
 *
 * Copyright in the whole and every part of this software program belongs to
 * Lure Technologies, LLC (“Lure”).  It may not be used, sold, licensed,
 * transferred, copied or reproduced in whole or in part in any manner or
 * form other than in accordance with and subject to the terms of a written
 * license from Lure or with the prior written consent of Lure or as
 * permitted by applicable law.
 *
 * This software program contains confidential and proprietary information and
 * must not be disclosed, in whole or in part, to any person or organization
 * without the prior written consent of Lure.  If you are neither the
 * intended recipient, nor an agent, employee, nor independent contractor
 * responsible for delivering this message to the intended recipient, you are
 * prohibited from copying, disclosing, distributing, disseminating, and/or
 * using the information in this email in any manner. If you have received
 * this message in error, please advise us immediately at
 * legal@luretechnologies.com by return email and then delete the message from your
 * computer and all other records (whether electronic, hard copy, or
 * otherwise).
 *
 * Any copies or reproductions of this software program (in whole or in part)
 * made by any method must also include a copy of this legend.
 *
 * Inquiries should be made to legal@luretechnologies.com
 *
 */
package com.luretechnologies.server.security.hsm;

import com.luretechnologies.server.security.util.Utils;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author developer
 */
public class HSMChannel {

    private static final Logger LOGGER = LoggerFactory.getLogger(HSMChannel.class);
    private static List<HSMUnit> hsmUnits;
    private static int hsmUnitIndex;
    Socket socket = null;
    InputStream fromHSM = null;
    PrintStream toHSM = null;

    HSMChannel(List<HSMUnit> units) {
        hsmUnits = units;
    }

    /**
     *
     * @param message
     * @return
     * @throws Exception
     */
    public byte[] exchange(byte[] message) throws Exception {
        return exchange(null, message);
    }

    /**
     *
     * @param title
     * @param message
     * @return
     * @throws Exception
     */
    public byte[] exchange(String title, byte[] message) throws Exception {
        int responseLength;
        byte[] buffer, sizeHeader = new byte[2];

        if (socket == null) {
            connect();
        }

        try {
            toHSM.write(message);
            toHSM.flush();
            Utils.hexPrettyPrint(message, String.format("HSM Request: %s", title));

            fromHSM.read(sizeHeader, 0, 2);
            responseLength = shortFromByteArray(sizeHeader);

            if (responseLength < 8) {
                throw new Exception("ResponseLength was shorter than expected: " + responseLength);
            }

            buffer = new byte[responseLength];

            fromHSM.read(buffer, 0, responseLength);

            String debugMessage = Utils.hexPrettyPrintToString(buffer, String.format("HSM Response: %s", title));

            if (buffer[6] != 48 && buffer[7] != 48) {
                throw new Exception(debugMessage);
            }

            System.out.println(debugMessage);
            return buffer;

        } catch (Exception e) {
            LOGGER.error("Error in exchange: ", e.getMessage());
            throw new Exception(e);
        } finally {
            disconnect();
        }
    }

    private int getHSMIndex() {
        synchronized (HSMChannel.class) {
            if (hsmUnitIndex == hsmUnits.size() - 1) {
                hsmUnitIndex = 0;
            } else {
                hsmUnitIndex++;
            }
            return hsmUnitIndex;
        }
    }

    /**
     *
     * @throws Exception
     */
    public void connect() throws Exception {

        int loops = 0;

        if (socket != null) {
            return;
        }

        while (loops++ < hsmUnits.size()) {

            int hsmIndex = getHSMIndex();

            String hsmInfo = "HSM #" + hsmIndex + " at [" + hsmUnits.get(hsmIndex).getAddress() + ":" + hsmUnits.get(hsmIndex).getPort() + "] with timeout = " + hsmUnits.get(hsmIndex).getTimeout();
            try {
                LOGGER.info("Connecting to " + hsmInfo);
                socket = new Socket();
                socket.connect(new InetSocketAddress(hsmUnits.get(hsmIndex).getAddress(), hsmUnits.get(hsmIndex).getPort()), hsmUnits.get(hsmIndex).getTimeout());
                fromHSM = socket.getInputStream();
                toHSM = new PrintStream(socket.getOutputStream(), false, "ISO-8859-1");
                System.out.println("Connected to " + hsmInfo);
                return;
            } catch (Exception e) {
                LOGGER.error("Error Connecting to " + hsmInfo, e);
                disconnect();
            }
        }

        throw new Exception("Could not connect to any HSM.");
    }

    /**
     *
     */
    public void disconnect() {
        LOGGER.info("Disconnecting HSM socket");
        try {
            if (toHSM != null) {
                toHSM.close();
                toHSM = null;
            }
            if (fromHSM != null) {
                fromHSM.close();
                fromHSM = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private int shortFromByteArray(byte[] buffer) {
        int mergedValue, val1, val2;

        val1 = buffer[0] << 8;
        val2 = (int) ((short) buffer[1] & 0x00FF);
        mergedValue = val1 + val2;

        return mergedValue;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
