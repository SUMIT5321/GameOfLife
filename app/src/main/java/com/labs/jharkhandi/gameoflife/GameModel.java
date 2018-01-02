package com.labs.jharkhandi.gameoflife;

/**
 * Created by sumit on 2/1/18.
 */

public class GameModel {

    private int rows;
    private int columns;
    private boolean[][] aliveMap;

    public GameModel(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        aliveMap = new boolean[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isAlive(int row, int column) {
        return !isOutOfBound(row, column) && aliveMap[row][column];
    }

    public void makeAlive(int row, int column) {
        if(!isOutOfBound(row, column))
            aliveMap[row][column] = true;
    }

    private boolean isOutOfBound(int row, int column) {
        return row < 0 || row >= rows || column < 0 || column >= columns;
    }

    public void makeDead(int row, int column) {
        if(!isOutOfBound(row, column))
            aliveMap[row][column] = false;
    }

    public boolean willLive(int row, int column) {
        int numOfAliveNeighbours = 0;
        for (int i = row-1; i <= row+1; i++) {
            for (int j = column-1; j <= column+1; j++) {
                if(isAlive(i,j) && !(i==row && j==column)){
                    numOfAliveNeighbours++;
             }
            }
        }
        if(isAlive(row, column)) return numOfAliveNeighbours == 2 || numOfAliveNeighbours == 3;
        return numOfAliveNeighbours == 3;
    }

    /**
     * returns false if next state is same as current state
     */
    public boolean next() {
        boolean isStill = true;
        boolean[][] newMap = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMap[i][j] = willLive(i,j);
                if(aliveMap[i][j] != newMap[i][j]) isStill = false;
            }
        }
        aliveMap = newMap;
        return !isStill;
    }

    /**
     * returns false if from is bigger than to either in width or height
     */
    public static boolean copyModelState(GameModel from, GameModel to){
        if(from.getRows() > to.getRows() || from.getColumns() > to.getColumns()){
            return false;
        }
        for (int i = 0; i < from.getRows(); i++) {
            System.arraycopy(from.aliveMap[i], 0, to.aliveMap[i], 0, from.getColumns());
        }
        return true;
    }

    public void reset(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                aliveMap[i][j] = false;
            }
        }
    }

    public boolean isStill() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (willLive(i,j) != aliveMap[i][j]) return false;
            }
        }
        return true;
    }
}