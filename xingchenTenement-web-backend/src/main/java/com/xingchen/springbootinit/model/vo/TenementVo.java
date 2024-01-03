package com.xingchen.springbootinit.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xingchen.springbootinit.model.entity.Tenement;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName tenement
 */
@TableName(value ="tenement")
@Data
public class TenementVo implements Serializable {
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
     * 创建人信息
     */
    private UserVO user;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}