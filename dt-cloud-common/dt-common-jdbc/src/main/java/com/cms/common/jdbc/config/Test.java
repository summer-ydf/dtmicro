package com.cms.common.jdbc.config;

import com.github.yitter.idgen.YitIdHelper;

/**
 * @author ydf Created by 2022/2/17 16:02
 */
public class Test {

    public static void main(String[] args) {
        long nextId = YitIdHelper.nextId();
        System.out.println(nextId);
    }
}
