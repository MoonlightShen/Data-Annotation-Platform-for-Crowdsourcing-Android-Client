package com.echo.dapc.bean.logic.taggingscene;

import java.util.List;

public class LabelClassification {
    private List<String> tagOptions;
    private boolean multiSelect;
    private boolean customOption;

    public LabelClassification() {
    }

    public LabelClassification(List<String> tagOptions, boolean multiSelect, boolean customOption) {
        this.tagOptions = tagOptions;
        this.multiSelect = multiSelect;
        this.customOption = customOption;
    }

    public List<String> getTagOptions() {
        return tagOptions;
    }

    public void setTagOptions(List<String> tagOptions) {
        this.tagOptions = tagOptions;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public boolean isCustomOption() {
        return customOption;
    }

    public void setCustomOption(boolean customOption) {
        this.customOption = customOption;
    }
}
