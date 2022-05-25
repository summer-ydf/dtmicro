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
    // 加密秘钥
    private static final String en_key = "klkskhiioiow0993829848398479e";
    // 偏移量
    private static final String en_iv = "8982983jklskdhy8iiowieoiwhhuu";


    public static List<SettingModels> values(){
        return list;
    }

    public static void addModels(String key,String title, SettingModelBuilders builders){
        list.add(new SettingModels(key,title,builders));
    }

    public static List<Map<String,Object>> settingList(List<SysConfig> configList){
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
            for( SettingModel model : models) {
                String convert_key = model.getKey().replaceAll("[.]","_");
                List data = null;
                if(model.getLoader() != null){
                    data = model.getLoader().load();
                }else {
                    data = model.getData();
                }
                SettingModel settingModel = new SettingModel(convert_key,model.getTitle(),model.getPlaceholder(),model.getType(),data);
                settingModel.setEncrypt(model.isEncrypt());
                Optional<SysConfig> schoolSettingOptional = configList.stream().filter(exist->exist.getK().equals(convert_key)).findAny();
                if(schoolSettingOptional.isPresent()){
                    if(model.getType().equals("select")){
                        String de= "";
                        if(model.isEncrypt()){
                            de= EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),en_key,en_iv, EncryptUtils.EncodeType.Hex);
                        }else {
                            de= schoolSettingOptional.get().getV();
                        }
                        settingModel.setValue(de.split(","));
                    }else{
                        if(model.isEncrypt()){
                            settingModel.setValue(EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),en_key,en_iv, EncryptUtils.EncodeType.Hex));
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
                        params.put(model.getKey(),EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),en_key,en_iv, EncryptUtils.EncodeType.Hex));
                    }else {
                        params.put(model.getKey(),schoolSettingOptional.get().getV());
                    }
                }
            }
        }
        return params;
    }

    public static List<SettingModel> settingModel(List<SysConfig> configList){
        List<SettingModel> result_models = new ArrayList<>();
        List<SettingModels> settingss = values();
        for(SettingModels setting:settingss){
            List<SettingModel> models = setting.getSettingModels();
            for( SettingModel model:models){
                String convert_key=model.getKey().replaceAll("[.]","_");
                Optional<SysConfig> schoolSettingOptional = configList.stream().filter(schoolSetting -> schoolSetting.getK().equals(convert_key)).findAny();
                if(schoolSettingOptional.isPresent()){
                    if(model.isEncrypt()){
                        model.setValue(EncryptUtils.decryptAES_CBC(schoolSettingOptional.get().getV(),en_key,en_iv, EncryptUtils.EncodeType.Hex));
                    }else {
                        model.setValue(schoolSettingOptional.get().getV());
                    }
                    result_models.add(model);
                }
            }
        }
        return result_models;
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
                    sysConfig.setV(EncryptUtils.encryptAES_CBC(value,en_key,en_iv, EncryptUtils.EncodeType.Hex));
                }else {
                    sysConfig.setV(value);
                }
                sysConfig.setVal(value);
                sysConfigList.add(sysConfig);
            }
        }
        return sysConfigList;
    }

//    public static Params removePub(Params params){
//        List<SettingModels> settingss = values();
//        for(SettingModels setting:settingss){
//            List<SettingModel> models = setting.getSettingModels();
//            for( SettingModel model:models){
//                if(model.getPub()==0){
//                    params.remove(model.getKey());
//                }
//                if(StringUtils.equalsIgnoreCase(model.getRettype(),"array")){
//                    Object val_obj=params.get(model.getKey());
//                    if(val_obj instanceof String){
//                        String val=StringUtils.replace(StringUtils.deleteWhitespace(params.getString(model.getKey())),"，",",");
//                        params.put(model.getKey(),StringUtils.split(val,","));
//                    }
//                }
//            }
//        }
//        return params;
//    }


    public static void setData(String key,List data){
        List<SettingModels> settingss = values();
        Optional<SettingModel> settingModel = settingss.stream().flatMap(settingModels -> settingModels.getSettingModels().stream()).filter(sm -> StringUtils.equals(sm.getKey(), key)).findAny();
        if(settingModel.isPresent()){
            settingModel.get().setData(data);
        }
    }
}
