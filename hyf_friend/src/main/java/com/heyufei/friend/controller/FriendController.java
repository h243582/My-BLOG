package com.heyufei.friend.controller;

import com.heyufei.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;
    /**
     * 添加好友
     * @param friendUserId 对方用户ID
     * @param LikeType 1：喜欢 0：不喜欢
     * @return
     */
    @RequestMapping(value= "/like/{friendUserId}/{LikeType}",method= RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendUserId, @PathVariable String LikeType){
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }
        //如果是喜欢
        if(LikeType.equals("1")){
            if(friendService.addFriend(claims.getId(), friendUserId)==0){
                return new Result(false, StatusCode.REPERROR,"已经添加过这个好友了");
            }
        }else{
            //不喜欢
            friendService.addNoFriend(claims.getId(), friendUserId);//向不喜欢列表中添加记录
        }
        return new Result(true, StatusCode.OK, "添加成功",claims.getId());
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value="/{friendid}",method=RequestMethod.DELETE)
    public Result remove(@PathVariable String friendid){
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }
        friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
