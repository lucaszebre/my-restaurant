package com.example.component;

import org.springframework.stereotype.Component;

@Component
public class ColorUtils {
    
    private static final int factor = 30;
    
    public static int[] hexToRgb(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new int[]{0, 0, 0};
        }
        
        hex = hex.replace("#", "");
        
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        
        return new int[]{r, g, b};
    }
    
    public static String rgbToHex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
    
    public static String getDarkerColor(String hexColor) {
        int[] rgb = hexToRgb(hexColor);
        
        int r = Math.max(0, rgb[0] - factor);
        int g = Math.max(0, rgb[1] - factor);
        int b = Math.max(0, rgb[2] - factor);
        
        return rgbToHex(r, g, b);
    }
    
    public static String getLighterColor(String hexColor) {
        int[] rgb = hexToRgb(hexColor);
        
        int r = Math.min(255, rgb[0] + factor);
        int g = Math.min(255, rgb[1] + factor);
        int b = Math.min(255, rgb[2] + factor);
        
        return rgbToHex(r, g, b);
    }
    
    public static String getComplementaryColor(String hexColor) {
        int[] rgb = hexToRgb(hexColor);
        
        int r = 255 - rgb[0];
        int g = 255 - rgb[1];
        int b = 255 - rgb[2];
        
        return rgbToHex(r, g, b);
    }
    
    public static String getAccentColor(String hexColor) {
        int[] rgb = hexToRgb(hexColor);
        
        int r = Math.min(255, rgb[0] + 30);
        int g = Math.min(255, rgb[1] + 30);
        int b = Math.min(255, rgb[2] + 30);
        
        return rgbToHex(r, g, b);
    }
} 