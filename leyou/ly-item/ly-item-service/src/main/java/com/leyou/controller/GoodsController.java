package com.leyou.controller;

import com.leyou.*;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// spu/page?key=&saleable=true&page=1&rows=5
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> findGoodsByPage(
            @RequestParam(name = "key",required = false) String key,
            @RequestParam(name = "saleable") Boolean saleable,
            @RequestParam(name = "page",required = true, defaultValue = "1") Integer page,
            @RequestParam(name = "rows", required = true, defaultValue = "5") Integer rows){

        return ResponseEntity.ok(goodsService.findGoodsByPage(key,saleable,page,rows));
    }

    /**
     * 添加商品信息
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.ok(null);
    }
    ///spu/detail/3
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> findSpuDetailByid(@PathVariable("id")Long id){
        return ResponseEntity.ok(goodsService.findSpuDetailByid(id));
    }

    //sku/list?id=2
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> findSkuById(@RequestParam("id")Long id){
        return ResponseEntity.ok(goodsService.findSkuById(id));
    }

    /**
     * 更新商品
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateSpu(@RequestBody SpuBo spuBo){
        goodsService.updateSpu(spuBo);
        return ResponseEntity.ok(null);
    }
}
