package scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserTaskSummaryDto {
    private UUID userId;
    private String email;

    private Long unfinishedCount;
    private List<String> unfinishedTitles;

    private Long finishedCount;
    private List<String> finishedTitles;
}