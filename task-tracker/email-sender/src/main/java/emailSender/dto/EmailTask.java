package emailSender.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailTask {
    private String recipient;
    private String title;
    private String body;
}
