package vo;

import java.sql.ResultSet;
import java.sql.SQLException;

//NOTICE: 목록 조회시 수량 체크
public class CountInfo extends ResultVO {
    private int totalCount; // 식별자

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public ResultVO generateByResultSet(ResultSet rs) throws SQLException {
        CountInfo instance = new CountInfo();
        instance.setTotalCount(rs.getInt("total_count"));

        return instance;
    }

    @Override
    public String toString() {
        return "CountInfo {totalCount:" + totalCount + "}";
    }

}
