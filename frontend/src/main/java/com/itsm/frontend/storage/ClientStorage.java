package com.itsm.frontend.storage;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.State;
import com.itsm.frontend.auditor.Auditable;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class ClientStorage implements Storage<Client> {

    private final String url;
    private final Storage<State> stateStorage;

    public ClientStorage(String url, Storage<State> stateStorage) {
        this.url = url;
        this.stateStorage = stateStorage;
    }

    @Override
    public Client get(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM clients WHERE id=" + id);
        Client client = null;
        if(rs.first()){
            client = new Client(id,rs.getString("name"),rs.getString("phone"),stateStorage.get(rs.getLong("state_id")));
        }
        statement.close();
        connection.close();
        if (client == null){
            throw new SQLException("No such element in a database");
        }
        return client;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM clients");
        List<Client> list= new LinkedList<>();
        while (rs.next()){
            Client client = new Client(rs.getLong("id"),rs.getString("name"),rs.getString("phone"),stateStorage.get(rs.getLong("state_id")));
            list.add(client);
        }
        return list;

    }

    @Override
    @Auditable
    public void add(Client o) throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO clients (name, phone,state_id) VALUES (?, ?, ?)");
        ps.setString(1,o.getName());
        ps.setString(2,o.getPhone());
        ps.setLong(3,o.getState().getId());
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    @Auditable
    public void update(Client o) throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("UPDATE clients SET name = ?, phone = ?, state_id = ? WHERE id = ?");
        ps.setString(1,o.getName());
        ps.setString(2,o.getPhone());
        ps.setLong(3,o.getState().getId());
        ps.setLong(4,o.getId());
        ps.execute();
        ps.close();
        connection.close();

    }

    @Override
    @Auditable
    public void delete(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("DELETE FROM clients WHERE `id` = ?");
        ps.setLong(1,id);
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    public boolean contains(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("SELECT name FROM clients WHERE id = ?");
        ps.setLong(1,id);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        boolean b = resultSet.first();
        ps.close();
        connection.close();
        return b;
    }
}
