package com.qing.feign;

import com.leyou.PageResult;
import com.leyou.Spu;
import com.leyou.feign.FeginInteface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "item-service")
public interface FeignServer extends FeginInteface {
}
