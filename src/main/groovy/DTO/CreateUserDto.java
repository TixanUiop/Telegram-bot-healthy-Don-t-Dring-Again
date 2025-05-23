package DTO;

import Entity.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private Long chatId;
    @NotNull
    //TODO Доделать валидацию
    @NotNull
    private String nickname;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private Gender gender;
}
