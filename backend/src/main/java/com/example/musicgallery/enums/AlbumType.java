package com.example.musicgallery.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AlbumType {

    PHYSICAL(1), DIGITAL(2), LIVE(3);

    private Integer type;

    AlbumType(Integer type) {
        this.type = type;
    }

    public static Integer getType(String name) {

        for (AlbumType albumType : values()) {
            if (name.equals(albumType.name())) {
                return albumType.getType();
            }
        }
        return null;
    }

    public static Map<Integer, String> getAllAlbumTypes() {
        Map<Integer, String> albumTypeMap = new HashMap<>();
        for (AlbumType value : values()) {
            albumTypeMap.put(value.getType(value.name()), value.name());
        }
        return albumTypeMap;
    }


}
