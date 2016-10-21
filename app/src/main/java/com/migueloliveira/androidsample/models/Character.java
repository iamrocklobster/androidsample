package com.migueloliveira.androidsample.models;

/**
 * Created by migueloliveira on 19/10/2016.
 */

public class Character {
    private Integer id;
    private String name;
    private String description;
    private String thumbnailExtension;

    public Character(Integer id, String name, String description, String thumbnail, String thumbnailExtension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtension = thumbnailExtension;
    }

    public String getThumbnail() {
        return thumbnail + "/standard_medium" + "." + thumbnailExtension;
    }

    public String getBackground() {
        return thumbnail + "/landscape_xlarge" + "." + thumbnailExtension;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    private String thumbnail;

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
