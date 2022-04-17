package com.cms.common.core.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 14:41
 */
@NoArgsConstructor
public class SettingModels {
    @Getter
    private String key;
    @Getter
    private String title;
    @Getter
    private List<SettingModel> settingModels = new ArrayList<>();

    public SettingModels(String key,String title, SettingModelBuilders builders) {
        this(key,title,builders.build());
    }

    public SettingModels(String key,String title, List<SettingModel> models) {
        this.key = key;
        this.title = title;
        this.settingModels = models;
    }
}
