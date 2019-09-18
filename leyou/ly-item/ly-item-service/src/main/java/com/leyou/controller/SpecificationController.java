package com.leyou.controller;

import com.leyou.Group;
import com.leyou.Params;
import com.leyou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
//http://api.leyou.com:88/api/item/spec/groups/76
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * http://api.leyou.com:88/api/item/spec/3
     * @param id
     * @return
     */
    @GetMapping("groups/{id}")
    public ResponseEntity<List<Group>> finSpecificationById(@PathVariable("id") Long id){
        return ResponseEntity.ok(specificationService.fidSpecificationByid(id));
    }

    /**
     * 添加分组
     * @param group
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody Group group){
        specificationService.saveGroup(group);
        return ResponseEntity.ok(null);
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id){
       specificationService.deleteGroup(id);
        return ResponseEntity.ok(null);
    }
    //Request URL:http://api.leyou.com:88/api/item/spec/param
    @PostMapping("param")
    public ResponseEntity<Void> saveParams(@RequestBody Params params){
        specificationService.saveParams(params);
        return ResponseEntity.ok(null);
    }
    //params?gid=1
    @GetMapping("params")
    public ResponseEntity<List<Params>> findParamsByGroupId(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specificationService.findParamsByGroupId(gid));
    }
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody Params params){
        specificationService.updateParam(params);
        return ResponseEntity.ok(null);
    }
    //api.leyou.com:88/api/item/spec/params?cid=3

    @GetMapping("param")
    public ResponseEntity<List<Params>> findParamsByCid(@RequestParam("cid") Long id){
        return ResponseEntity.ok(specificationService.findParamsByCid(id));
    }
}
