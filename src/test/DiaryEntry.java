package test;

import java.sql.Timestamp;

public class DiaryEntry {
    private int id;
    private String title;
    private String content;
    private Timestamp createdAt;

    public DiaryEntry() {}

    public DiaryEntry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public DiaryEntry(int id, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " (" + createdAt.toString().substring(0, 16) + ")";
    }
}