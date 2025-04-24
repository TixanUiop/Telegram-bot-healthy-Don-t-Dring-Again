package Entity

import groovy.transform.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@Entity
@Table(name = "telegram_users")
@CompileStatic
class TelegramUser extends BaseEntity<Integer>{

    @Column(name = "telegram_id")
    private Long telegramId

    @OneToOne(mappedBy = "telegramUser")
    private TelegramUserProfile profile;

    TelegramUser(){}

    TelegramUser(Long telegramId){
        this.telegramId = telegramId
    }

    TelegramUserProfile getTelegramUserProfile() {
        return profile
    }
    void setId(TelegramUserProfile telegramUserProfile) {
        this.profile = telegramUserProfile
    }

    Integer getId() {
        return id
    }
    void setId(Integer id) {
        this.id = id
    }
    Long getTelegramId() {
        return telegramId
    }
    void setTelegramId(Long telegramId) {
        this.telegramId = telegramId
    }
    @Override
    String toString() {
        return "TelegramUser(id=$id, telegramId=$telegramId)"
    }
    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        TelegramUser that = (TelegramUser) o
        return id != null ? id.equals(that.id) : that.id == null && (telegramId != null ? telegramId.equals(that.telegramId) : that.telegramId == null)
    }
    @Override
    int hashCode() {
        return Objects.hash(id, telegramId)
    }

}
