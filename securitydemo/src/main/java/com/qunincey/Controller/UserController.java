package com.qunincey.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.qunincey.Dto.User;
import com.qunincey.app.security.social.AppSingUpUtils;
import com.qunincey.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: spingsecurity
 * @description:
 * @author: qiuxu
 * @create: 2019-06-15 21:28
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
	private AppSingUpUtils appSingUpUtils;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request){

        String userId = user.getUsername();
//        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
        appSingUpUtils.doPostSignUp(new ServletWebRequest(request), userId);

    }

    @GetMapping(value = "/me")
    public Object getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getDetails();
    }


    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> queryUserList(@RequestParam(required = false) String username, Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(username, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
        List<User> userList=new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        return userList;
    }

    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id){

        System.out.println("进入getInfo服务");
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user, BindingResult errors){


        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.toString()));
        }
        System.out.println(user);
        user.setId(Long.valueOf("1"));
        return user;
    }

    @PutMapping(value = "/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors){
        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.toString()));
        }
        System.out.println(user);
        user.setId(Long.valueOf("1"));
        return user;
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }


}
