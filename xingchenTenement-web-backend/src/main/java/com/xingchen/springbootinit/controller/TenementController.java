package com.xingchen.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xingchen.springbootinit.annotation.AuthCheck;
import com.xingchen.springbootinit.common.BaseResponse;
import com.xingchen.springbootinit.common.DeleteRequest;
import com.xingchen.springbootinit.common.ErrorCode;
import com.xingchen.springbootinit.common.ResultUtils;
import com.xingchen.springbootinit.constant.UserConstant;
import com.xingchen.springbootinit.exception.BusinessException;
import com.xingchen.springbootinit.exception.ThrowUtils;
import com.xingchen.springbootinit.model.dto.tenement.TenementAddRequest;
import com.xingchen.springbootinit.model.dto.tenement.TenemenEditRequest;
import com.xingchen.springbootinit.model.dto.tenement.TenementQueryRequest;
import com.xingchen.springbootinit.model.dto.tenement.TenementUpdateRequest;
import com.xingchen.springbootinit.model.entity.Tenement;
import com.xingchen.springbootinit.model.entity.User;
import com.xingchen.springbootinit.model.vo.PostVO;
import com.xingchen.springbootinit.model.vo.TenementVo;
import com.xingchen.springbootinit.service.TenementService;
import com.xingchen.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/lixingchen">程序员鱼皮</a>
 * @from <a href="https://xingchen.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/tenement")
@Slf4j
public class TenementController {

    @Resource
    private TenementService tenementService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param TenementAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTenement(@RequestBody TenementAddRequest TenementAddRequest, HttpServletRequest request) {
        if (TenementAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Tenement tenement = new Tenement();
        BeanUtils.copyProperties(TenementAddRequest, tenement);
        tenementService.validTenement(tenement, true);
        User loginUser = userService.getLoginUser(request);
        tenement.setUserId(loginUser.getId());
        boolean result = tenementService.save(tenement);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newPostId = tenement.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTenement(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Tenement tenement = tenementService.getById(id);
        ThrowUtils.throwIf(tenement == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!tenement.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = tenementService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param tenementUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTenement(@RequestBody TenementUpdateRequest tenementUpdateRequest) {
        if (tenementUpdateRequest == null || tenementUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Tenement tenement = new Tenement();
        BeanUtils.copyProperties(tenementUpdateRequest, tenement);
        // 参数校验
        tenementService.validTenement(tenement, false);
        long id = tenementUpdateRequest.getId();
        // 判断是否存在
        Tenement oldtenement = tenementService.getById(id);
        ThrowUtils.throwIf(oldtenement == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = tenementService.updateById(tenement);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<TenementVo> getTenementVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Tenement tenement = tenementService.getById(id);
        if (tenement == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(tenementService.getTenementVO(tenement, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param tenementQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<TenementVo>> listTenementVOByPage(@RequestBody TenementQueryRequest tenementQueryRequest,
                                                           HttpServletRequest request) {
        long current = tenementQueryRequest.getCurrent();
        long size = tenementQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Wrapper<Tenement> queryWrapper = tenementService.getQueryWrapper(tenementQueryRequest);
        Page<Tenement> tenementPage = tenementService.page(new Page<>(current, size),
                queryWrapper);
        return ResultUtils.success(tenementService.getTenementVOPage(tenementPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param tenementQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<TenementVo>> listMyTenementVOByPage(@RequestBody TenementQueryRequest tenementQueryRequest,
            HttpServletRequest request) {
        if (tenementQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        tenementQueryRequest.setUserId(loginUser.getId());
        long current = tenementQueryRequest.getCurrent();
        long size = tenementQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Tenement> tenementPage = tenementService.page(new Page<>(current, size),
                tenementService.getQueryWrapper(tenementQueryRequest));

        return ResultUtils.success(tenementService.getTenementVOPage(tenementPage, request));
    }

    /**
     * 编辑（用户）
     *
     * @param tenemenEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editTenement(@RequestBody TenemenEditRequest tenemenEditRequest, HttpServletRequest request) {
        if (tenemenEditRequest == null || tenemenEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Tenement tenement = new Tenement();
        BeanUtils.copyProperties(tenemenEditRequest, tenement);
        // 参数校验
        tenementService.validTenement(tenement, false);
        User loginUser = userService.getLoginUser(request);
        long id = tenemenEditRequest.getId();
        // 判断是否存在
        Tenement oldPost = tenementService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldPost.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = tenementService.updateById(tenement);
        return ResultUtils.success(result);
    }

}
