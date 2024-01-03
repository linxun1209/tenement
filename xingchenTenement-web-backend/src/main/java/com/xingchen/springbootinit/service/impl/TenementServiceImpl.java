package com.xingchen.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.xingchen.springbootinit.common.ErrorCode;
import com.xingchen.springbootinit.constant.CommonConstant;
import com.xingchen.springbootinit.exception.BusinessException;
import com.xingchen.springbootinit.exception.ThrowUtils;
import com.xingchen.springbootinit.mapper.PostFavourMapper;
import com.xingchen.springbootinit.mapper.PostThumbMapper;
import com.xingchen.springbootinit.model.dto.post.PostQueryRequest;
import com.xingchen.springbootinit.model.dto.tenement.TenementQueryRequest;
import com.xingchen.springbootinit.model.entity.*;
import com.xingchen.springbootinit.model.vo.PostVO;
import com.xingchen.springbootinit.model.vo.TenementVo;
import com.xingchen.springbootinit.model.vo.UserVO;
import com.xingchen.springbootinit.service.TenementService;
import com.xingchen.springbootinit.mapper.TenementMapper;
import com.xingchen.springbootinit.service.UserService;
import com.xingchen.springbootinit.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author xing'chen
* @description 针对表【tenement】的数据库操作Service实现
* @createDate 2024-01-02 17:04:18
*/
@Service
public class TenementServiceImpl extends ServiceImpl<TenementMapper, Tenement>
    implements TenementService{


    private final static Gson GSON = new Gson();

    @Resource
    private UserService userService;



    @Override
    public void validTenement(Tenement tenement, boolean b) {
        if (tenement == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = tenement.getName();
        String houseNumber = tenement.getHouseNumber();
        // 创建时，参数不能为空
        if (b) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, houseNumber), ErrorCode.PARAMS_ERROR);
        }

    }

    /**
     * 获取查询包装类
     *
     * @param tenementQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Tenement> getQueryWrapper(TenementQueryRequest tenementQueryRequest) {
        QueryWrapper<Tenement> queryWrapper = new QueryWrapper<>();
        if (tenementQueryRequest == null) {
            return queryWrapper;
        }
        Long id = tenementQueryRequest.getId();
        String searchText = tenementQueryRequest.getSearchText();
        String name = tenementQueryRequest.getName();
        String sortField = tenementQueryRequest.getSortField();
        String sortOrder = tenementQueryRequest.getSortOrder();
        String gender = tenementQueryRequest.getGender();
        Long ICN = tenementQueryRequest.getICN();
        Long tel = tenementQueryRequest.getTel();
        String houseHoldType = tenementQueryRequest.getHouseHoldType();
        String houseNumber = tenementQueryRequest.getHouseNumber();
        Date rentTime = tenementQueryRequest.getRentTime();
        Date expirationDate = tenementQueryRequest.getExpirationDate();
        Integer rent = tenementQueryRequest.getRent();
        String age = tenementQueryRequest.getAge();
        Long userId = tenementQueryRequest.getUserId();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.like("name", name).or().like("houseHoldType", houseHoldType);
        }
        queryWrapper.like(StringUtils.isNotBlank(houseNumber), "houseNumber", houseNumber);
        queryWrapper.like(StringUtils.isNotBlank(gender), "gender", gender);
        queryWrapper.ne(ObjectUtils.isNotEmpty(ICN), "ICN", ICN);
        queryWrapper.ne(ObjectUtils.isNotEmpty(tel), "tel", tel);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(rent), "rent", rent);
        queryWrapper.eq(ObjectUtils.isNotEmpty(rentTime), "rentTime", rentTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(expirationDate), "expirationDate", expirationDate);
        queryWrapper.eq(ObjectUtils.isNotEmpty(age),"age",age);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }




    @Override
    public TenementVo getTenementVO(Tenement tenement, HttpServletRequest request) {
        TenementVo tenementVo = new TenementVo();
        BeanUtils.copyProperties(tenement,tenementVo);
        // 1. 关联查询用户信息
        Long userId = tenement.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        tenementVo.setUser(userVO);
        return tenementVo;
    }

    @Override
    public Page<TenementVo> getTenementVOPage(Page<Tenement> tenementPage, HttpServletRequest request) {
        List<Tenement> tenementList = tenementPage.getRecords();
        Page<TenementVo> tenementVoPage = new Page<>(tenementPage.getCurrent(), tenementPage.getSize(), tenementPage.getTotal());
        if (CollUtil.isEmpty(tenementList)) {
            return tenementVoPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = tenementList.stream().map(Tenement::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<TenementVo> teVOList = tenementList.stream().map(tenement -> {
            TenementVo tenementVo=new TenementVo();
            BeanUtils.copyProperties(tenement,tenementVo);
            Long userId = tenement.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            tenementVo.setUser(userService.getUserVO(user));
            return tenementVo;
        }).collect(Collectors.toList());
        tenementVoPage.setRecords(teVOList);
        return tenementVoPage;
    }
}




