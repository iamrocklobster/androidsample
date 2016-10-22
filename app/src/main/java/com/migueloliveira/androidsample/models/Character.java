package com.migueloliveira.androidsample.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by migueloliveira on 19/10/2016.
 */

public class Character implements Parcelable {
    private Integer id;
    private String name;
    private String description;
    private String thumbnail;
    private String thumbnailExtension;

    public Character(Integer id, String name, String description, String thumbnail, String thumbnailExtension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtension = thumbnailExtension;
    }

    protected Character(Parcel in) {
        name = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        thumbnailExtension = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(thumbnailExtension);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

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
