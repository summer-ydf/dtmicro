package com.cms.manage.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统：用户实体
 * @author DT
 * @date 2021/6/2 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_operator")
public class SysOperatorEntity implements Serializable {

    private static final long serialVersionUID = 3660353661543747205L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;

}
