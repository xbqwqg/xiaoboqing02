package com.leyou;

import javax.persistence.Transient;
import java.util.List;

public class SpuBo extends Spu {
    @Transient
    private SpuDetail spuDetail;
    @Transient
    private List<Sku> skus;

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
