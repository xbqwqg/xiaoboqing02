package com.leyou.mapper;

import com.leyou.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand,List<Integer>> {
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    void saveCategoryBrand(@Param(value="cid")Integer cid, @Param(value = "bid") Long bid);
    @Select("select b.id,b.image,b.letter,b.`name` from tb_brand b INNER JOIN tb_category_brand c on b.id = c.brand_id where c.category_id =#{id} ")
    List<Brand> findBrandByCid(@Param("id")Long id);
}
