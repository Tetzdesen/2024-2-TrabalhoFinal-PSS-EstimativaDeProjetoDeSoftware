package com.br.estimativadeprojetodesoftware.repository.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tetzner
 */
public class PerfilHasCampoRepositoryH2 {

    private Connection connection;

    public PerfilHasCampoRepositoryH2(Connection connection) {
        this.connection = connection;
    }

    public void salvar(String perfilId, int campoId, double valorPerfilCampo) {
        String sql = "INSERT INTO perfil_has_campo (perfil_idPerfil, campo_idCampo, valorPerfilCampo) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, perfilId);
            statement.setInt(2, campoId);
            statement.setDouble(3, valorPerfilCampo);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerPorIds(String perfilId, int campoId) {
        String sql = "DELETE FROM perfil_has_campo WHERE perfil_idPerfil = ? AND campo_idCampo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, perfilId);
            statement.setInt(2, campoId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Double> buscarValoresPorPerfil(String perfilId) {
        List<Double> valores = new ArrayList<>();
        String sql = "SELECT valorPerfilCampo FROM perfil_has_campo WHERE perfil_idPerfil = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, perfilId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                valores.add(resultSet.getDouble("valorPerfilCampo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valores;
    }
}
