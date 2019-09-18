package com.leyou.controller;

import com.leyou.Brand;
import com.leyou.Category;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @GetMapping("list")
    public ResponseEntity<List<Category>> findByParentId(@RequestParam(name = "pid")Long pid){
        return  ResponseEntity.ok(categoryService.findByParentId(pid));

    }


    /**
     * 修改回显数据
     * http://api.leyou.com:88/api/item/category/bid/1115
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> findBrandById(@PathVariable(name = "bid")Long bid){
        return ResponseEntity.ok(categoryService.findCategoryByBid(bid));
    }
}
