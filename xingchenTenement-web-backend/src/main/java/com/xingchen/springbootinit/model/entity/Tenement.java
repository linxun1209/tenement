package com.xingchen.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName tenement
 */
@TableName(value ="tenement")
@Data
public class Tenement implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身份证号码
     */
    private Long ICN;

    /**
     * 电话号码
     */
    private Long tel;

    /**
     * 户型
     */
    private String houseHoldType;

    /**
     * 门牌号
     */
    private String houseNumber;

    /**
     * 租入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentTime;

    /**
     * 合同到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;


    /**
     * 租房用户id
     */
    private Long userId;

    /**
     * 租房金额(每月)
     */
    private Integer rent;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}