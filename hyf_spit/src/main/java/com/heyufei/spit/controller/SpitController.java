package com.heyufei.spit.controller;

import com.heyufei.spit.pojo.Spit;
import com.heyufei.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOne(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /**
     * 增加
     *
     * @param spit
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param spit
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级ID查询吐槽分页数据
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentId}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }


    /**
     * 吐槽点赞
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String spitId, HttpServletRequest request){
        //判断用户是否点过赞

        final String authHeader = request.getHeader("Authorization");
        final String token = authHeader.substring(8);
        Claims claims = jwtUtil.parseJWT(token);
        System.out.println("spitId:"+claims.getId());

        String userid = claims.getId();

        if(redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitId)!=null){
            return new Result(false,StatusCode.REPERROR,"你已经点过赞了");
        }

        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set( "thumbup_"+userid+"_"+ spitId,"1");
        return new Result(true,StatusCode.OK,"点赞成功",("userid: "+ userid+ ",  spitId: "+spitId));
    }
}
