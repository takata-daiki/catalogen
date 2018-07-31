package org.vaultage.shared.file.meta;

import java.util.Objects;

public final class MetaFile extends MetaFolder {

  private static final long serialVersionUID = 201302051235L;

  private final String hash;
  private final long size;

  public MetaFile(String path, long changeDate, String hash, long size) {
    super(path, changeDate);
    this.hash = Objects.requireNonNull(hash);
    this.size = size;
  }

  @Override
  public final String getHash() {
    return hash;
  }

  public final long getSize() {
    return size;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), hash, size);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MetaFile) {
      MetaFile other = (MetaFile) obj;
      return super.equals(other) && Objects.equals(hash, other.hash)
          && Objects.equals(size, other.size);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return "MetaFile [path=" + getPath() + ", changeDate=" + getChangeDate() + ", hash="
        + hash + ", size=" + size + "]";
  }

}
