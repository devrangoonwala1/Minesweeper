package pkgBackendEngine;

public class CellData {
    private CellData[][] ms_board;
    private SlSpot.CELL_STATUS status;
    private SlSpot.CELL_TYPE type;
    private int cell_score;

    public CellData(SlSpot.CELL_TYPE type){
        this.type = type;
        this.cell_score = 0;
    }

    public SlSpot.CELL_TYPE getType(){
        return type;
    }

    public void setType(SlSpot.CELL_TYPE type){
        this.type = type;
    }

    public int getCell_score(){
        return cell_score;
    }

    public void setCell_score(int cell_score){
        this.cell_score = cell_score;
    }

    public SlSpot.CELL_STATUS getStatus(){
        return status;
    }

    public void setStatus(SlSpot.CELL_STATUS status){
        this.status = status;
    }
}


