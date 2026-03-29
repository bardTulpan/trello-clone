package scheduler.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import scheduler.dto.UserTaskSummaryDto;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class UserTaskSummaryRowMapper implements RowMapper<UserTaskSummaryDto> {

    @Override
    public UserTaskSummaryDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        UUID userId = rs.getObject("user_id", UUID.class);
        String email = rs.getString("email");

        Long unfinishedCount = rs.getLong("unfinished_count");
        Long finishedCount = rs.getLong("finished_count");

        List<String> unfinishedTitles = mapArray(rs.getArray("unfinished_titles"));
        List<String> finishedTitles = mapArray(rs.getArray("finished_titles"));

        return new UserTaskSummaryDto(
                userId,
                email,
                unfinishedCount,
                unfinishedTitles,
                finishedCount,
                finishedTitles
        );
    }

    private List<String> mapArray(Array sqlArray) throws SQLException {
        if (sqlArray == null) {
            return Collections.emptyList();
        }

        Object array = sqlArray.getArray();

        if (array instanceof String[]) {
            return Arrays.asList((String[]) array);
        }

        throw new IllegalStateException(
                "Expected SQL array of type String[], but got: " + array.getClass()
        );
    }
}