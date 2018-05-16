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
package com.luretechnologies.server.service.impl;

import com.luretechnologies.server.security.util.Utils;
import com.luretechnologies.server.service.TripleDESService;
import java.security.Security;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author developer
 */
@Service
public class TripleDESServiceImpl implements TripleDESService {

    private byte[] gKey;
    private byte[] gIV;
    private String gTransformation;

    @Override
    public void setKey(String key) throws Exception {
        setKey(Utils.toByteArray(key));
    }

    @Override
    public void setKey(byte[] key) throws Exception {
        gKey = new byte[24];

        // if 16-byte key passed, transform to 24-byte key
        if (key.length == 16) {
            System.arraycopy(key, 0, gKey, 0, 16);
            System.arraycopy(key, 0, gKey, 16, 8);
        } else if (key.length == 24) {
            System.arraycopy(key, 0, gKey, 0, 24);
        } else {
            gKey = null;
            throw new Exception("Invalid key size. Must be 24 bytes.");
        }
    }

    @Override
    public void setIV(String iv) throws Exception {
        setIV(Utils.toByteArray(iv));
    }

    @Override
    public void setIV(byte[] iv) throws Exception {
        if (iv.length != 8) {
            throw new Exception("Invalid IV. Must be 8 bytes.");
        }

        gIV = new byte[8];
    }

    @Override
    public void setTransformation(String padding) {
        gTransformation = padding;
    }

    @Override
    public String encryptData(String data) throws Exception {
        return Utils.toHexString(doFinal(true, Utils.toByteArray(data)));
    }

    @Override
    public byte[] encryptData(byte[] data) throws Exception {
        return doFinal(true, data);
    }

    @Override
    public String decryptData(String data) throws Exception {
        return Utils.toHexString(doFinal(false, Utils.toByteArray(data)));
    }

    @Override
    public byte[] decryptData(byte[] data) throws Exception {
        return doFinal(false, data);
    }

    @Override
    public String removePKCS5Padding(String data) {
        return Utils.toHexString(removePKCS5Padding(Utils.toByteArray(data)));
    }

    @Override
    public byte[] removePKCS5Padding(byte[] data) {
        byte[] newData = new byte[data.length - data[data.length - 1]];
        System.arraycopy(data, 0, newData, 0, newData.length);
        return newData;
    }

    /**
     * Performs the encryption or decryption process
     *
     * @param encrypt True for encryption, False for decryption
     * @param data The data to be processed
     * @return The resulting data
     * @throws Exception
     */
    private byte[] doFinal(boolean encrypt, byte[] data) throws Exception {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());

            KeySpec spec = new DESedeKeySpec(gKey);
            SecretKeyFactory keyfac = SecretKeyFactory.getInstance("DESede");
            SecretKey key = keyfac.generateSecret(spec);
            IvParameterSpec iv = new IvParameterSpec(gIV);
            Cipher enc = Cipher.getInstance(gTransformation);

            if (encrypt) {
                enc.init(Cipher.ENCRYPT_MODE, key, iv);
            } else {
                enc.init(Cipher.DECRYPT_MODE, key, iv);
            }

            byte[] processed = enc.doFinal(data);

            return processed;

        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

}
