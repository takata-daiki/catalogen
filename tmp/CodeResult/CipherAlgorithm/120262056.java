/*
 * Copyright 2009-2011 Antonio Muñoz <antoniogmc (AT) gmail.com>
 * Distributed under the terms of the GNU General Public License v3
 */

package com.agmc.crypto.provider.cipher;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.agmc.crypto.provider.MyException;
import com.agmc.crypto.provider.key.MyPBEKey;

/**
 *
 * <p>
 * Implementa el esquema 1 del PBE definido en PKCS5. Se utiliza junto a MD2 o
 * MD5 y DES.
 * </p>
 *
 * @author  Tomby
 * @version 0.5 25/03/2002
 *
 */
public abstract class MyPBES1 extends MyPBE {

    private int blockSize;
    private String mdAlgorithm;
    private String cipherAlgorithm;

    private MessageDigest md;

    /**
     * Constructor que inicializa el objeto con los parametros requeridos.
     * @param blockSize tamaño de un bloque, usualmente 16.
     * @param cipherAlgorithm algoritmo de cifrado a usar
     * @param mdAlgorithm algoritmo de hashing a usar
     *
     */
    public MyPBES1(int blockSize, String cipherAlgorithm, String mdAlgorithm) {
        super(cipherAlgorithm);
        this.mdAlgorithm = mdAlgorithm;
        this.cipherAlgorithm = cipherAlgorithm;
        this.blockSize = blockSize;
    }

    /**
     * Inicializa el cifrador interno.
     *
     * @param mode indica el modo con el que el cifrador se va inicilizar (Encriptar o desencriptar)
     * @param password el password
     * @param salt el salt o semilla
     * @param counter en numero de iteraciones que se usaran
     * @exception MyException si hay algun problema durante la inicialización.
     */
    protected void initCipher(int mode, MyPBEKey secretKey, byte[] salt, int counter) throws MyException {
        byte[] dkey = null;
        byte[] key = new byte[blockSize/2];
        byte[] iv = new byte[blockSize/2];

        try {
            md = MessageDigest.getInstance(this.mdAlgorithm, "MyJCE");
        }
        catch(GeneralSecurityException e) {
            //no debería ser lanzada
            throw new MyException("Error al inicializar el MessageDigest: clase=" + e.getClass() + " mensaje=" + e.getMessage());
        }

        dkey = pbkdf(secretKey.getEncoded(), salt, counter);

        System.arraycopy(dkey, 0, key, 0, blockSize/2);
        System.arraycopy(dkey, blockSize/2, iv, 0, blockSize/2);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, cipherAlgorithm);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(cipherAlgorithm, "MyJCE");

            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            SecretKey sk = factory.generateSecret(keySpec);
            cipher.init(mode, sk, ivSpec);
        }
        catch(GeneralSecurityException e) {
            //no debería ser lanzada
            e.printStackTrace(System.err);
            throw new MyException("Error al inicializar el Cipher: clase=" + e.getClass() + " mensaje=" + e.getMessage());
        }

    }

    /* es la funcion de derivación de la clave */
    private byte[] pbkdf(byte[] p, byte[] s, int c) {
        byte[] res = null;

        //1º calculo el primer valor
        md.update(p);
        md.update(s);
        res = md.digest();

        //luego calculamos el resto
        for(int i = 1; i < c; i++) {
            md.update(res);
            res = md.digest();
        }

        return res;
    }

    /* es la funcion de derivación de la clave */
    /*private byte[] pbkdf(byte[] p, byte[] s, int c) {

        for(int i = 0; i < c; i++) {
            md.update(p);
            md.update(s);
        }
        return md.digest();
    }*/

}
