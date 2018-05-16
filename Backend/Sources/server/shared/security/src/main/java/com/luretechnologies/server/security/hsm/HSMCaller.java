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

import com.luretechnologies.server.security.hsm.message.HSMDecryptSymmetricDUKPT;
import com.luretechnologies.server.security.hsm.message.HSMDecryptSymmetricStatic;
import com.luretechnologies.server.security.hsm.message.HSMDerriveSymmetricKey;
import com.luretechnologies.server.security.hsm.message.HSMEncryptSymmetricStatic;
import com.luretechnologies.server.security.hsm.message.HSMExportKeyUnderRSAPublicKey;
import com.luretechnologies.server.security.hsm.message.HSMGenerateMACSymmetricStatic;
import com.luretechnologies.server.security.hsm.message.HSMGenerateRSAKeyPair;
import com.luretechnologies.server.security.hsm.message.HSMGenerateRSASignature;
import com.luretechnologies.server.security.hsm.message.HSMImportPublicKey;
import com.luretechnologies.server.security.hsm.message.HSMPerformDiagnostics;
import com.luretechnologies.server.security.hsm.message.HSMRSADecryptData;
import com.luretechnologies.server.security.hsm.message.HSMTranslateDUKPTPinToZPK;
import com.luretechnologies.server.security.hsm.message.HSMValidatePublicKey;
import com.luretechnologies.server.security.hsm.message.HSMValidateRSASignature;
import com.luretechnologies.server.security.hsm.message.HSMVerifyMACSymmetricDUKPT;
import com.luretechnologies.server.security.hsm.message.HSMVerifyMACSymmetricStatic;
import com.luretechnologies.server.security.util.Utils;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author developer
 */
public class HSMCaller {

    /**
     *
     */
    public static byte[] MOD_ENCODED_BYTES = {48, -126, 1, 9, 2, -126, 1, 0};

    /**
     *
     */
    public static byte[] EXP_ENCODED_BYTES = {2, 3};

    /**
     *
     */
    public static final int RESPONSE_SIZE = 8;

    /**
     *
     */
    public static final int RESPONSE_SIZE_WITH_LENGTH = 8;

    private static int RSA_BYTE_SIZE = 256;
    private static int ENCODED_KEY_SIZE = RSA_BYTE_SIZE + MOD_ENCODED_BYTES.length + EXP_ENCODED_BYTES.length + 3;
    private HSMPerformDiagnostics diagMessage;
    private HSMGenerateRSAKeyPair generateRSAKeyPair;
    private HSMDerriveSymmetricKey derriveSymmetricKey;
    private HSMGenerateRSASignature generateRSASignature;
    private HSMValidateRSASignature validateRSASignature;
    private HSMImportPublicKey importPublicKey;
    private HSMValidatePublicKey validatePublicKey;
    private HSMExportKeyUnderRSAPublicKey exportKeyUnderRSAPublicKey;
    private HSMRSADecryptData rsaDecryptData;
    private HSMDecryptSymmetricDUKPT decryptSymmetricDUKPT;
    private HSMDecryptSymmetricStatic decryptSymmetricStatic;
    private HSMEncryptSymmetricStatic encryptSymmetricStatic;
    private HSMGenerateMACSymmetricStatic generateMACSymmetricStatic;
    private HSMVerifyMACSymmetricStatic verifyMACSymmetricStatic;
    private HSMVerifyMACSymmetricDUKPT verifyMACSymmetricDUKPT;
    private HSMTranslateDUKPTPinToZPK translateDUKPTPinToZPK;
    private HSMChannel channel;
    byte[] byteMessage, response;

    /**
     *
     * @param hsmUnits
     */
    public HSMCaller(List<HSMUnit> hsmUnits) {

        channel = new HSMChannel(hsmUnits);
        ///
        diagMessage = new HSMPerformDiagnostics();
        derriveSymmetricKey = new HSMDerriveSymmetricKey();
        generateRSAKeyPair = new HSMGenerateRSAKeyPair();
        generateRSASignature = new HSMGenerateRSASignature();
        validateRSASignature = new HSMValidateRSASignature();
        importPublicKey = new HSMImportPublicKey();
        validatePublicKey = new HSMValidatePublicKey();
        exportKeyUnderRSAPublicKey = new HSMExportKeyUnderRSAPublicKey();
        rsaDecryptData = new HSMRSADecryptData();
        decryptSymmetricDUKPT = new HSMDecryptSymmetricDUKPT();
        decryptSymmetricStatic = new HSMDecryptSymmetricStatic();
        encryptSymmetricStatic = new HSMEncryptSymmetricStatic();
        generateMACSymmetricStatic = new HSMGenerateMACSymmetricStatic();
        verifyMACSymmetricStatic = new HSMVerifyMACSymmetricStatic();
        verifyMACSymmetricDUKPT = new HSMVerifyMACSymmetricDUKPT();
        translateDUKPTPinToZPK = new HSMTranslateDUKPTPinToZPK();
    }

    /**
     *
     * @throws Exception
     */
    public void initHSMTransmission() throws Exception {
        channel.connect();
    }

    /**
     *
     * @throws Exception
     */
    public void endHSMTransmission() throws Exception {
        channel.disconnect();
    }

    /**
     *
     * @return @throws Exception
     */
    public byte[] diagnose() throws Exception {
        byteMessage = diagMessage.exportMessage();

        return channel.exchange(byteMessage);
    }

    /**
     *
     * @param BDK
     * @param serialNumber
     * @return
     * @throws Exception
     */
    public byte[][] derriveIPEK(String BDK, String serialNumber) throws Exception {
        derriveSymmetricKey.defaultSettings();
        derriveSymmetricKey.masterKey.setValue(BDK);
        derriveSymmetricKey.serialNumber.setValue(padSerialWithF(serialNumber));

        byteMessage = derriveSymmetricKey.exportMessage();
        response = channel.exchange(byteMessage);

        byte[] IPEK = new byte[33];
        byte[] checkValue = new byte[6];

        System.arraycopy(response, RESPONSE_SIZE, IPEK, 0, 33);
        System.arraycopy(response, 41, checkValue, 0, 6);

        byte[][] keyAndCheckValue = {IPEK, checkValue};
        return keyAndCheckValue;
    }

    private String padSerialWithF(String serialNumber) {
        while (serialNumber.length() < 15) {
            serialNumber = "F" + serialNumber;
        }

        // Should check if length exceeds 15 and throws exception
        return serialNumber;
    }

    // RSA Key Commands
    /**
     *
     * @return @throws Exception
     */
    public byte[][] generateRSAKeyPair() throws Exception {
        generateRSAKeyPair.defaultSettings();
        byteMessage = generateRSAKeyPair.exportMessage();

        response = channel.exchange(byteMessage);

        byte[] publicKey = new byte[269];
        int privateKeySize = response.length - 281;
        byte[] privateKey = new byte[privateKeySize];

        System.arraycopy(response, RESPONSE_SIZE, publicKey, 0, 269);
        System.arraycopy(response, 281, privateKey, 0, privateKeySize);

        byte[][] keyPair = {decodePublicKey(publicKey), privateKey};
        return keyPair;
    }

    /**
     *
     * @param publicKey
     * @param dataToSign
     * @return
     * @throws Exception
     */
    public byte[] formatPublicKeyForSignature(byte[] publicKey, byte[] dataToSign) throws Exception {
        importPublicKey.defaultSettings();
        importPublicKey.publicKey.setRawParameter(encodePublicKey(publicKey));
        importPublicKey.dataToSign.setRawParameter(purgeDelimiters(dataToSign));

        byteMessage = importPublicKey.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE, response.length);
    }

    private byte[] purgeDelimiters(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 59 || data[i] == 126) {
                data[i] = 48;
            }
        }

        return data;
    }

    /**
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public byte[] formatPublicKey(byte[] publicKey) throws Exception {
        importPublicKey.defaultSettings();
        importPublicKey.publicKey.setRawParameter(encodePublicKey(publicKey));

        byteMessage = importPublicKey.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE, response.length);
    }

    /**
     *
     * @param hsmPublicKey
     * @param dataToVerify
     * @return
     * @throws Exception
     */
    public boolean validatePublicKey(byte[] hsmPublicKey, byte[] dataToVerify) throws Exception {
        validatePublicKey.hsmPublicKey.setRawParameter(hsmPublicKey);

        if (dataToVerify != null) {
            validatePublicKey.dataToVerify.setRawParameter(dataToVerify);
        }

        byteMessage = validatePublicKey.exportMessage();
        response = channel.exchange(byteMessage);

        return response[6] == 48 && response[7] == 48;
    }

    private byte[] encodePublicKey(byte[] publicKey) {
        byte[] encodedPublic = new byte[ENCODED_KEY_SIZE];

        int newModSize = MOD_ENCODED_BYTES.length + RSA_BYTE_SIZE,
                newExpIndex = newModSize + EXP_ENCODED_BYTES.length;

        System.arraycopy(MOD_ENCODED_BYTES, 0, encodedPublic, 0, MOD_ENCODED_BYTES.length);
        System.arraycopy(publicKey, 0, encodedPublic, MOD_ENCODED_BYTES.length, RSA_BYTE_SIZE);
        System.arraycopy(EXP_ENCODED_BYTES, 0, encodedPublic, newModSize, EXP_ENCODED_BYTES.length);
        System.arraycopy(publicKey, RSA_BYTE_SIZE, encodedPublic, newExpIndex, 3);

        return encodedPublic;
    }

    private byte[] decodePublicKey(byte[] encodedPublicKey) {
        byte[] decodedPublic = new byte[RSA_BYTE_SIZE + 3];

        System.arraycopy(encodedPublicKey, 8, decodedPublic, 0, RSA_BYTE_SIZE);
        System.arraycopy(encodedPublicKey, RSA_BYTE_SIZE + 10, decodedPublic, RSA_BYTE_SIZE, 3);

        return decodedPublic;
    }

    // RSA Crypto Commands
    /**
     *
     * @param data
     * @param hsmPrivateKey
     * @return
     * @throws Exception
     */
    public byte[] generateRSASignature(byte[] data, byte[] hsmPrivateKey) throws Exception {
        byte[] localData = Arrays.copyOf(data, data.length);

        generateRSASignature.defaultSettings();
        generateRSASignature.dataToSign.setRawParameter(purgeDelimiters(localData));
        generateRSASignature.privateKey.setRawParameter(hsmPrivateKey);

        byteMessage = generateRSASignature.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, RESPONSE_SIZE_WITH_LENGTH + RSA_BYTE_SIZE);
    }

    /**
     *
     * @param hsmPublicKey
     * @param hsmKey
     * @param keyCheckValue
     * @param hsmPrivateKey
     * @return
     * @throws Exception
     */
    public byte[][] exportKeyUnderRSAPublicKey(byte[] hsmPublicKey, byte[] hsmKey, byte[] keyCheckValue, byte[] hsmPrivateKey) throws Exception {
        exportKeyUnderRSAPublicKey.defaultSettings();
        exportKeyUnderRSAPublicKey.publicKey.setRawParameter(hsmPublicKey);
        exportKeyUnderRSAPublicKey.keyToExport.setRawParameter(hsmKey);
        exportKeyUnderRSAPublicKey.keyCheckValue.setRawParameter(padCheckWithZeros(keyCheckValue));
        exportKeyUnderRSAPublicKey.privateKey.setRawParameter(hsmPrivateKey);

        byteMessage = exportKeyUnderRSAPublicKey.exportMessage();
        response = channel.exchange(byteMessage);

        byte[] encryptedKey, signature;
        encryptedKey = Arrays.copyOfRange(response, 28, 284);
        signature = Arrays.copyOfRange(response, 288, 544);

        byte[][] exportedData = {encryptedKey, signature};

        return exportedData;
    }

    private byte[] padCheckWithZeros(byte[] data) {
        byte[] response = new byte[16];

        for (int i = 6; i < 16; i++) {
            response[i] = 48;
        }

        System.arraycopy(data, 0, response, 0, 6);

        return response;
    }

    /**
     *
     * @param encryptedBlock
     * @param hsmPrivateKey
     * @return
     * @throws Exception
     */
    public byte[] rsaDecryptData(byte[] encryptedBlock, byte[] hsmPrivateKey) throws Exception {
        rsaDecryptData.defaultSettings();
        rsaDecryptData.encryptedBlock.setRawParameter(encryptedBlock);
        rsaDecryptData.privateKey.setRawParameter(hsmPrivateKey);

        byteMessage = rsaDecryptData.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, response.length);
    }

    /**
     *
     * @param data
     * @param signature
     * @param hsmPublicKey
     * @return
     * @throws Exception
     */
    public boolean validateRSASignature(byte[] data, byte[] signature, byte[] hsmPublicKey) throws Exception {
        validateRSASignature.defaultSettings();
        validateRSASignature.dataToVerify.setRawParameter(data);
        validateRSASignature.signature.setRawParameter(signature);
        validateRSASignature.publicKey.setRawParameter(hsmPublicKey);

        byteMessage = validateRSASignature.exportMessage();
        response = channel.exchange(byteMessage);

        return response[6] == 48 && response[7] == 48;
    }

    /**
     *
     * @param bdk
     * @param encryptedBlock
     * @param serialNumber
     * @param initializationVector
     * @return
     * @throws Exception
     */
    public byte[] decryptSymmetricDUKPT(String bdk, byte[] encryptedBlock, String serialNumber, String initializationVector) throws Exception {
        decryptSymmetricDUKPT.defaultSettings();
        decryptSymmetricDUKPT.bdk.setValue(bdk);
        decryptSymmetricDUKPT.serialNumber.setValue(serialNumber);
        decryptSymmetricDUKPT.initializationVector.setValue(initializationVector);
        decryptSymmetricDUKPT.encryptedBlock.setRawParameter(encryptedBlock);

        byteMessage = decryptSymmetricDUKPT.exportMessage();
        response = channel.exchange(byteMessage);

        return Utils.removePaddingPKCS(Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH + 20, response.length));
    }

    /**
     *
     * @param tek
     * @param encryptedBlock
     * @param initializationVector
     * @return
     * @throws Exception
     */
    public byte[] decryptSymmetricStatic(String tek, byte[] encryptedBlock, String initializationVector) throws Exception {
        decryptSymmetricStatic.defaultSettings();
        decryptSymmetricStatic.key.setValue(tek);
        decryptSymmetricStatic.initializationVector.setValue(initializationVector);
        decryptSymmetricStatic.encryptedBlock.setRawParameter(encryptedBlock);

        byteMessage = decryptSymmetricStatic.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, response.length);
    }

    /**
     *
     * @param tek
     * @param clearBlock
     * @param initializationVector
     * @return
     * @throws Exception
     */
    public byte[] encryptSymmetricStatic(String tek, byte[] clearBlock, String initializationVector) throws Exception {
        encryptSymmetricStatic.defaultSettings();
        encryptSymmetricStatic.key.setValue(tek);
        encryptSymmetricStatic.initializationVector.setValue(initializationVector);
        encryptSymmetricStatic.clearBlock.setRawParameter(clearBlock);

        byteMessage = encryptSymmetricStatic.exportMessage();
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, response.length);
    }

    /**
     *
     * @param tak
     * @param dataBlock
     * @return
     * @throws Exception
     */
    public byte[] generateMACSymmetricStatic(String tak, byte[] dataBlock) throws Exception {
        generateMACSymmetricStatic.defaultSettings();
        generateMACSymmetricStatic.key.setValue(tak);
        generateMACSymmetricStatic.dataBlock.setRawParameter(dataBlock);

        byteMessage = generateMACSymmetricStatic.exportMessage();
        Utils.hexPrettyPrint(byteMessage, "Generate MAC Static Command");
        response = channel.exchange(byteMessage);

        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, response.length);
    }

    /**
     *
     * @param bdk
     * @param ksn
     * @param dataBlock
     * @return
     * @throws Exception
     */
    public boolean verifyPowaMAC_DUKPT(String bdk, String ksn, String dataBlock) throws Exception {
        byte[] data = Utils.toByteArray("0000000000000100" + Utils.SHA256(dataBlock.substring(0, dataBlock.length() - 14)));
        byte[] mac = dataBlock.substring(dataBlock.length() - 8, dataBlock.length()).getBytes();
        return verifyPowaMAC_DUKPT(bdk, ksn, data, mac);
    }

    /**
     *
     * @param bdk
     * @param ksn
     * @param mac
     * @param dataBlock
     * @return
     * @throws Exception
     */
    public boolean verifyPowaMAC_DUKPT(String bdk, String ksn, byte[] dataBlock, byte[] mac) throws Exception {
        return verifyMACSymmetricDUKPT(bdk, ksn, dataBlock, mac);
    }

    /**
     *
     * @param tak
     * @param dataBlock
     * @return
     * @throws Exception
     */
    public boolean verifyPowaMAC(String tak, String dataBlock) throws Exception {
        byte[] data = Utils.toByteArray(Utils.SHA256(dataBlock.substring(0, dataBlock.length() - 14)));
        byte[] mac = dataBlock.substring(dataBlock.length() - 8, dataBlock.length()).getBytes();
        return verifyPowaMAC(tak, data, mac);
    }

    /**
     *
     * @param tak
     * @param dataBlock
     * @param mac
     * @return
     * @throws Exception
     */
    public boolean verifyPowaMAC(String tak, byte[] dataBlock, byte[] mac) throws Exception {
        return verifyMACSymmetricStatic(tak, dataBlock, mac);
    }

    /**
     *
     * @param tak
     * @param dataBlock
     * @param MAC
     * @return
     * @throws Exception
     */
    public boolean verifyMACSymmetricStatic(String tak, byte[] dataBlock, byte[] MAC) throws Exception {

        verifyMACSymmetricStatic.defaultSettings();
        verifyMACSymmetricStatic.key.setValue(tak);
        verifyMACSymmetricStatic.dataBlock.setRawParameter(dataBlock);
        verifyMACSymmetricStatic.MAC.setRawParameter(MAC);

        byteMessage = verifyMACSymmetricStatic.exportMessage();
        response = channel.exchange("Verify MAC Static Command", byteMessage);

        return response.length == 8 && response[6] == '0' && response[7] == '0';
    }

    /**
     *
     * @param bdk
     * @param ksn
     * @param dataBlock
     * @param MAC
     * @return
     * @throws Exception
     */
    public boolean verifyMACSymmetricDUKPT(String bdk, String ksn, byte[] dataBlock, byte[] MAC) throws Exception {

        verifyMACSymmetricDUKPT.defaultSettings();
        verifyMACSymmetricDUKPT.bdk.setValue(bdk);
        verifyMACSymmetricDUKPT.ksn.setValue(ksn);
        verifyMACSymmetricDUKPT.dataBlock.setRawParameter(dataBlock);
        verifyMACSymmetricDUKPT.mac.setRawParameter(MAC);

        byteMessage = verifyMACSymmetricDUKPT.exportMessage();
        response = channel.exchange("Verify MAC DUKPT Command", byteMessage);

        return response.length == 8 && response[6] == '0' && response[7] == '0';
    }

    /**
     *
     * @param bdk
     * @param zpk
     * @param ksn
     * @param encryptedPinBlock
     * @param pan
     * @return
     * @throws Exception
     */
    public byte[] translateDUKPT_PIN_to_ZPK(String bdk, String zpk, String ksn, byte[] encryptedPinBlock, String pan) throws Exception {
        translateDUKPTPinToZPK.defaultSettings();
        translateDUKPTPinToZPK.bdk.setValue(bdk);
        translateDUKPTPinToZPK.zpk.setValue(zpk);
        translateDUKPTPinToZPK.ksn.setValue(ksn);
        translateDUKPTPinToZPK.encryptedBlock.setRawParameter(encryptedPinBlock);
        translateDUKPTPinToZPK.pan.setValue(pan.substring(pan.length() - 12 - 1, pan.length() - 1));

        byteMessage = translateDUKPTPinToZPK.exportMessage();
        response = channel.exchange(byteMessage);
        return Arrays.copyOfRange(response, RESPONSE_SIZE_WITH_LENGTH, response.length);
    }
}
