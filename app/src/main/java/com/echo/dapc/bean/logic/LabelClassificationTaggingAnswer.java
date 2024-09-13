package com.echo.dapc.bean.logic;

import java.util.List;
import java.util.Map;

public class LabelClassificationTaggingAnswer {
    Map<Integer, String> answerTypes;
    List<LabelClassificationTaggingData> dataList;

    public static class LabelClassificationTaggingData{
        Long dataId;
        String dataContent;
        Integer answerKey;
    }

}
