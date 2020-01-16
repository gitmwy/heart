package com.ksh.heart.system.controller.hospital;

import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.hospital.Hospital;
import com.ksh.heart.system.service.HospitalDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医院列表
 */
@Api(value = "HospitalDetailController", tags = {"医院管理"})
@RequestMapping("/hospital/detail")
@RestController
public class HospitalDetailController {

    @Autowired
    private HospitalDetailService hospitalDetailService;

    @ApiOperation("医院列表")
    @GetMapping(value = "/page/list")
    public R pageList(Hospital hospital) {
        return hospitalDetailService.selectPageList(hospital);
    }
}
