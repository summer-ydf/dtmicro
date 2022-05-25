package com.cms.common.core.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型实体对象
 * @author DT辰白
 * @date 2022/4/17 14:42
 */
@NoArgsConstructor
@Getter
@Setter
public class SettingModel {

    /**
     * key
     */
    private String key;

    /**
     * 标题
     */
    private String title;

    /**
     * 输入提示
     */
    private String placeholder;

    /**
     * 值
     */
    private Object value;

    /**
     * 类型
     */
    private String type = "input";

    /**
     * 数据集合
     */
    private List data = new ArrayList();

    /**
     * 是否公开，1是，0否
     */
    private int pub = 1;

    /**
     * 是否加密存储
     */
    private boolean encrypt = true;

    /**
     * 返回类型
     */
    private String rettype;

    /**
     * 回调
     */
    private SettingModelDataCallBacker callbacker;

    public SettingModel(String key, String title, String placeholder) {
        this.key = key;
        this.title = title;
        this.placeholder = placeholder;
    }

    public SettingModel(String key, String title, String placeholder, SettingModelDataCallBacker callBacker) {
        this.key = key;
        this.title = title;
        this.placeholder = placeholder;
        this.callbacker = callBacker;
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

}
