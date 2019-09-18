package com.leyou.mapper;

import com.leyou.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,List<Long>> {
    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> findCategoryByBid(Long bid);
}
