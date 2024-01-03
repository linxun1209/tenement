package com.xingchen.springbootinit.model.dto.tenement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xingchen.springbootinit.common.PageRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/lixingchen">程序员鱼皮</a>
 * @from <a href="https://xingchen.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenementQueryRequest extends PageRequest implements Serializable {

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
     * 搜索词
     */
    private String searchText;

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
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentTime;

    /**
     * 合同到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    /**
     * 租房金额(每月)
     */
    private Integer rent;

    /**
     * 租房用户id
     */
    private Long userId;




    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}