package com.xingchen.springbootinit.model.dto.tenement;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 编辑请求
 *
 * @author <a href="https://github.com/lixingchen">程序员鱼皮</a>
 * @from <a href="https://xingchen.icu">编程导航知识星球</a>
 */
@Data
public class TenemenEditRequest implements Serializable {

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
     * 租房用户id
     */
    private long userId;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}