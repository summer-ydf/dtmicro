package com.cms.common.core.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 14:42
 */
@NoArgsConstructor
@Getter
@Setter
public class SettingModel {
   
    private String key;
    
    private String title;
    
    private String placeholder;
    
    private Object value;
   
    private String type = "input";
   
    private List data = new ArrayList();
   
    private int pub = 1;//是否公开，1是，0否

    private boolean encrypt = true;//是否加密存储

    private String rettype;//返回类型

    private SettingModelDataLoader loader;
   
    private SettingModelDataCallbacker callbacker;

    public SettingModel(String key, String title, String placeholder) {
        this.key = key;
        this.title = title;
        this.placeholder = placeholder;
    }

    public SettingModel(String key, String title, String placeholder, SettingModelDataCallbacker callbacker) {
        this.key = key;
        this.title = title;
        this.placeholder = placeholder;
        this.callbacker = callbacker;
    }

    public SettingModel(String key, String title, String placeholder, String rettype) {
        this.key = key;
        this.title = title;
        this.rettype = rettype;
        this.placeholder = placeholder;
    }

    public SettingModel(String key, String title, String placeholder, int pub) {
        this.key = key;
        this.title = title;
        this.placeholder = placeholder;
        this.pub = pub;
    }

    public SettingModel(String key, String title, String placeholder, String type, List data) {
        this(key, title, placeholder);
        this.type = type;
        this.data.clear();
        this.data.addAll(data);
        if (this.type.equals("select")) {
            this.value = new ArrayList<>();
        }
    }

    public SettingModel(String key, String title, String placeholder, String type, List data, String rettype) {
        this(key, title, placeholder);
        this.type = type;
        this.rettype = rettype;
        this.data.clear();
        this.data.addAll(data);
        if (this.type.equals("select")) {
            this.value = new ArrayList<>();
        }
    }

    public SettingModel(String key, String title, String placeholder, String type, boolean encrypt) {
        this(key, title, placeholder);
        this.type = type;
        this.encrypt = encrypt;
    }

    public SettingModel(String key, String title, String placeholder, String type, SettingModelDataLoader loader) {
        this(key, title, placeholder);
        this.type = type;
        this.loader = loader;
        if (this.type.equals("select")) {
            this.value = new ArrayList<>();
        }
    }
}
