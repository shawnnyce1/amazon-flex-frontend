package com.amazonflex.controller;

import com.amazonflex.service.FlexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/flex")
@CrossOrigin(origins = "*")
public class FlexController {

    @Autowired
    private FlexService flexService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return flexService.login(email, password);
    }
    
    @GetMapping("/blocks")
    public Map<String, Object> grabBlocks() {
        return flexService.grabBlocks();
    }
    
    @PostMapping("/blocks/filter")
    public Map<String, Object> grabBlocksWithRate(@RequestBody Map<String, Double> request) {
        double minRate = request.getOrDefault("minRate", 0.0);
        return flexService.grabBlocksWithRate(minRate);
    }
    
    @PostMapping("/auto-refresh")
    public Map<String, Object> toggleAutoRefresh(@RequestBody Map<String, Boolean> request) {
        boolean enabled = request.getOrDefault("enabled", false);
        return flexService.toggleAutoRefresh(enabled);
    }
    
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return flexService.getStatus();
    }
}
