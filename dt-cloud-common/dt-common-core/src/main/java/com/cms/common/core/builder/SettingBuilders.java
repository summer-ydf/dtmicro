package com.cms.common.core.builder;

import com.cms.common.core.domain.Params;
import com.cms.common.core.domain.SysConfig;
import com.cms.common.core.utils.EncryptUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 配置模型类
 * @author DT辰白 Created by 2022/4/19 15:36
 */
@Data
public class SettingBuilders {

    private static List<SettingModels> list = new ArrayList<>();
    private static final String EN_KEY = "klkskhiioiow0993829848398479e";
    private static final String EN_IV = "8982983jklskdhy8iiowieoiwhhuu";

    public static List<SettingModels> values(){
        return list;
    }

    public static void addModels(String key,String title, SettingModelBuilders builders) {
        list.add(new SettingModels(key,title,builders));
    }

    public static List<Map<String,Object>> settingList(List<SysConfig> configList) {
        List<Map<String,Object>> schoolSettingList = new ArrayList<>();
        List<SettingModels> modelsList = values();
        for(SettingModels setting : modelsList){
            List<SettingModel> models = setting.getSettingModels();
            if(models.size() == 0){
                continue;
            }
            Map<String,Object> map = new HashMap<>();
            map.put("title",setting.getTitle());
            map.put("key",setting.getKey());
            List<SettingModel> settingModels = new ArrayList<>();
            for(SettingModel model : models) {
                String convertKey = model.getKey().replaceAll("[.]","_");
                SettingModel settingModel = new SettingModel(convertKey,model.getTitle(),model.getPlaceholder(),model.getType(),model.getData());
                settingModel.setEncrypt(model.isEncrypt());
                Optional<SysConfig> schoolSettingOptional = configList.stream().filter(exist->exist.getK().equals(convertKey)).findAny();
                if(schoolSettingOptional.isPresent()) {
                    if(model.getType().equals("select")) {
                        String de;
                        if(model.isEncrypt()){
                            de = EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),EN_KEY,EN_IV, EncryptUtils.EncodeType.Hex);
                        }else {
                            de = schoolSettingOptional.get().getV();
                        }
                        assert de != null;
                        settingModel.setValue(de.split(","));
                    }else {
                        if(model.isEncrypt()) {
                            settingModel.setValue(EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),EN_KEY,EN_IV, EncryptUtils.EncodeType.Hex));
                        }else {
                            settingModel.setValue(schoolSettingOptional.get().getV());
                        }
                    }
                }
                settingModels.add(settingModel);
            }
            map.put("models",settingModels);
            schoolSettingList.add(map);
        }
        return schoolSettingList;
    }

    public static Params settingMap(List<SysConfig> configList) {
        Params params = new Params();
        List<SettingModels> settingModelsList = values();
        for(SettingModels setting : settingModelsList) {
            List<SettingModel> models = setting.getSettingModels();
            for(SettingModel model : models) {
                if(model.getPub()==0) {
                    continue;
                }
                String convertKey = model.getKey().replaceAll("[.]","_");
                Optional<SysConfig> schoolSettingOptional = configList.stream().filter(sysConfig -> sysConfig.getK().equals(convertKey)).findAny();
                if(schoolSettingOptional.isPresent()) {
                    if(model.isEncrypt()) {
                        params.put(model.getKey(),EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),EN_KEY,EN_IV, EncryptUtils.EncodeType.Hex));
                    }else {
                        params.put(model.getKey(),schoolSettingOptional.get().getV());
                    }
                }
            }
        }
        return params;
    }

    public static List<SettingModel> settingModel(List<SysConfig> configList){
        List<SettingModel> resultModels = new ArrayList<>();
        List<SettingModels> settingModels = values();
        for(SettingModels setting : settingModels) {
            List<SettingModel> models = setting.getSettingModels();
            for( SettingModel model : models) {
                String convertKey = model.getKey().replaceAll("[.]","_");
                Optional<SysConfig> schoolSettingOptional = configList.stream().filter(schoolSetting -> schoolSetting.getK().equals(convertKey)).findAny();
                if(schoolSettingOptional.isPresent()) {
                    if(model.isEncrypt()) {
                        model.setValue(EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),EN_KEY,EN_IV, EncryptUtils.EncodeType.Hex));
                    }else {
                        model.setValue(schoolSettingOptional.get().getV());
                    }
                    resultModels.add(model);
                }
            }
        }
        return resultModels;
    }

    public static List<SysConfig> settingParamsToList(Params params) {
        List<SysConfig> sysConfigList = new ArrayList<>();
        List<SettingModels> settingModelsList = values();
        for(SettingModels setting : settingModelsList) {
            List<SettingModel> models = setting.getSettingModels();
            for( SettingModel model : models) {
                String convertKey = model.getKey().replaceAll("[.]","_");
                String value = StringUtils.trim(params.getString(convertKey));
                if(StringUtils.isEmpty(value)) {
                    continue;
                }
                SysConfig sysConfig =new SysConfig();
                sysConfig.setK(convertKey);
                if(model.isEncrypt()) {
                    sysConfig.setV(EncryptUtils.encryptAES_CBC(value,EN_KEY,EN_IV, EncryptUtils.EncodeType.Hex));
                }else {
                    sysConfig.setV(value);
                }
                sysConfig.setVal(value);
                sysConfigList.add(sysConfig);
            }
        }
        return sysConfigList;
    }

    public static Params removePub(Params params) {
        List<SettingModels> settingModels = values();
        for(SettingModels setting : settingModels) {
            List<SettingModel> models = setting.getSettingModels();
            for( SettingModel model : models){
                if(model.getPub() == 0) {
                    params.remove(model.getKey());
                }
                if(StringUtils.equalsIgnoreCase(model.getRettype(),"array")) {
                    Object valObj =params.get(model.getKey());
                    if(valObj instanceof String) {
                        String val=StringUtils.replace(StringUtils.deleteWhitespace(params.getString(model.getKey())),"，",",");
                        params.put(model.getKey(),StringUtils.split(val,","));
                    }
                }
            }
        }
        return params;
    }
}
