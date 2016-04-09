/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Camilo
 */
public class AESEncryptor implements EncryptionInterface{

    private static final String algorithm = "AES";
    
    private SecretKey key;
    
    public byte[] encrypt(String data)
    {
        Cipher cipher = null;
        
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        byte[] cipherText = null;
        
        try {
            cipherText = cipher.doFinal(data.getBytes());
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cipherText;
        
    }
    
    public String decrypt(byte[] data)
    {
        
        Cipher cipher = null;
        
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            cipher.init(Cipher.DECRYPT_MODE, this.key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        byte[] plainText = null;
        
        try {
            plainText = cipher.doFinal(data);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new String(plainText);
        
    }
    
    public String generateKeys()
    {
        KeyGenerator keyGen = null;
        
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }

        SecretKey secretKey = null;
        
        if(keyGen != null){
            secretKey = keyGen.generateKey();
        }
        
        if(secretKey != null){
            this.key = secretKey;
        }
        
        return AESEncryptor.keyToString(secretKey);
        
    }
    
    public String getAlgorithm()
    {
        return AESEncryptor.algorithm;
    }
    
    public SecretKey getKey()
    {
        return this.key;
    }
    
    /**
     * 
     * Turns SecretKey to String for storage in DB
     * 
     * @param secretKey
     * @return 
     */
    public static String keyToString(SecretKey secretKey)
    {
        
        char[] hex;
        hex = Hex.encodeHex(secretKey.getEncoded());
        return String.valueOf(hex);
        
    }
    
}