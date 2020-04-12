package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.JdbcBeanValidateApi;
import ru.javawebinar.topjava.web.RootController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User save(User user) {

        JdbcBeanValidateApi.validate(user);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());

            jdbcTemplate.batchUpdate(
                    "insert into user_roles (user_id, role) values(?,?)",
                    user.getRoles(),
                    10,
                    (ps, argument) -> {
                        ps.setInt(1, user.getId());
                        ps.setString(2, argument.name());
                    });

        } else if (namedParameterJdbcTemplate.update(" " +
                " UPDATE users " +
                "    SET name=:name, email=:email, password=:password, " +
                "        registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay" +
                "  WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        String sql = " " +
                " SELECT * FROM users u " +
                " left join user_roles ur on u.id = ur.user_id" +
                " WHERE u.id=?";
        return DataAccessUtils.singleResult(getUserList(sql, id));
    }

    @Override
    public User getByEmail(String email) {
        String sql = " " +
                " SELECT * FROM users u " +
                " left join user_roles ur on u.id = ur.user_id" +
                " WHERE u.email=?";
        return DataAccessUtils.singleResult(getUserList(sql, email));
    }

    @Override
    public List<User> getAll() {
        String sql = " " +
                " SELECT * FROM users u " +
                " left join user_roles ur on u.id = ur.user_id" +
                " WHERE 1=? " +
                " ORDER BY name, email";
        return getUserList(sql, 1);
    }

    public List<User> getUserList(String sql, Object o) {
        Map<Integer, User> mapUsers = new LinkedHashMap<>();
        Map<Integer, List<Role>> mapRoles = new LinkedHashMap<>();
        RowCallbackHandler handler = rs -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Date registered = rs.getDate("registered");
            Boolean enabled = rs.getBoolean("enabled");
            int calories_per_day = rs.getInt("calories_per_day");
            String role = rs.getString("role");

            mapRoles.computeIfAbsent(id, k -> new ArrayList<>());
            mapRoles.get(id).add(Role.valueOf(role));
            mapUsers.computeIfAbsent(id,
                    key -> new User(id, name, email, password, calories_per_day, enabled, registered, null));
        };
        jdbcTemplate.query(sql, handler, o);
        mapUsers.values().forEach(user -> user.setRoles(mapRoles.get(user.getId())));
        return new ArrayList<>(mapUsers.values());
    }
}
