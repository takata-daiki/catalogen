package org.fenrir.jcollector.core.entity;

import java.util.Date;

/**
 * TODO v1.0 Javadoc
 * @author Antonio Archilla Nava
 * @version v0.1.20110724
 */
public class Picture 
{
	private Long id;
    private String relativePath;	
	private Long collection;			
	private Long pictureGroup;		
	private Long repository;		
    private Integer width;
    private Integer height;
    private Long resolution;
    private Date collectionDate;
    private Boolean collected;
    private String checksum;

    public Picture()
    {
    	
    }
    
    public Picture(Picture picture)
    {
    	this.setId(picture.getId());
    	this.setRelativePath(picture.getRelativePath());
    	this.setCollection(picture.getCollection());
    	this.setPictureGroup(picture.getPictureGroup());
    	this.setRepository(picture.getRepository());
    	this.setWidth(picture.getWidth());
    	this.setHeight(picture.getHeight());
    	this.setResolution(picture.getResolution());
    	this.setCollectionDate(picture.getCollectionDate());
    	this.setCollected(picture.isCollected());
    	this.setChecksum(picture.getChecksum());
    }
    
    public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getRelativePath() 
	{
		return relativePath;
	}
	
	public void setRelativePath(String relativePath) 
	{
		this.relativePath = relativePath;
	}
	
	public Long getCollection() 
	{
		return collection;
	}
	
	public void setCollection(Long collection) 
	{
		this.collection = collection;
	}
	
	public Long getPictureGroup() 
	{
		return pictureGroup;
	}
	
	public void setPictureGroup(Long pictureGroup) 
	{
		this.pictureGroup = pictureGroup;
	}
	
	public Long getRepository() 
	{
		return repository;
	}
	
	public void setRepository(Long repository) 
	{
		this.repository = repository;
	}
	
	public Integer getWidth() 
	{
		return width;
	}
	
	public void setWidth(Integer width) 
	{
		this.width = width;
	}
	
	public Integer getHeight() 
	{
		return height;
	}
	
	public void setHeight(Integer height) 
	{
		this.height = height;
	}
	
	public Long getResolution()
	{
		return resolution;
	}
	
	public void setResolution(Long resolution)
	{
		this.resolution = resolution;
	}

	public Date getCollectionDate() 
	{
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) 
	{
		this.collectionDate = collectionDate;
	}
	
	public Boolean isCollected()
	{
		return collected;
	}
	
	public void setCollected(Boolean collected)
	{
		this.collected = collected;
	}
	
	public String getChecksum()
	{
		return checksum;
	}
	
	public void setChecksum(String checksum)
	{
		this.checksum = checksum;
	}
		
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result	+ ((relativePath == null) ? 0 : relativePath.hashCode());		
		result = prime * result	+ ((collection == null) ? 0 : collection.hashCode());			
		result = prime * result	+ ((repository == null) ? 0 : repository.hashCode());
		result = prime * result	+ ((pictureGroup == null) ? 0 : pictureGroup.hashCode());				
		result = prime * result + ((width == null) ? 0 : width.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((resolution == null) ? 0 : resolution.hashCode());
		result = prime * result	+ ((collectionDate == null) ? 0 : collectionDate.hashCode());
		result = prime * result + ((collected == null) ? 0 : collected.hashCode());
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Picture)){
			return false;
		}
		
		Picture other = (Picture) obj;	
		/* id */
		if(id==null){
			if(other.id!=null){
				return false;
			}
		} 
		else if(!id.equals(other.id)){
			return false;
		}
		/* Relative path */ 
		if(relativePath==null){
			if(other.relativePath!=null){
				return false;
			}
		} 
		else if(!relativePath.equals(other.relativePath)){
			return false;
		}	
		/* Collection */
		if(collection==null){
			if(other.collection!=null){
				return false;
			}
		} 
		else if(!collection.equals(other.collection)){
			return false;
		}
		/* Repository */
		if(repository==null){
			if(other.repository!=null){
				return false;
			}
		} 
		else if(!repository.equals(other.repository)){
			return false;
		}
		/* Picture group */
		if(pictureGroup==null){
			if(other.pictureGroup!=null){
				return false;
			}
		} 
		else if(!pictureGroup.equals(other.pictureGroup)){
			return false;
		}			
		/* Width */
		if(width==null){
			if(other.width!=null){
				return false;
			}
		}
		else if(!width.equals(other.width)){
			return false;
		}
		/* Height */
		if(height==null){
			if(other.height!=null){
				return false;
			}
		} 
		else if(!height.equals(other.height)){
			return false;
		}	
		/* Resolution */
		if(resolution==null){
			if(other.resolution!=null){
				return false;
			}
		} 
		else if(!resolution.equals(other.resolution)){
			return false;
		}
		/* CollectionDate */
		if(collectionDate==null){
			if(other.collectionDate!=null){
				return false;
			}
		} 
		else if(!collectionDate.equals(other.collectionDate)){
			return false;
		}
		/* Collected */
		if(collected==null){
			if(other.collected!=null){
				return false;
			}
		}
		else if(!collected.equals(other.collected)){
			return false;
		}
		/* Checksum */
		if(checksum==null){
			if(other.checksum!=null){
				return false;
			}
		} 
		else if(!checksum.equals(other.checksum)){
			return false;
		}
		
		return true;
	}
}
