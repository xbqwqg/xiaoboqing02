package com.leyou.mapper;

import com.leyou.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SkuMapper extends Mapper<Sku> {
    @Select("select * from tb_sku where spu_id = #{id}")
    List<Sku> findSkuBySpuId(Long id);
}
