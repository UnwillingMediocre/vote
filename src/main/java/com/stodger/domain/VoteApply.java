package com.stodger.domain;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-09 10:17
 */
public class VoteApply {
    private Integer id;
    /**
     * 发送人
     */
    private String sponsor;
    private String description;
    private String title;
    private Integer status;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "VoteApply{" +
                "id=" + id +
                ", sponsor='" + sponsor + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                '}';
    }
}
