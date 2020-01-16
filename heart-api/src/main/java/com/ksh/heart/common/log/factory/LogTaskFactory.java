package com.ksh.heart.common.log.factory;

import com.ksh.heart.common.log.LogManager;
import com.ksh.heart.common.log.state.LogSucceed;
import com.ksh.heart.common.log.state.LogType;
import com.ksh.heart.common.utils.SpringContextHolder;
import com.ksh.heart.system.dao.UserLoginLogMapper;
import com.ksh.heart.system.dao.UserOperationLogMapper;
import com.ksh.heart.system.entity.user.LoginLog;
import com.ksh.heart.system.entity.user.OperationLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    private static UserLoginLogMapper userLoginLogMapper = SpringContextHolder.getBean(UserLoginLogMapper.class);
    private static UserOperationLogMapper userOperationLogMapper = SpringContextHolder.getBean(UserOperationLogMapper.class);

    public static TimerTask loginSuccessLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginLog loginLog = LogFactory.createLoginLog(
                            LogType.LOGIN, userId, null, ip, LogSucceed.SUCCESS);
                    userLoginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginFailLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip, LogSucceed.FAIL);
                try {
                    userLoginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                        LogType.EXIT, userId, null, ip, LogSucceed.SUCCESS);
                try {
                    userLoginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask businessLog(final Long userId, final String businessName, final String clazzName, final String methodName, final String msg, final Long time) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, businessName, clazzName, methodName, msg, LogSucceed.SUCCESS, time);
                try {
                    userOperationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建操作日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Long userId, final String exception) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, exception, LogSucceed.FAIL, null);
                try {
                    userOperationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
