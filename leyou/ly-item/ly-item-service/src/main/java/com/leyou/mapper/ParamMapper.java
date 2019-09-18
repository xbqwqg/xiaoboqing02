package com.leyou.mapper;

import com.leyou.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ParamMapper extends Mapper<Params> {
    @Select("Select * from tb_params where cid = #{id}")
    List<Params> findParamsByCid(@Param("id") Long id);
}
