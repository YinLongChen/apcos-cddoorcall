package com.jinxin.platform.cddoorcall.mapper;

import com.jinxin.platform.cddoorcall.pojo.domains.CddoorcallCallInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (CddoorcallCallInfo)表数据库访问层
 *
 * @author cyl
 * @since 2021-05-17 09:33:14
 */
@Mapper
public interface CddoorcallCallInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CddoorcallCallInfo queryById(String id);
    CddoorcallCallInfo queryByUserId(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CddoorcallCallInfo> queryAllByLimit();


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 对象列表
     */
    List<CddoorcallCallInfo> queryAll(CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 新增数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 影响行数
     */
    int insert(CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 修改数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 影响行数
     */
    int update(CddoorcallCallInfo cddoorcallCallInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}