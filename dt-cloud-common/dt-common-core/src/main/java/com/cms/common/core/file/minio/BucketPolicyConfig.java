package com.cms.common.core.file.minio;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Minio Bucket访问策略配置
 * @author DT
 * @date 2021/8/12 22:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class BucketPolicyConfig {

    private String Version;

    private List<Statement> Statement;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Statement {
        private String Effect;
        private String Principal;
        private String Action;
        private String Resource;
    }
}
