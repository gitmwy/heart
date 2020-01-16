package com.ksh.heart.system.controller.hospital;

import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.hospital.Doctor;
import com.ksh.heart.system.service.HospitalDoctorService;
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
 * 医生列表
 */
@Api(value = "HospitalDoctorController", tags = {"医生管理"})
@RequestMapping("/hospital/doctor")
@RestController
public class HospitalDoctorController {

    @Autowired
    private HospitalDoctorService hospitalDoctorService;

    @ApiOperation("医生列表")
    @GetMapping(value = "/page/list")
    public R pageList(Doctor doctor) {
        return hospitalDoctorService.selectPageList(doctor);
    }

    @ApiOperation(value = "获取讲者信息")
    @GetMapping(value = "/info")
    public R info(@ApiParam(name = "hospitalId", value = "医院ID（必填）", example = "1") @RequestParam(required = false) Long hospitalId) {
        Assert.notNull(hospitalId, "医院ID不能为空");
        return hospitalDoctorService.getDoctorByHospitalId(hospitalId);
    }
}
