# 建表脚本
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists tenement;

-- 切换库
use tenement;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

# 租户信息表
create table if not exists tenement
(
    id         int auto_increment comment 'id' primary key,
    name       varchar(10)                       null comment '姓名',
    gender     varchar(1)                              null comment '性别',
    age        varchar(3)                         null comment '年龄',
    ICN        bigint                             null comment '身份证号码',
    tel        bigint                             null comment '电话号码',
    householdType varchar(3)                     null comment '户型',
    houseNumber varchar(10)                      null comment '门牌号',
    rentTime date                              null comment '租入时间',
    expirationDate date                           null comment '合同到期时间',
    rent       int                                null comment  '租房金额(每月)'
) ;


INSERT INTO tenement.user (id, userAccount, userPassword, userName, userAvatar, userProfile, userRole) VALUES (1, 'xingchen', 'b0dd3697a192885d7c055db46155b26a', '星辰', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'admin');
INSERT INTO tenement.user (id, userAccount, userPassword, userName, userAvatar, userProfile, userRole) VALUES (2, 'xingchen2', 'b0dd3697a192885d7c055db46155b26a', '林寻', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'user');