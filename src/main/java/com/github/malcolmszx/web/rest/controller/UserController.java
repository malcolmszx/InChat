package com.github.malcolmszx.web.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.malcolmszx.web.annotation.*;
import com.github.malcolmszx.web.rest.HttpStatus;
import com.github.malcolmszx.web.rest.ResponseEntity;
import com.github.malcolmszx.web.rest.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("")
    @JsonResponse
    public ResponseEntity<User> listUser() {
        // 查询用户
        User user = new User();
        user.setId(1);
        user.setName("Leo");
        user.setAge((short) 18);
        return ResponseEntity.ok().build(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putMethod(@PathVariable("id") int id, @RequestBody String body) {
        // 更新用户
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable int id) {
        // 删除用户
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("")
    public ResponseEntity<?> postMethod(@RequestBody String body) {
        // 添加用户
        JSONObject json = JSONObject.parseObject(body);
        User user = new User();
        user.setId(json.getIntValue("id"));
        user.setName(json.getString("name"));
        user.setAge(json.getShortValue("age"));
        return ResponseEntity.status(HttpStatus.CREATED).build(user);
    }
}
