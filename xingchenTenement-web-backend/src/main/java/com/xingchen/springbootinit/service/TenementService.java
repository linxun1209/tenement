package com.xingchen.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchen.springbootinit.model.dto.tenement.TenementQueryRequest;
import com.xingchen.springbootinit.model.entity.Tenement;
import com.xingchen.springbootinit.model.entity.Tenement;
import com.xingchen.springbootinit.model.vo.PostVO;
import com.xingchen.springbootinit.model.vo.TenementVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author xing'chen
* @description 针对表【tenement】的数据库操作Service
* @createDate 2024-01-02 17:04:18
*/
public interface TenementService extends IService<Tenement> {

    void validTenement(Tenement tenement, boolean b);

    Wrapper<Tenement> getQueryWrapper(TenementQueryRequest tenementQueryRequest);

    Page<TenementVo> getTenementVOPage(Page<Tenement> tenementPage, HttpServletRequest request);

    TenementVo getTenementVO(Tenement tenement, HttpServletRequest request);
}
