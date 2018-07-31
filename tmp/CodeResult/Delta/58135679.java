/**
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.waveprotocol.wave.concurrencycontrol.common;

import static org.waveprotocol.wave.model.wave.Constants.NO_VERSION;

import org.waveprotocol.wave.model.operation.wave.WaveletOperation;
import org.waveprotocol.wave.model.util.Preconditions;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is simply a list of operations with some additional meta data.
 *
 * This class is very similar to WaveDeltaMessage. One of the reasons for
 * this class's existence is that we remove all dependencies to any client code
 * from CC.
 *
 * This class also only contains data that is absolutely needed by CC.
 *
 * @author zdwang@google.com (David Wang)
 */
public class Delta implements Iterable<WaveletOperation> {

  /**
   * NOTE(danilatos): Exposing so the merging subclass may optimise the ops using
   * composition, before it is serialised.
   */
  protected final List<WaveletOperation> operations = new ArrayList<WaveletOperation>();

  /**
   * The last know version from the server before the first operation
   * in this list.
   */
  private long version = NO_VERSION;

  /**
   * The hashed version of the wave after the last operation in this list is
   * applied. This field is set by the wave server and is only useful for
   * recovery purposes.
   *
   * The version is the version after all the operations in the delta has been
   * applied. This version number can be different to the version number in the
   * delta + number of operations in the delta.
   */
  private HashedVersion signature;

  /**
   * The timestamp the delta occurred at.
   */
  private long timestamp = Constants.NO_TIMESTAMP;

  /**
   * Constructs a delta without knowing a version.
   */
  public Delta() {
  }

  /**
   * Constructs a delta with a version.
   *
   * @param version The last know version from the server before the first operation
   * in this list.
   */
  public Delta(long version) {
    this.version = version;
  }

  /**
   * Constructs a delta with a version and signature
   *
   * @param version The last know version from the server before the first operation
   * in this list.
   * @param signature This signature after the ops are applied on the server.
   */
  public Delta(long version, HashedVersion signature) {
    this.version = version;
    this.signature = signature;
  }

  /**
   * Adds a given op to the list.
   * @param op
   */
  public void add(WaveletOperation op) {
    Preconditions.checkNotNull(op, "Null operation");
    operations.add(op);
  }

  /**
   * @return the wave operation at the given index.
   */
  public WaveletOperation get(int i) {
    return operations.get(i);
  }

  /**
   * @return The number of operations in the container.
   */
  public int size() {
    return operations.size();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<WaveletOperation> iterator() {
    return operations.iterator();
  }

  /**
   * @return The last know version from the server before the first operation
   * in this list.
   */
  public long getVersion() {
    return version;
  }

  /**
   * Sets the last know version from the server before the first operation.
   *
   * @param version
   */
  public void setVersion(long version) {
    this.version = version;
  }

  /**
   * Gets the signature of the delta from the server. This the signature after
   * all the operations in this delta have been applied.
   */
  public HashedVersion getHashedVersion() {
    return signature;
  }

  /**
   * Sets the hash of the delta from the server. This the signature after
   * all the operations in this delta have been applied.
   */
  public void setSignature(HashedVersion signature) {
    this.signature = signature;
  }

  /**
   * Add every operation in the given delta to this list.
   * @param delta
   */
  public void addAll(Delta delta) {
    Preconditions.checkNotNull(delta, "Null delta");
    operations.addAll(delta.operations);
  }

  /**
   * @return Is list empty.
   */
  public boolean isEmpty() {
    return operations.isEmpty();
  }

  /**
   * Replaces an operation at the given index.
   * @return The previous operation at the index.
   */
  public WaveletOperation set(int index, WaveletOperation op) {
    Preconditions.checkNotNull(op, "Null operation");
    return operations.set(index, op);
  }

  /**
   * Removes an operation at the given index.
   * @return The previous operation at the index.
   */
  public WaveletOperation remove(int index) {
    return operations.remove(index);
  }

  /**
   * Clears all operations from this delta.
   */
  public void clear() {
    operations.clear();
  }

  /**
   * Adds all the given operation to the delta.
   */
  public void addAll(WaveletOperation[] ops) {
    for (WaveletOperation op : ops) {
      operations.add(op);
    }
  }

  /**
   * Get the timestamp the delta occurred at.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Set the timestamp the delta occurred at.
   * @param timestamp
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    String ret = "";
    for (WaveletOperation o : operations) {
      if (!"".equals(ret)) {
        ret += ", ";
      }
      ret += o;
    }

    return "Delta = " +
        "[operations:" + ret + "] \n" +
        "[version:" + version + "] \n" +
        "[signature:" + signature + "] \n" +
        "[timestamp: " + timestamp + "]";
  }
}
