package com.cms.document.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cms.common.jdbc.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ydf Created by 2022/4/7 10:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_file_info")
@ApiModel(value="系统字典对象")
@EqualsAndHashCode(callSuper = true)
public class FileInformationEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "文件名称")
    private String filename;

    @ApiModelProperty(value = "后缀")
    private String suffix;

    @ApiModelProperty(value = "大小")
    private Long size;

    @ApiModelProperty(value = "文件地址")
    private String fileUrl;

    @ApiModelProperty(value = "桶名称")
    private String bucket;

    @ApiModelProperty(value = "文件对象名称")
    private String objectName;
}
