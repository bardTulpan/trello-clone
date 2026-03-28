package scheduler.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scheduler.dto.UserTaskSummaryDto;
import scheduler.mapper.UserTaskSummaryRowMapper;

import java.util.*;

@Repository
@AllArgsConstructor
public class MainRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserTaskSummaryRowMapper userTaskSummaryRowMapper;

    public List<UserTaskSummaryDto> getTasks() {
        String sql = """
                SELECT
                    u.id as user_id,
                    u.email,
                
                    COUNT(*) FILTER (WHERE t.completed = false) as unfinished_count,
                    COALESCE(
                        ARRAY_AGG(t.title) FILTER (WHERE t.completed = false),
                        '{}'
                    ) as unfinished_titles,
                
                    COUNT(*) FILTER (
                        WHERE t.completed = true\s
                        AND t.updated_at >= now() - interval '1 day'
                    ) as finished_count,
                    COALESCE(
                        ARRAY_AGG(t.title) FILTER (
                            WHERE t.completed = true\s
                            AND t.updated_at >= now() - interval '1 day'
                        ),
                        '{}'
                    ) as finished_titles
                
               FROM app_users u
                LEFT JOIN tasks t ON t.user_id = u.id
                GROUP BY u.id, u.email;
        """;

        return jdbcTemplate.query(sql, userTaskSummaryRowMapper);

    }
}

//прикрутить миграции к backend
//посмотреть что в backend не реализовано
//начать тесты писать
//начать писать emailSender
//прикрутить SMTP сервер
//сменить язык и вынести всё в конфиги
//прикрутить отправку в топик
