package scheduler.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailTask {
    private String recipient;
    private String title;
    private String body;
}
