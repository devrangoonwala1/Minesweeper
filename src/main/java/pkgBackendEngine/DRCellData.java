/**
 * Name: Dev Rangoonwala
 * Date: 12/7/24
 * Class: csc133 - 08
 * Description: This is the DRCellData class which holds the data for status type cell_score and ms_board.
 */

package pkgBackendEngine;

public class DRCellData {
    private DRCellData[][] ms_board;
    private DRSpot.CELL_STATUS status;
    private DRSpot.CELL_TYPE type;
    private int cell_score;

    public DRCellData(DRSpot.CELL_TYPE type){
        this.status = DRSpot.CELL_STATUS.NOT_EXPOSED;
        this.type = type;
        this.cell_score = 0;
    }

    public DRCellData[][] getMs_board() {
        return ms_board;
    }

    public void setMs_board(DRCellData[][] ms_board) {
        this.ms_board = ms_board;
    }

    public DRSpot.CELL_TYPE getType(){
        return type;
    }

    public void setType(DRSpot.CELL_TYPE type){
        this.type = type;
    }

    public int getCell_score(){
        return cell_score;
    }

    public void setCell_score(int cell_score){
        this.cell_score = cell_score;
    }

    public DRSpot.CELL_STATUS getStatus(){
        return status;
    }

    public void setStatus(DRSpot.CELL_STATUS status){
        this.status = status;
    }
}


