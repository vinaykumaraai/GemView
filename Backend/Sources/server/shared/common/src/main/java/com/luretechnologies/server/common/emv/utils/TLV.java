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
package com.luretechnologies.server.common.emv.utils;

import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 *
 * @author developer
 */
public class TLV {

    private EMVTag tag;
    private String hexLength;
    private String hexValue;
    private final byte[] value;

    /**
     *
     */
    protected final List<TLV> tlvChilds;

    /**
     * Creates constructed TLV
     *
     * @param tag tag
     * @param list set of nested TLVs
     */
    public TLV(EMVTag tag, List<TLV> list) {
        this.tag = tag;
        this.tlvChilds = list;
        this.value = null;
    }

    /**
     *
     * @param tag
     * @param list
     * @throws DecoderException
     */
    public TLV(String tag, List<TLV> list) throws DecoderException {
        this(new EMVTag(tag), list);
    }

    /**
     * Creates primitive TLV
     *
     * @param tag tag
     * @param value value as byte[]
     */
    public TLV(EMVTag tag, byte[] value) {
        this.tag = tag;
        this.value = value;
        tlvChilds = null;
    }

    /**
     *
     * @param tag
     * @param value
     * @throws DecoderException
     */
    public TLV(String tag, byte[] value) throws DecoderException {
        this(new EMVTag(tag), value);
    }

    /**
     *
     * @param tag
     * @param value
     * @throws DecoderException
     */
    public TLV(EMVTag tag, String value) throws DecoderException {
        this(tag, Hex.decodeHex(value.toCharArray()));
    }

    /**
     *
     * @param tag
     * @param value
     * @throws DecoderException
     */
    public TLV(String tag, String value) throws DecoderException {
        this(new EMVTag(tag), Hex.decodeHex(value.toCharArray()));
    }

    /**
     *
     * @param tag
     * @param hexLength
     * @param hexValue
     * @throws DecoderException
     */
    public TLV(String tag, String hexLength, String hexValue) throws DecoderException {
        this.tag = new EMVTag(tag);
        this.hexValue = hexValue;
        this.hexLength = hexLength;
        this.value = null;
        this.tlvChilds = null;
    }

    /**
     *
     * @return
     */
    public boolean isConstructed() {
        return tag.isConstructed();
    }

    /**
     *
     * @return
     */
    public EMVTag getTag() {
        return tag;
    }

    private int getTotalLengthBytes() {
        if (hexLength != null) {
            return calculateBytesCountForLength(hexLength.length() / 2);
        }
        if (!isConstructed()) {
            return calculateBytesCountForLength(value.length);
        }
        String hexTlvChild = "";
        for (TLV tlv : tlvChilds) {
            hexTlvChild += tlv.getHexTLV();
        }
        return calculateBytesCountForLength(hexTlvChild.length() / 2);
    }

    private int getTotalLength() {
        if (hexValue != null) {
            return hexValue.length() / 2;
        }
        if (!isConstructed()) {
            return value.length;
        }
        int totalLength = 0;
        for (TLV tlv : tlvChilds) {
            totalLength += tlv.getTag().bytes.length + tlv.getTotalLengthBytes() + tlv.getTotalLength();
        }
        return totalLength;
    }

    /**
     *
     * @return
     */
    public String getHexLength() {
        if (hexLength != null) {
            return hexLength;
        }

        int length = getTotalLength();

        byte[] lengthBuffer = new byte[calculateBytesCountForLength(length)];

        if (lengthBuffer.length == 1) {
            lengthBuffer[0] = (byte) length;

        } else if (lengthBuffer.length == 2) {
            lengthBuffer[0] = (byte) 0x81;
            lengthBuffer[1] = (byte) length;

        } else if (lengthBuffer.length == 3) {
            lengthBuffer[0] = (byte) 0x82;
            lengthBuffer[1] = (byte) (length / 0x100);
            lengthBuffer[2] = (byte) (length % 0x100);

        } else if (lengthBuffer.length == 4) {
            lengthBuffer[0] = (byte) 0x83;
            lengthBuffer[1] = (byte) (length / 0x10000);
            lengthBuffer[2] = (byte) (length / 0x100);
            lengthBuffer[3] = (byte) (length % 0x100);
        } else {
            throw new IllegalStateException("length [" + length + "] out of range (0x1000000)");
        }
        hexLength = Hex.encodeHexString(lengthBuffer);
        return hexLength;
    }

    /**
     *
     * @return
     */
    public String getHexValue() {
        if (hexValue != null) {
            return hexValue;
        }
        if (!isConstructed()) {
            hexValue = Hex.encodeHexString(value);
        } else {
            hexValue = "";
            for (TLV tlv : tlvChilds) {
                hexValue += tlv.getHexTLV();
            }
        }
        return hexValue;
    }

    /**
     *
     * @return
     */
    public String getHexTLV() {
        if (hexLength != null && hexValue != null) {
            return (getTag().getHexName() + hexLength + hexValue).toUpperCase();
        }

        if (!isConstructed()) {
            return (getTag().getHexName() + getHexLength() + getHexValue()).toUpperCase();
        }
        String totalHexTlv = getTag().getHexName() + getHexLength();
        for (TLV tlv : tlvChilds) {
            totalHexTlv += tlv.getHexTLV();
        }
        return totalHexTlv.toUpperCase();
    }

    private int calculateBytesCountForLength(int length) {
        if (length < 0x80) {
            return 1;
        }
        if (length < 0x100) {
            return 2;
        }
        if (length < 0x10000) {
            return 3;
        }
        if (length < 0x1000000) {
            return 4;
        }
        throw new IllegalStateException("length [" + length + "] out of range (0x1000000)");
    }

    @Override
    public String toString() {
        return getHexValue();
    }
}
