package org.vaultage.shared.fs.meta;

import java.util.Date;
import java.util.Objects;

public final class MetaFile extends MetaFolder {

  private static final long serialVersionUID = 201302051235L;

  private final String hash;

  public MetaFile(String path, Date changeDate, String hash) {
    super(path, changeDate);
    this.hash = Objects.requireNonNull(hash);
  }

  @Override
  public final String getHash() {
    return hash;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), hash);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MetaFile) {
      MetaFile other = (MetaFile) obj;
      return super.equals(other) && Objects.equals(hash, other.hash);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return "MetaFile [path=" + getPath() + ", changeDate=" + getChangeDate() + ", hash=" + hash
        + "]";
  }

}
