package nl.oscar.dpi.donutrater.hiscore.javafx;

import nl.oscar.dpi.donutrater.library.domain.DonutReview;

public class DisplayReview {

    private DonutReview review;

    public DisplayReview(DonutReview review) {
        this.review = review;
    }

    public DonutReview getReview() {
        return review;
    }

    public void setReview(DonutReview review) {
        this.review = review;
    }

    public Long getClientId() {
        return this.review.getClientId();
    }

    public String getDonutId() {
        return this.review.getDonutId();
    }

    public Boolean getLike() {
        return this.review.isLike();
    }

    public String getTimestamp() {
        return this.review.getTimestamp().toLocalTime().toString();
    }

    public Boolean getSuccess() {
        return this.review.isSuccess();
    }
}
