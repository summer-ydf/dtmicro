package com.cms.item.canal.service;

import com.alibaba.otter.canal.protocol.Message;

/**
 * @author ydf Created by 2021/10/11 14:17
 */
public interface MessageConvert {

    void convert(Message message) throws Exception;

    void register(Class<? extends MessageProcess> process);
}
