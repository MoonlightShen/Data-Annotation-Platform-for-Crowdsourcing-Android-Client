package com.echo.dapc.bean.enumeration;

public enum FileType {
    UNKNOWN(0, "UNKNOWN", "", null),
    TXT(1, "txt", "text/plain", DataType.TEXT),
    XLS(2, "xls", "application/vnd.ms-excel", DataType.TEXT),
    XLSX(3, "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", DataType.TEXT),
    CSV(4, "csv", "text/csv", DataType.TEXT),
    PNG(5, "png", "image/png", DataType.IMAGE),
    JPG(6, "jpg", "image/jpeg", DataType.IMAGE);

    private final int index;
    private final String suffix;
    private final String mime;
    private final DataType dataType;

    FileType(int index, String suffix, String mime, DataType dataType) {
        this.index = index;
        this.suffix = suffix;
        this.mime = mime;
        this.dataType = dataType;
    }

    public static FileType getByIndex(final int index) {
        for (FileType value : values()) {
            if (index == value.getIndex()) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static FileType getBySuffix(final String suffix) {
        for (FileType value : values()) {
            if (suffix.equals(value.getSuffix())) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static String[] getSupportedFiles(DataType dataType) {
        if (dataType==DataType.TEXT){
            return new String[]{TXT.getMime(), XLS.getMime(), XLSX.getMime(), CSV.getMime()};
        }
        if (dataType==DataType.IMAGE){
            return new String[]{PNG.getMime(), JPG.getMime()};
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getMime() {
        return mime;
    }

    public DataType getDataType() {
        return dataType;
    }
}
