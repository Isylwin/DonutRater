package nl.oscar.dpi.donutrater.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonutReview {

    @Id
    @GeneratedValue
    private long id;

    private long clientId;
    private String donutId;
    private LocalDateTime timestamp;
    private boolean isLike;
    private boolean success;

    public DonutReview() {

    }

    public DonutReview(long clientId, String donutId, boolean liked) {
        this.clientId = clientId;
        this.donutId = donutId;
        this.isLike = liked;
    }

    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getDonutId() {
        return donutId;
    }

    public void setDonutId(String donutId) {
        this.donutId = donutId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
