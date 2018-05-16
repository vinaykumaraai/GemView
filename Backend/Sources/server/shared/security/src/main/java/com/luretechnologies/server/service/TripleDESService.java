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
package com.luretechnologies.server.service;

/**
 *
 *
 * @author developer
 */
public interface TripleDESService {

    /**
     * Sets the key from a Hex encoded byte string
     *
     * @param key The key as hex encoded bytes
     * @throws Exception
     */
    public void setKey(String key) throws Exception;

    /**
     * Sets the key from a ASCII encoded byte string
     *
     * @param key The key formatted as ASCII
     * @throws Exception
     */
    public void setKey(byte[] key) throws Exception;

    /**
     * Sets the initialization vectors from a Hex encoded byte string
     *
     * @param iv The initialization vector
     * @throws Exception
     */
    public void setIV(String iv) throws Exception;

    /**
     * Sets the initialization vectors from a ASCII encoded byte string
     *
     * @param iv The initialization vector
     * @throws Exception
     */
    public void setIV(byte[] iv) throws Exception;

    /**
     * Sets the transformation
     *
     * @param padding The transformation string i.e. "DESede/CBC/PKCS5Padding",
     * "DESede/CBC/NoPadding"
     */
    public void setTransformation(String padding);

    /**
     * Encrypts a Hex encoded byte string and returns same
     *
     * @param data The data to be encrypted
     * @return Hex encoded encrypted byte string
     * @throws Exception
     */
    public String encryptData(String data) throws Exception;

    /**
     * Encrypts a ASCII encoded byte string and returns same
     *
     * @param data The data to be encrypted
     * @return ASCII encoded encrypted byte string
     * @throws Exception
     */
    public byte[] encryptData(byte[] data) throws Exception;

    /**
     *
     * Decrypts a Hex encoded byte string and returns same
     *
     * @param data The data to be encrypted
     * @return Hex encoded decrypted byte string
     * @throws Exception
     */
    public String decryptData(String data) throws Exception;

    /**
     * Decrypts a ASCII encoded byte string and returns same
     *
     * @param data The data to be encrypted
     * @return ASCII encoded decrypted byte string
     * @throws Exception
     */
    public byte[] decryptData(byte[] data) throws Exception;

    /**
     * Removes PKCS5-compatible padding from a string
     *
     * @param data The padded string
     * @return The unpadded string
     */
    public String removePKCS5Padding(String data);

    /**
     * Removes PKCS5-compatible padding from a byte array
     *
     * @param data The padded byte array
     * @return The unpadded byte array
     */
    public byte[] removePKCS5Padding(byte[] data);
}
