package com.itsm.storage;

import com.itsm.entity.Client;
import com.itsm.entity.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductStorage implements Storage<Product> {
    private final String url;
    private final StateStorage stateStorage;

    public ProductStorage(String url, StateStorage stateStorage) {
        this.url = url;
        this.stateStorage = stateStorage;
    }

    @Override
    public Product get(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products WHERE id=" + id);
        Product product = null;
        if(rs.first()){
            product = new Product(id,rs.getString("name"),stateStorage.get(rs.getLong("state_id")));
        }
        statement.close();
        connection.close();
        if (product == null){
            throw new SQLException("No such element in a database");
        }
        return product;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products");
        List<Product> list= new LinkedList<>();
        while (rs.next()){
            Product product = new Product(rs.getLong("id"),rs.getString("name"),stateStorage.get(rs.getLong("state_id")));
            list.add(product);
        }
        return list;
    }

    @Override
    public void add(Product o) throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO products (name,state_id) VALUES (?, ?)");
        ps.setString(1,o.getName());
        ps.setLong(2,o.getState().getId());
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    public void update(Product o) throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("UPDATE products SET name = ?, state_id = ? WHERE id = ?");
        ps.setString(1,o.getName());
        ps.setLong(2,o.getState().getId());
        ps.setLong(3,o.getId());
        ps.execute();
        ps.close();
        connection.close();

    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("DELETE FROM products WHERE `id` = ?");
        ps.setLong(1,id);
        ps.execute();
        ps.close();
        connection.close();
    }

    @Override
    public boolean contains(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("SELECT name FROM products WHERE id = ?");
        ps.setLong(1,id);
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        boolean b = resultSet.first();
        ps.close();
        connection.close();
        return b;
    }
}
