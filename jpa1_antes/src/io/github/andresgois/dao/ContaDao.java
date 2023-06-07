package io.github.andresgois.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import io.github.andresgois.model.Conta;

public class ContaDao {
    private Connection cnn;

    public ContaDao() {
        try {
            this.cnn = DriverManager.getConnection("mysql://localhost/javaee", "andre", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adiciona(Conta conta) {
        try {
            String sql = "insert into conta(agencia, numero, titular) values (?,?,?)";
            PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setLong(1, conta.getAgencia());
            ps.setLong(2, conta.getNumero());
            ps.setString(3, conta.getTitular());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
