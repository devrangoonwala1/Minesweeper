package pkgBackendEngine;


public class SIMSBoard {

    private int current_score;
    private CellData[][]  ms_board;
    private boolean gameActive;
    private int ROWS;
    private int COLS;
    private int NUM_MINES;




    public SIMSBoard() {

    }

    public void printBoard() {

    }




    public boolean  isGameActive(){

        return false;
    }

    public void printCellScore(){

    }

    public SlSpot.CELL_STATUS getCellStatus(int ROWS, int COLS){
        return null;
    }

    public SlSpot.CELL_TYPE changeCellStatus(int ROWS, int COLS){
        return null;
    }

    public SlSpot.CELL_TYPE getCellType(int x, int y){
        return null;
    }

    public int getCurrentScore(){
        return 0;
    }





}
