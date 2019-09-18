package com.leyou.service;

import com.leyou.Category;
import com.leyou.exception.MyException;
import com.leyou.mapper.CategoryMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<Category> findByParentId(Long pid){
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        if(list.isEmpty() || list.size() == 0){
            throw new MyException("商品分类找不到",404);
        }
        return list;
    }

    public List<Category> findCategoryByBid(Long bid){
        return categoryMapper.findCategoryByBid(bid);
    }

}
