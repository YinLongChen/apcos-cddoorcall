package com.jinxin.platform.cddoorcall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinxin.platform.cddoorcall.mapper.CddoorcallCallInfoMapper;
import com.jinxin.platform.cddoorcall.pojo.domains.CddoorcallCallInfo;
import com.jinxin.platform.cddoorcall.service.CddoorcallCallInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CddoorcallCallInfo)表服务实现类
 *
 * @author cyl
 * @since 2021-05-17 09:33:24
 */
@Service("cddoorcallCallInfoService")
public class CddoorcallCallInfoServiceImpl implements CddoorcallCallInfoService {
    @Resource
    private CddoorcallCallInfoMapper cddoorcallCallInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CddoorcallCallInfo queryById(String id) {
        return this.cddoorcallCallInfoMapper.queryById(id);
    }

    @Override
    public CddoorcallCallInfo queryByUserId(String id) {
        return cddoorcallCallInfoMapper.queryByUserId(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public PageInfo<CddoorcallCallInfo> queryAllByLimit(int offset, int limit, CddoorcallCallInfo cddoorcallCallInfo) {
        PageHelper.startPage(offset, limit);
        PageInfo<CddoorcallCallInfo> pageInfo = new PageInfo<>(cddoorcallCallInfoMapper.queryAll(cddoorcallCallInfo));
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    @Override
    public CddoorcallCallInfo insert(CddoorcallCallInfo cddoorcallCallInfo) {
        this.cddoorcallCallInfoMapper.insert(cddoorcallCallInfo);
        return cddoorcallCallInfo;
    }

    /**
     * 修改数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    @Override
    public CddoorcallCallInfo update(CddoorcallCallInfo cddoorcallCallInfo) {
        this.cddoorcallCallInfoMapper.update(cddoorcallCallInfo);
        return this.queryById(cddoorcallCallInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.cddoorcallCallInfoMapper.deleteById(id) > 0;
    }


    /**
     * 根据对象查找数据
     *
     * @param cddoorcallCallInfo 实例对象
     * @return 实例对象
     */
    @Override
    public List<CddoorcallCallInfo> queryAll(CddoorcallCallInfo cddoorcallCallInfo) {

        return this.cddoorcallCallInfoMapper.queryAll(cddoorcallCallInfo);
    }
}