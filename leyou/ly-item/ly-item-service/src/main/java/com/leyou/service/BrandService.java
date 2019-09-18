package com.leyou.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.Brand;
import com.leyou.PageResult;
import com.leyou.exception.MyException;
import com.leyou.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;
    //brand/page?key=&page=1&rows=5&sortBy=letter&desc=false
    public PageResult<Brand> findBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key){
        //分页
        PageHelper.startPage(page,rows);
        //条件查询
        Example example = new Example(Brand.class);
        if(!StringUtils.isEmpty(key)){
            example.createCriteria().andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        //排序
        if(!StringUtils.isEmpty(sortBy)){
            String orderByClause = sortBy + (desc ?" DESC":" ASC" );
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    /**
     * 添加品牌
     * @param brand
     * @param categorys
     */
    @Transactional
    public void saveBrand(Brand brand, List<Integer> categorys){
        brandMapper.insert(brand);
        for(int i = 0; i < categorys.size(); i++){
            brandMapper.saveCategoryBrand(categorys.get(i),brand.getId());
        }
    }

    /**
     * 根据id查询品牌信息
     * @param bid
     * @return
     */
    public Brand findBrandById(Integer bid){
        return brandMapper.selectByPrimaryKey(bid);
    }

    /**
     * 更新品牌
     * @param brand
     */
    public void updateBrand(Brand brand){brandMapper.updateByPrimaryKey(brand);}

    /**
     * 根据商品分类id获取品牌信息
     * @param id
     * @return
     */
    public List<Brand> findBrandByCid(Long id){
        List<Brand> list = brandMapper.findBrandByCid(id);
        if(list.size() < 0){
            throw new MyException("未找到",404);
        }
        return list;
    }
}
