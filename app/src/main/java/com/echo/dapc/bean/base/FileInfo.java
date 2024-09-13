package com.echo.dapc.bean.base;

import com.echo.dapc.bean.enumeration.FileType;

public class FileInfo {
    private String name;
    private String path;
    private FileType type;
    private Long size;
    private String key;
    private String tag;

    public FileInfo(String name, String path, FileType type, Long size, String key, String tag) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
        this.key = key;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

