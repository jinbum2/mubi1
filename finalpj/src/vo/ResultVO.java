package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultVO {

    private int totalCount = 0;
    
    private int currentPage = 0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public ResultVO() {
    }

    public abstract ResultVO generateByResultSet(ResultSet rs) throws SQLException;
}
