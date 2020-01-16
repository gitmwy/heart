package com.ksh.heart.system.controller.user;

import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.dto.PageForm;
import com.ksh.heart.system.service.UserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心
 */
@Api(value = "UserDetailController", tags = {"个人中心"})
@RestController
@RequestMapping("/user/detail")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @ApiOperation("用户信息")
    @GetMapping(value = "/info")
    public R info() {
        return userDetailService.selectInfo();
    }

    @ApiOperation("员工管理")
    @GetMapping(value = "/user/list")
    public R userList(PageForm page) {
        return userDetailService.selectUserList(page);
    }

    @ApiOperation("员工管理详情")
    @GetMapping(value = "/user/info")
    public R userInfo(@ApiParam(name = "id", value = "员工ID（必填）", example = "1") @RequestParam(required = false) Long id) {
        Assert.notNull(id, "员工ID不能为空");
        return userDetailService.selectUserInfo(id);
    }

    @ApiOperation("讲者管理")
    @GetMapping(value = "/doctor/list")
    public R doctorList(PageForm page) {
        return userDetailService.selectDoctorList(page);
    }

    @ApiOperation("讲者管理详情")
    @GetMapping(value = "/doctor/info")
    public R doctorInfo(@ApiParam(name = "id", value = "医生ID（必填）", example = "1") @RequestParam(required = false) Long id) {
        Assert.notNull(id, "医生ID不能为空");
        return userDetailService.selectDoctorInfo(id);
    }

    @ApiOperation("医院管理")
    @GetMapping(value = "/hospital/list")
    public R hospitalList(PageForm page) {
        return userDetailService.selectHospitalList(page);
    }

    @ApiOperation("医院管理详情")
    @GetMapping(value = "/hospital/info")
    public R hospitalInfo(@ApiParam(name = "id", value = "医院ID（必填）", example = "1") @RequestParam(required = false) Long id) {
        Assert.notNull(id, "医院ID不能为空");
        return userDetailService.selectHospitalInfo(id);
    }
}
