package org.mindstorms.benchmark.time.win32;

import com.sun.jna.Structure;

/**
 * A mapping for the FILETIME structure.
 */
public class FileTime extends Structure implements IFileTime {
  private static final long FILETIME_TICKS = 10000L;
  private static final long MILLIS= 1000L;

  public int dwLowDateTime;
  public int dwHighDateTime;

  /**
   * Returns the value represented by the structure.
   * It is expressed in seconds.
   *
   * @return
   */
  public double toClock() {
    long result = dwHighDateTime;
    result = result << 32;
    result = result | dwLowDateTime;

    return (double) result / (FILETIME_TICKS * MILLIS);
  }
}
