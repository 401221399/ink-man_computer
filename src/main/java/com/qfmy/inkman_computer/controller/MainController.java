package com.qfmy.inkman_computer.controller;

import com.qfmy.inkman_computer.common.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class MainController {
    @RequestMapping("/")
    @ResponseBody
    public R index (){ return R.ok(); }
}
