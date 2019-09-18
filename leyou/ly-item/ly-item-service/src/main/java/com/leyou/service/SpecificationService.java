package com.leyou.service;

import com.leyou.Group;
import com.leyou.Params;
import com.leyou.exception.MyException;
import com.leyou.mapper.ParamMapper;
import com.leyou.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品参数
 */
@Service
public class SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private ParamMapper paramsMapper;

    /**
     * 查询组信息
     * @param id
     * @return
     */
    public List<Group> fidSpecificationByid(Long id){
        Group specification = new Group();
        specification.setCid(id);
        List<Group> list = specificationMapper.select(specification);
        if(list.size() < 0){
            throw new MyException("未查到",400);
        }
        return list;
    }
    /**
     * 添加组信息
     */
    @Transactional
    public void saveGroup(Group group){
        specificationMapper.insert(group);
    }

    /**
     * 删除组信息
     * @param id
     */
    public void deleteGroup(Long id){
        specificationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加参数属性
     * @param params
     */
    public void saveParams(Params params){
        paramsMapper.insert(params);
    }
    /**
     * 查询参数属性
     */
    public List<Params> findParamsByGroupId(Long gid){
        Params params = new Params();
        params.setGroupId(gid);
        List<Params> list = paramsMapper.select(params);
        if(list.size() < 0){
            throw new MyException("未找到",400);
        }
        return list;
    }

    /**
     * 更新参数
     * @param params
     */
    public void updateParam(Params params){
        paramsMapper.updateByPrimaryKey(params);
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Params> findParamsByCid(Long id){
        List<Params> list = paramsMapper.findParamsByCid(id);
        if(list.size() < 0){
            throw  new MyException("未查到",404);
        }
        return list;
    }
}
