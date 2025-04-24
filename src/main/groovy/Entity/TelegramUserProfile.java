package Entity;
import jakarta.persistence.*;
import lombok.*;
import Converter.GenderConverter;
import Converter.LocalDateConverter;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class TelegramUserProfile extends BaseEntity<Integer> {

    @Column(name = "nick_name")
    private String nickName;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate birthday;

    @Convert(converter = GenderConverter.class)
    private Gender gender;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "telegram_id")
    private TelegramUser telegramUser;
}
