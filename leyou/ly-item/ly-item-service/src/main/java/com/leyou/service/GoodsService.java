package com.leyou.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.*;
import com.leyou.exception.MyException;
import com.leyou.mapper.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    public PageResult<Spu> findGoodsByPage(String key, Boolean saleable, Integer page, Integer rows){
        //开启分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Spu.class);
        if (saleable != null) {
            example.createCriteria().orEqualTo("saleable", saleable);
        }
        if(Strings.isNotBlank(key)){
            example.createCriteria().andLike("title","%"+key+"%");
        }
        //封装
      Page<Spu> pageInfo = (Page<Spu>) spuMapper.selectByExample(example);
        List<Spu> list = change(pageInfo.getResult());
        return new PageResult<Spu>(pageInfo.getTotal(),list);

    }
    @Transactional
    public void saveGoods(SpuBo spuBo){
        spuBo.setValid(true);
        spuBo.setSaleable(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insert(spuBo);  //保存通用属性
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        spuDetailMapper.insert(spuBo.getSpuDetail());  //保存商品详情
        saveStockAndSku(spuBo.getSkus(),spuBo.getId()); //保存库存和sku

    }

    private void saveStockAndSku(List<Sku> skus,Long spuId){
        List<Sku> list = new ArrayList<>();
        for(Sku sku : skus){
            if(!sku.getEnable()){
                continue;
            }
            sku.setSpuId(spuId);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
           skuMapper.insert(sku);  //保存sku



            Stock stock = new Stock();
            stock.setStock(sku.getStock());
            stock.setSkuId(sku.getId());
            stockMapper.insert(stock);
        }
    }

    /**
     * 更新商品
     * @param spuBo
     */
    public void updateSpu(SpuBo spuBo){
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.updateByPrimaryKey(spuBo);  //保存通用属性
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());  //保存商品详情
        updateStockAndSku(spuBo.getSkus(),spuBo.getId()); //保存库存和sku

    }

    private void updateStockAndSku(List<Sku> skus,Long spuId){
        List<Sku> list = new ArrayList<>();
        if(list ==null || list.size() <0){
            throw  new MyException("未找到",404);
        }
        for(Sku sku : skus){
            if(!sku.getEnable()){
                continue;
            }
            skuMapper.delete(sku);  //保存sku
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stockMapper.delete(stock);
        }
        saveStockAndSku(skus,spuId);
    }

    /**
     * 根据商品id查询Spudeatil
     * @return
     */
    public SpuDetail findSpuDetailByid(Long id){
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(id);
        SpuDetail spuDetail1 = spuDetailMapper.selectOne(spuDetail);
        if(spuDetail1 == null){
            throw  new MyException("未找到",404);
        }
        return spuDetail1;
    }
    /**
     * 根据商品id查询Spudeatil
     * @return
     */
    public List<Sku> findSkuById(Long id){
        System.out.println(id);
        List<Sku> sku1 = skuMapper.findSkuBySpuId(id);
        if(sku1 == null ||sku1.size() < 0){
            throw  new MyException("未找到",404);
        }
        for (Sku sku2 : sku1) {
            Stock stock = stockMapper.findStockBySkuId(sku2.getId());
            if(stock == null){
                continue;
            }
            System.out.println(id);
            sku2.setStock(stock.getStock());
        }
        return sku1;
    }


    /**
     * 修改品牌名字
     * @param list
     * @return
     */
    private List<Spu> change(List<Spu> list){
        List<Brand> brands = new ArrayList();
        List<Category> categories01 = new ArrayList<>();
        List<Category> categories02 = new ArrayList<>();
        List<Category> categories03 = new ArrayList<>();

        for (Spu spu: list) {
            brands.add(brandMapper.selectByPrimaryKey(spu.getBrandId()));
            categories01.add(categoryMapper.selectByPrimaryKey(spu.getCid1()));
            categories02.add(categoryMapper.selectByPrimaryKey(spu.getCid2()));
            categories03.add(categoryMapper.selectByPrimaryKey(spu.getCid3()));
        }
        for(int i = 0 ; i < brands.size(); i ++){
            list.get(i).setBname(brands.get(i).getName());
            list.get(i).setCname(categories01.get(i).getName()+"_"+categories02.get(i).getName()+"_"+categories03.get(i).getName());
        }
        return list;
    }

}
