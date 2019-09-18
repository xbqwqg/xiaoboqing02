package com.leyou.feign;

import com.leyou.PageResult;
import com.leyou.Spu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FeginInteface {
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> findGoodsByPage(
            @RequestParam(name = "key",required = false) String key,
            @RequestParam(name = "saleable") Boolean saleable,
            @RequestParam(name = "page",required = true, defaultValue = "1") Integer page,
            @RequestParam(name = "rows", required = true, defaultValue = "5") Integer rows);
}
