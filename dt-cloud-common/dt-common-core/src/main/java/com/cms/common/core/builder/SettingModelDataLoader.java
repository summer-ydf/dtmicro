package com.cms.common.core.builder;

import java.util.List;

/**
 * @author DT
 * @date 2022/4/17 14:45
 */
@FunctionalInterface
public interface SettingModelDataLoader {
    List load();
}
