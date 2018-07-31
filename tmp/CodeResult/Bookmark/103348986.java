package it.units.stud.bookmarking.protocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Bookmark {
    
    private final String url;
    private final String description;
    private final HashSet<String> tags;
    private final int rank;
    private final String owner;

    public Bookmark(String url, String description, Set<String> tags, int rank, String owner) {
        this.url = url;
        this.description = description;
        this.tags = new HashSet<>(tags);
        this.rank = rank;
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getTags() {
        return new HashSet<>(tags);
    }
    
    public int getRank() {
        return rank;
    }

    public String getOwner() {
        return owner;
    }
    
    public Bookmark withDescription(String description) {
        return new Bookmark(url, description, tags, rank, owner);
    }
    
    public Bookmark withTags(Set<String> tags) {
        return new Bookmark(url, description, tags, rank, owner);
    }
    
    public Bookmark addTags(String... tags) {
        final Bookmark bookmark = new Bookmark(url, description, this.tags, rank, owner);
        bookmark.tags.addAll(Arrays.asList(tags));
        return bookmark;
    }
    
    public Bookmark removeTags(String... tags) {
        final Bookmark bookmark = new Bookmark(url, description, this.tags, rank, owner);
        bookmark.tags.removeAll(Arrays.asList(tags));
        return bookmark;
    }
    
    public Bookmark withRank(int rank) {
        return new Bookmark(url, description, tags, rank, owner);
    }
}
