package com.migueloliveira.androidsample.models;

/**
 * Created by migueloliveira on 21/10/2016.
 */

public class Serie {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnail() {
        return thumbnail + "/standard_fantastic" + "." + thumbnailExtension;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    private Integer id;
    private String title;
    private String thumbnail;
    private String thumbnailExtension;

    public Serie(Integer id, String title, String thumbnail, String thumbnailExtension) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.thumbnailExtension = thumbnailExtension;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailExtension='" + thumbnailExtension + '\'' +
                '}';
    }
}
