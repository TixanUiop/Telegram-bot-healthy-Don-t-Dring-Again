package DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTelegramUser {
    private Long chatId;
}
