/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mco364;

import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class Coordinate {
    String symbol;
    int x;
    int y;
    int width;
    int height;
    
    public Coordinate(String symbol, int x, int y) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }
    
    public Coordinate (String symblol, int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.symbol = symblol;
    }
    
    public Coordinate (String symbol, int x, int y, int height, boolean isEquals) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.symbol = symbol;
    }
}
