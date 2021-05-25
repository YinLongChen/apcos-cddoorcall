package com.jinxin.platform.cddoorcall.service;

import com.github.pagehelper.PageInfo;
import com.jinxin.platform.cddoorcall.pojo.domains.CddoorcallCallInfo;

import java.util.List;

/**
 * (CddoorcallCallInfo)表服务接口
 *
 * @author cyl
 * @since 2021-05-17 09:33:18
 */
public interface CddoorcallCallInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CddoorcallCallInfo queryById(String id);
    CddoorcallCallInfo queryByUserId(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    PageInfo<CddoorcallCallInfo> queryAllByLimit(int offset, int limit, CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 新增数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    CddoorcallCallInfo insert(CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 修改数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    CddoorcallCallInfo update(CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 根据对象查找数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    List<CddoorcallCallInfo> queryAll(CddoorcallCallInfo cddoorcallCallInfo);


}