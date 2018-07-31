/*
 * Copyright 2012-2015 Dirk Strauss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ds2.oss.core.api;

/**
 * The known hash algorithms for the MessageDigest.
 *
 * @author dstrauss
 * @version 0.4
 */
public enum HashAlgorithm {
    /**
     * The MD5 hash.
     */
    MD5("md5"),
    /**
     * The SHA1 hash.
     */
    SHA1("sha-1"),
    /**
     * The SHA 256 hash.
     */
    SHA256("sha-256"),
    /**
     * The sha 512 algorithm.
     */
    SHA512("sha-512");
    /**
     * The algorithm id.
     */
    private String alg;

    /**
     * Inits the enum value.
     *
     * @param s
     *            the algorithm id
     */
    private HashAlgorithm(final String s) {
        alg = s;
    }

    /**
     * Returns the algorithm id for this hash type.
     *
     * @return the algorithm id
     */
    public String getAlgorithm() {
        return alg;
    }
}
