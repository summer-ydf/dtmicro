package com.cms.manage.entity;

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
 * 微信用户实体对象
 * @author DT辰白 Created by 2022/4/27 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("wx_member_user")
@ApiModel(value="微信用户实体对象")
public class WxMemberEntity extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "电话")
    private String mobile;

//    @ApiModelProperty(value = "生日")
//    private LocalDate birthday;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "唯一标识")
    private String openid;

    @ApiModelProperty(value = "sessionKey")
    private String sessionKey;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "状态")
    private Integer status;

//    @TableLogic(delval = "1", value = "0")
//    private Integer deleted;
}
