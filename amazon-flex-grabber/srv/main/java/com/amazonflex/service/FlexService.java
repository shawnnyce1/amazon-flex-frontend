package com.amazonflex.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlexService {
    
    private WebDriver driver;
    private boolean autoRefreshEnabled = false;
    private String lastLoginEmail;
    private List<Map<String, Object>> blockHistory = new ArrayList<>();
    
    public Map<String, String> login(String email, String password) {
        Map<String, String> response = new HashMap<>();
        
        try {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            
            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            driver.get("https://flex.amazon.com/");
            
            // Human-like delay
            Thread.sleep(2000 + (int)(Math.random() * 3000));
            
            WebElement emailField = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("email"))
            );
            
            // Type slowly like a human
            for (char c : email.toCharArray()) {
                emailField.sendKeys(String.valueOf(c));
                Thread.sleep(100 + (int)(Math.random() * 200));
            }
            
            Thread.sleep(500 + (int)(Math.random() * 1000));
            
            WebElement passwordField = driver.findElement(By.id("password"));
            for (char c : password.toCharArray()) {
                passwordField.sendKeys(String.valueOf(c));
                Thread.sleep(100 + (int)(Math.random() * 200));
            }
            
            Thread.sleep(1000 + (int)(Math.random() * 2000));
            
            WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
            loginButton.click();
            
            Thread.sleep(5000 + (int)(Math.random() * 3000));
            String currentUrl = driver.getCurrentUrl();
            
            if (currentUrl.contains("dashboard") || currentUrl.contains("opportunities")) {
                response.put("status", "success");
                response.put("message", "Login successful");
                response.put("currentUrl", currentUrl);
                lastLoginEmail = email;
            } else {
                response.put("status", "failed");
                response.put("message", "Login failed - check credentials");
                response.put("currentUrl", currentUrl);
            }
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        
        return response;
    }
    
    public Map<String, Object> grabBlocks() {
        return grabBlocksWithRate(0.0);
    }
    
    public Map<String, Object> grabBlocksWithRate(double minRate) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            
            driver.get("https://flex.amazon.com/opportunities");
            
            // Random delay to appear human
            Thread.sleep(3000 + (int)(Math.random() * 4000));
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            boolean blocksFound = driver.findElements(By.className("offer-card")).size() > 0;
            
            response.put("status", "success");
            response.put("blocksFound", blocksFound);
            response.put("message", blocksFound ? "Blocks available!" : "No blocks found");
            response.put("timestamp", LocalDateTime.now().toString());
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        
        return response;
    }
    
    @Scheduled(fixedRate = 120000) // Check every 2 minutes to avoid detection
    public void autoGrabBlocks() {
        if (autoRefreshEnabled && lastLoginEmail != null) {
            try {
                Map<String, Object> result = grabBlocks();
                if ("success".equals(result.get("status")) && (Boolean) result.get("blocksFound")) {
                    Map<String, Object> blockEvent = new HashMap<>();
                    blockEvent.put("timestamp", LocalDateTime.now().toString());
                    blockEvent.put("message", "Blocks found!");
                    blockEvent.put("email", lastLoginEmail);
                    blockHistory.add(blockEvent);
                    
                    System.out.println("ALERT: Blocks found for " + lastLoginEmail);
                }
            } catch (Exception e) {
                System.err.println("Auto-grab error: " + e.getMessage());
            }
        }
    }
    
    public Map<String, Object> toggleAutoRefresh(boolean enabled) {
        Map<String, Object> response = new HashMap<>();
        autoRefreshEnabled = enabled;
        response.put("status", "success");
        response.put("autoRefreshEnabled", autoRefreshEnabled);
        response.put("message", enabled ? "Auto-refresh enabled" : "Auto-refresh disabled");
        return response;
    }
    
    public Map<String, Object> getStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("autoRefreshEnabled", autoRefreshEnabled);
        response.put("lastLoginEmail", lastLoginEmail);
        response.put("blockHistoryCount", blockHistory.size());
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}