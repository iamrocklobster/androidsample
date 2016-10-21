package com.migueloliveira.androidsample.models;

/**
 * Created by migueloliveira on 20/10/2016.
 */

public class Comic {
    private Integer id;
    private String name;
    private String description;
    private String thumbnail;
    private String thumbnailExtension;
    private Integer pageCount;

    public Comic(Integer id, String name, String description, String thumbnail, String thumbnailExtension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtension = thumbnailExtension;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail + "/standard_medium" + "." + thumbnailExtension;
    }

    public String getBackground() {
        return thumbnail + "/landscape_incredible" + "." + thumbnailExtension;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }
}
