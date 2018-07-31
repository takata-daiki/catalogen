package org.genomespace.converter.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

public class DataFormat {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private URL url;
	private String name;
	private String fileExtension;
	private String description;
	private String internalId;

	public DataFormat(String name, String description, String url, String extension) {
		this.name = name;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			String message = MessageFormat.format("DataFormat|unable to create URL from string: {0}", 
					url);
			logger.error(message, e);
			throw new IllegalArgumentException(message, e);
		}
		this.fileExtension = extension;
		this.description = description;
	}
	
	public DataFormat(String name, String description, String url, String extension, String internalId) {
		this(name, description, url, extension);
		this.internalId = internalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String id) {
		this.name = id;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String extension) {
		this.fileExtension = extension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	public void setUrl(String urlString){
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public void setUrlString(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public String getUrlString() {
		return url.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileExtension == null) ? 0 : fileExtension.hashCode());
		result = prime * result
				+ ((internalId == null) ? 0 : internalId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataFormat other = (DataFormat) obj;
		if (fileExtension == null) {
			if (other.fileExtension != null)
				return false;
		} else if (!fileExtension.equals(other.fileExtension))
			return false;
		if (internalId == null) {
			if (other.internalId != null)
				return false;
		} else if (!internalId.equals(other.internalId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataFormat [name=");
		builder.append(name);
		builder.append(", fileExtension=");
		builder.append(fileExtension);
		builder.append("]");
		return builder.toString();
	}
}
