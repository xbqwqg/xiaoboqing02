package com.leyou.controller;

import com.leyou.Brand;
import com.leyou.PageResult;
import com.leyou.exception.MyException;
import com.leyou.service.BrandService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

//http://api.leyou.com:88/api/item/brand/cid/4
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 条件查询品牌信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> findBrandByPage(
            @RequestParam(name = "page",defaultValue = "1",required = true) Integer page,
            @RequestParam(name = "rows",defaultValue = "5",required = true) Integer rows,
            @RequestParam(name = "sortBy",defaultValue = "letter",required = true) String sortBy,
            @RequestParam(name = "desc", required = false) Boolean desc,
            @RequestParam(name = "key",required = false) String key) {
        PageResult<Brand> result = brandService.findBrandByPage(page, rows, sortBy, desc, key);
        if (result == null || result.getItems().size() == 0) {
            throw new MyException("没有找到", 400);
        }
        System.out.println("你好！！！！！！！！！！");
        return ResponseEntity.ok(result);

    }

    /**
     * 添加品牌信息
     * @return
     */
    @RequestMapping
    public ResponseEntity<Void> saveBrand(Brand brand) {
        brandService.saveBrand(brand,brand.getCids());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    /**
     * 上传图片
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file,HttpServletRequest request){
            System.out.println("你牛逼");
            if(!file.isEmpty()){
            String scheme = request.getScheme();
            String storePath= "F:\\nginx-1.12.2\\html";
            String fileName = System.currentTimeMillis()+""+file.getOriginalFilename();
            File filepath = new File(storePath, fileName);
            if (!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(storePath + File.separator + fileName));
            } catch (Exception e){
                throw new MyException("上传图片失败",400);
            }
            return ResponseEntity.status(200).body("http://localhost:88/"+fileName);
        }else{
            throw new MyException("上传图片失败",400);
        }
    }

    /**
     * 更新
     * @param brand
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand){
        brandService.updateBrand(brand);
        return ResponseEntity.ok(null);
    }

    /**
     * 根据商品分类id查询品牌信息
     * @param id
     * @return
     */
    @GetMapping("cid/{id}")
    public ResponseEntity<List<Brand>> findBrandByCid(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.findBrandByCid(id));
    }
}
