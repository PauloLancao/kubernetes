package com.callisto.restdocopenapi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Foo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    
    @Column()
    private String body;

    @Column()
    private String metadata;
    
    @Column()
    private String name;
    
    @Column()
    private String link;
    
    protected Foo() {
    }

    public Foo(long id, String title, String body, String metadata, String name, String link) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.metadata = metadata;
        this.name = name;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public String getLink() {
		return name;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
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
        Foo other = (Foo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (metadata == null) {
            if (other.metadata != null)
                return false;
        } else if (!metadata.equals(other.metadata))
            return false;
        if (name == null) {
            if (other.name!= null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (link == null) {
            if (other.link!= null)
                return false;
        } else if (!link.equals(other.link))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Foo [id=" + id + ", title=" + title + ", metadata=" + metadata + ", name=" + name + "]";
    }
}
