package com.migueloliveira.androidsample.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by migueloliveira on 19/10/2016.
 */

public class Character implements Parcelable {
    private Integer id;
    private String name;
    private String description;
    private String thumbnail;
    private String thumbnailExtension;
    private String modified;
    private String resourceURI;

    protected Character(Parcel in) {
        name = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        thumbnailExtension = in.readString();
        modified = in.readString();
        resourceURI = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(thumbnailExtension);
        dest.writeString(modified);
        dest.writeString(resourceURI);
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

    public String getModified() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        try {
            Date date = format.parse(modified);
            return date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Character(Integer id, String name, String description, String thumbnail, String thumbnailExtension, String modified, String resourceURI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtension = thumbnailExtension;
        this.modified = modified;
        this.resourceURI = resourceURI;
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

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailExtension='" + thumbnailExtension + '\'' +
                ", modified='" + modified + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                '}';
    }
}
