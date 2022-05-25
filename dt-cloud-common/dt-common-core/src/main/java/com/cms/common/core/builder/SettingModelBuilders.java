package com.cms.common.core.builder;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DT辰白
 * @date 2022/4/17 14:43
 */
@NoArgsConstructor
public class SettingModelBuilders implements Serializable {

    private List<SettingModel> models = new ArrayList<>();

    public SettingModelBuilders buildModel(String key, String title, String placeholder){
        models.add(new SettingModel(key,title,placeholder));
        return this;
    }

    public SettingModelBuilders buildModel(String key, String title, String placeholder, SettingModelDataCallBacker callBacker){
        models.add(new SettingModel(key,title,placeholder,callBacker));
        return this;
    }

    public SettingModelBuilders buildModel(String key, String title, String placeholder, String rettype){
        models.add(new SettingModel(key,title,placeholder,rettype));
        return this;
    }


    public SettingModelBuilders buildModel(String key, String title, String placeholder, int pub){
        models.add(new SettingModel(key,title,placeholder,pub));
        return this;
    }


    public SettingModelBuilders buildModel(String key, String title, String placeholder, String type, List data){
        models.add(new SettingModel(key,title,placeholder,type,data));
        return this;
    }

    public SettingModelBuilders buildModel(String key, String title, String placeholder, String type, List data, String rettype){
        models.add(new SettingModel(key,title,placeholder,type,data,rettype));
        return this;
    }


    public SettingModelBuilders buildModel(String key, String title, String placeholder, String type, boolean encrypt){
        models.add(new SettingModel(key,title,placeholder,type,encrypt));
        return this;
    }

    public SettingModelBuilders buildModel(String key, String title, String placeholder, String type, SettingModelDataLoader loader){
        models.add(new SettingModel(key,title,placeholder,type,loader));
        return this;
    }

    public List<SettingModel> build(){
        return this.models;
    }

}
