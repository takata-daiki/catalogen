package br.com.dragonrise.filesizeanalyzer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class DirectoryNode
		implements
			FileNode {

	private static final Logger logger = LogManager
			.getLogger ( DirectoryNode.class );

	private final File directory;
	private final List<FileNode> childs;

	private transient FileSize size = null;
	private transient String toString = null;
	private transient int hashCode = -1;

	public DirectoryNode ( final File directory, final List<FileNode> childs ) {
		this.directory = checkNotNull ( directory, "directory is null" );
		this.childs = Collections.unmodifiableList ( Lists
				.newArrayList ( childs ) );
	}

	public File getFile ( ) {
		return directory;
	}

	public FileSize getNodeSize ( ) {
		logger.trace ( "getNodeSize" );
		if ( size != null ) {
			return size;
		}

		FileSize totalSize = FileSize.valueOf ( 0 );
		for ( final FileNode child : childs ) {
			totalSize = totalSize.add ( child.getNodeSize ( ) );
		}
		return size = totalSize;
	}

	public List<FileNode> getChilds ( ) {
		logger.trace ( "getChilds" );
		return childs;
	}

	public boolean isLeaf ( ) {
		logger.trace ( "isLeaf" );
		return false;
	}

	public int getTotalChildCount ( ) {
		logger.trace ( "getTotalChildCount" );
		int ret = 0;

		for ( final FileNode child : childs ) {
			ret += 1 + child.getTotalChildCount ( );
		}

		return ret;
	}

	public int compareTo ( final FileNode other ) {
		return directory.compareTo ( other.getFile ( ) );
	}

	@Override
	public boolean equals ( final Object obj ) {
		logger.trace ( "equals" );
		if ( obj == this ) {
			return true;
		}

		if ( ! ( obj instanceof DirectoryNode ) ) {
			return false;
		}

		final DirectoryNode other = (DirectoryNode) obj;
		return new EqualsBuilder ( )
				.append ( directory, other.directory )
					.isEquals ( );
	}

	@Override
	public int hashCode ( ) {
		logger.trace ( "hashCode" );
		int ret = hashCode;
		if ( ret < 0 ) {
			ret = new HashCodeBuilder ( ).append ( directory ).toHashCode ( );
		}
		return ret;
	}

	@Override
	public String toString ( ) {
		logger.trace ( "toString" );
		String ret = toString;
		if ( ret == null ) {
			final String name = directory.getName ( );
			if ( name.isEmpty ( ) ) {
				ret = directory.getPath ( );
			} else {
				ret = name;
			}
		}
		return ret;
	}
}
