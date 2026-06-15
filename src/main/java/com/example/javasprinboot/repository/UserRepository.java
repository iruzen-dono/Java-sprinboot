package com.example.javasprinboot.repository;

import com.example.javasprinboot.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Couche d'acc�s aux donn�es (DAO) pour les utilisateurs.
 * Utilise JdbcTemplate pour ex�cuter les requ�tes SQL.
 */
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * RowMapper : transforme une ligne de r�sultat SQL en objet User.
     */
    private final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            return user;
        }
    };

    // Injection de d�pendance via constructeur
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * R�cup�re tous les utilisateurs.
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY id ASC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * R�cup�re un utilisateur par son ID.
     */
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    /**
     * R�cup�re un utilisateur par son login.
     */
    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, login);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Ajoute un nouvel utilisateur.
     */
    public void save(User user) {
        String sql = "INSERT INTO users (login, password, name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getLogin(), user.getPassword(), user.getName());
    }

    /**
     * Met � jour un utilisateur existant.
     */
    public void update(User user) {
        String sql = "UPDATE users SET login = ?, password = ?, name = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getPassword(), user.getName(), user.getId());
    }

    /**
     * V�rifie si un login existe d�j�.
     */
    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count != null && count > 0;
    }

    /**
     * Supprime un utilisateur par son ID.
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
