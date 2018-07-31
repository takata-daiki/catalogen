/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.baseparadigm;

import java.math.BigInteger;
import java.util.Random;
import org.baseparadigm.i.Bytes;
import org.baseparadigm.i.CidScheme;

/**
 * A content addressable BigInteger. Unfortunately it isn't possible to subclass
 * Number or BigInteger because of comparability which is done differently in
 * content addressable objects than standard java numbers. This is a limitation
 * of the Java platform not an artifact of BaseParadigm design; there is no
 * language facility for combining behaviors.
 * 
 * Also, I am assuming that BigInteger's binary representation is transparent
 * and compatible with other platforms, but this has not been researched at the
 * time of this writing. The binary representation of CaInteger is up for comment.
 * 
 * @author travis
 */
public class CaInteger
     extends HasNecessaryGraph {
    
    private final BigInteger backingInteger;
    private final Bytes bytes;

    public CaInteger(CidScheme cids, BigInteger bi) {
        super(cids);
        backingInteger = bi;
        bytes = new ByteChunk(backingInteger.toByteArray());
    }
    public CaInteger(CidScheme cids, long bi) {
        super(cids);
        backingInteger = BigInteger.valueOf(bi);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }
    
    /* begin BigInteger constructors */
    
    public CaInteger(CidScheme cids, byte[] val) {
        super(cids);
        backingInteger = new BigInteger(val);
        bytes = new ByteChunk(val);
    }

    public CaInteger(CidScheme cids, int signum, byte[] magnitude) {
        super(cids);
        backingInteger = new BigInteger(signum, magnitude);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }

    public CaInteger(CidScheme cids, String val, int radix) {
        super(cids);
        backingInteger = new BigInteger(val, radix);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }

    public CaInteger(CidScheme cids, String val) {
        super(cids);
        backingInteger = new BigInteger(val);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }

    public CaInteger(CidScheme cids, int numBits, Random rnd) {
        super(cids);
        backingInteger = new BigInteger(numBits, rnd);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }

    public CaInteger(CidScheme cids, int bitLength, int certainty, Random rnd) {
        super(cids);
        backingInteger = new BigInteger(bitLength, certainty, rnd);
        bytes = new ByteChunk(backingInteger.toByteArray());
    }
    
    /* end BigInteger constructors */

    @Override public Bytes getBytes() { return bytes; }

    public BigInteger getBigInteger() { return backingInteger; }

}
