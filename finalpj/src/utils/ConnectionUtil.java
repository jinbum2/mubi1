package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import vo.ResultVO;

public class ConnectionUtil<T extends ResultVO> {
	//제너릭을 활용한 유틸이다. 실제로 키를 넣거나 쿼리를 사용할때는 서비스나 모델에 있다.
	//키값은 서비스에 있다고 보면 된다

    private T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    /*
     * 서브 클래스
     * */
    class ConnectionSet {

        private Connection conn = null;
        private Context context = null;
        private PreparedStatement pstmt = null;
        private ResultSet re = null;

        public Connection getConn() {
            return conn;
        }

        public void setConn(Connection conn) {
            this.conn = conn;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public PreparedStatement getPstmt() {
            return pstmt;
        }

        public void setPstmt(PreparedStatement pstmt) {
            this.pstmt = pstmt;
        }

        public ResultSet getRe() {
            return re;
        }

        public void setRe(ResultSet re) {
            this.re = re;
        }
    }

    /*
     * object 하나만 조회
     * */
    public T getOne(String query, ArrayList<String> orderedParameters)
        throws NumberFormatException, SQLException, NamingException {

        List<T> list = getList(query, orderedParameters);

        return list.size() == 0 ? null : list.get(0);
    }

    /*
     * object 리스트 조회
     * */
    public List<T> getList(String query, ArrayList<String> orderedParameters)
        throws NumberFormatException, SQLException, NamingException {

        ConnectionSet connectionSet = connect();
        verifyDAOInstance(connectionSet);
        connectionSet.pstmt = generatePreparedStatement(connectionSet, query, orderedParameters);
        List<T> list = getPreparedStatement(connectionSet.pstmt);
        close(connectionSet);
        return list;
    }

    /*
     * object 저장(insert/update/soft delete)
     * */
    public void save(String query, ArrayList<String> orderedParameters)
        throws NumberFormatException, SQLException, NamingException {

        ConnectionSet connectionSet = connect();
        verifyDAOInstance(connectionSet);
        connectionSet.pstmt = generatePreparedStatement(connectionSet, query, orderedParameters);
        savePreparedStatement(connectionSet.pstmt);
        close(connectionSet);
    }

    private PreparedStatement generatePreparedStatement(ConnectionSet connectionSet, String query,
        ArrayList<String> orderedParameters)
        throws NumberFormatException, SQLException {

        connectionSet.pstmt = connectionSet.conn.prepareStatement(query);
        for (int inx = 0; inx < orderedParameters.size(); inx++) {
            String value = orderedParameters.get(inx);

            if (value != null && value.matches("[0-9]+")) {
                connectionSet.pstmt.setInt(inx + 1, Integer.parseInt(value));
            } else {
                connectionSet.pstmt.setString(inx + 1, value);
            }
        }
        return connectionSet.pstmt;
    }

    private void verifyDAOInstance(ConnectionSet connectionSet) {
        if (connectionSet.conn == null) {
            throw new NullPointerException("디비 초기화에 실패하였습니다.");
        }
    }

    private List<T> getPreparedStatement(PreparedStatement pstmt) throws SQLException {

        ResultSet rs = pstmt.executeQuery();

        List<T> list = new ArrayList<T>();

        while (rs.next()) {
            T row = (T)model.generateByResultSet(rs);
            list.add(row);
        }
        rs.close();
        return list;
    }

    private void savePreparedStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.execute();
    }

    private ConnectionSet connect() throws SQLException, NamingException {
        ConnectionSet connectionSet = new ConnectionSet();
        connectionSet.context = new InitialContext();
        DataSource dataSource = (DataSource)connectionSet.context.lookup("java:comp/env/jdbc/OracleDB");
        connectionSet.conn = dataSource.getConnection();

        return connectionSet;
    }

    private void close(ConnectionSet connectionSet) {
        try {
            if (connectionSet.re != null)
                connectionSet.re.close();
            if (connectionSet.pstmt != null)
                connectionSet.pstmt.close();
            if (connectionSet.context != null)
                connectionSet.context.close();
            if (connectionSet.conn != null)
                connectionSet.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
