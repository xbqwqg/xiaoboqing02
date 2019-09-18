package com.leyou.mapper;

import com.leyou.Stock;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock> {
    @Select("select * from tb_stock where sku_id = #{id}")
    Stock findStockBySkuId(Long id);
}
