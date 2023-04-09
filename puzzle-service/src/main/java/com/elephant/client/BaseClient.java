package com.elephant.client;

import com.cunw.framework.exception.BusinessException;

import javax.annotation.Nonnull;

/**
 * Client基类
 * @author dengchengwei
 */
public abstract class BaseClient {

    /**
     * 检查异常
     *
     * @param resultVO 结果
     */
    private <T> void checkWithException(@Nonnull ResultVO<T> resultVO) {
        if (!resultVO.isSucceed()) {
            throw new BusinessException(resultVO.getMessage());
        }
    }

    /**
     * 获取数据
     *
     * @param resultVO 结果
     * @return T 数据
     */
    protected <T> T getDataWithException(@Nonnull ResultVO<T> resultVO) {
        checkWithException(resultVO);
        return resultVO.getData();
    }



}
