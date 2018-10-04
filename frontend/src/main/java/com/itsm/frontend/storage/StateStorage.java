package com.itsm.frontend.storage;

import com.itsm.common.entity.State;
import com.itsm.frontend.auditor.Auditable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StateStorage implements Storage<State> {
    private final DataSource dataSource;

    public StateStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public State get(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM states WHERE id=" + id);
        State state = null;
        if(rs.first()){
            state = new State(id,rs.getByte("code"),rs.getString("name"));
        }
        statement.close();
        connection.close();
        if (state == null){
            throw new SQLException("No such element in a database");
        }
        return state;
    }

    @Override
    public List<State> getAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM states");
        List<State> list= new LinkedList<>();
        while (rs.next()){
            State state = new State(rs.getLong("id"),rs.getByte("code"),rs.getString("name"));
            list.add(state);
        }
        connection.close();
        return list;
    }

    @Override
    @Auditable
    public void add(State o) throws SQLException{
        Connection connection = dataSource.getConnection();;
        PreparedStatement ps = connection.prepareStatement("INSERT INTO states (`name`, `code`) VALUES (?, ?)");
        ps.setString(1,o.getName());
        ps.setInt(2,o.getCode());
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    @Auditable
    public void update(State o) throws SQLException{
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE states SET name = ?, code = ? WHERE id = ?");
        ps.setString(1,o.getName());
        ps.setInt(2,o.getCode());
        ps.setLong(3,o.getId());
        ps.execute();
        ps.close();
        connection.close();

    }

    @Override
    @Auditable
    public void delete(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM states WHERE `id` = ?");
        ps.setLong(1,id);
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    public boolean contains(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT name FROM states WHERE id = ?");
        ps.setLong(1,id);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        boolean b = resultSet.first();
        ps.close();
        connection.close();
        return b;
    }


}
