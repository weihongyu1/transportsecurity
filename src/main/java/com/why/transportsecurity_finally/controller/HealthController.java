package com.why.transportsecurity_finally.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查Controller
 *
 * @author why
 * @date 2023/03/26 16:23
 **/
@RestController
@RequestMapping("/ts")
public class HealthController {

    private static final Integer HEALTH = 200;

    @RequestMapping("/health")
    public Integer health() {
        return HEALTH;
    }
}
