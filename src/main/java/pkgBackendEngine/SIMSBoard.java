package pkgBackendEngine;


import java.util.Random;

public class SIMSBoard {

    private int current_score;
    private CellData[][]  ms_board;
    private boolean gameActive;
    private int ROWS = 9;
    private int COLS = 7;
    private int NUM_MINES = 14;




    public SIMSBoard(int ROWS, int COLS, int NUM_MINES) {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.NUM_MINES = NUM_MINES;
        ms_board = new CellData[ROWS][COLS];
        gameActive = true;
        current_score = 0;

        printBoard();
    }

    public void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (ms_board[row][col] == null){
                    ms_board[row][col] = new CellData(SlSpot.CELL_TYPE.GOLD);
                }
            }
        }

        Random random = new Random();
        int placed_Mine = 0;
        while (placed_Mine < NUM_MINES) {
            int mineRow = random.nextInt(ROWS);
            int mineCol = random.nextInt(COLS);

            if (ms_board[mineRow][mineCol].getType() != SlSpot.CELL_TYPE.MINE){
                ms_board[mineRow][mineCol].setType(SlSpot.CELL_TYPE.MINE);
                placed_Mine++;
            }
        }

        for (int row = 0; row < ROWS; row++) {
            System.out.println("");
            for (int col = 0; col < COLS; col++) {
                if (ms_board[row][col].getType() == SlSpot.CELL_TYPE.MINE){
                    System.out.print("M ");
                }
                else{
                    System.out.print("G ");
                }
            }
        }

        System.out.println();
        countTileScore();
        printCellScore();
    }


    public void countTileScore(){
        int[] x = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] y = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (ms_board[row][col].getType() == SlSpot.CELL_TYPE.MINE){
                    ms_board[row][col].setCell_score(0);
                }
                else{
                    int i=0;
                    int score = 0;
                    while (i < x.length){
                        int xRow = (row + x[i] + ROWS) % ROWS;
                        int yCol = (col + y[i] + COLS) % COLS;
                        if((xRow >= 0) && (xRow < ROWS) && (yCol >= 0) && (yCol < COLS)){
                            if (ms_board[xRow][yCol].getType() == SlSpot.CELL_TYPE.MINE) {
                                score+=10;
                            }
                            else if(ms_board[xRow][yCol].getType() == SlSpot.CELL_TYPE.GOLD){
                                score+=5;
                            }
                        }
                        i++;
                    }
                    ms_board[row][col].setCell_score(score);
                }
            }
        }
    }




    public boolean isGameActive(){
        return gameActive;
    }

    public void printCellScore(){
        for (int row = 0; row < ROWS; row++) {
            System.out.println("");
            for (int col = 0; col < COLS; col++) {
                System.out.printf("%2d ", ms_board[row][col].getCell_score());
            }
        }
    }

    public SlSpot.CELL_STATUS getCellStatus(int rows, int cols){
        return ms_board[ROWS][COLS].getStatus();
    }

    public SlSpot.CELL_TYPE changeCellStatus(int rows, int cols){
        if (ms_board[rows][cols].getStatus() == SlSpot.CELL_STATUS.NOT_EXPOSED){
            ms_board[rows][cols].setStatus(SlSpot.CELL_STATUS.NOT_EXPOSED);
        }
        else{
            ms_board[rows][cols].setStatus(SlSpot.CELL_STATUS.EXPOSED);
        }
        return ms_board[rows][cols].getType();
    }

    public SlSpot.CELL_TYPE getCellType(int rows, int cols){
        return ms_board[rows][cols].getType();
    }

    public int getCurrentScore(){
        return current_score;
    }




}
