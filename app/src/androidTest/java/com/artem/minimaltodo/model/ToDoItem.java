package com.artem.minimaltodo.model;

import java.time.LocalDateTime;

public class ToDoItem {

    private String title;
    private String description;
    private LocalDateTime remindAt;

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getRemindAt() {
        return remindAt;
    }

    public static class Builder {

        private String title;
        private String description;
        private LocalDateTime remindAt;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setReminder(LocalDateTime remindAt) {
            this.remindAt = remindAt;
            return this;
        }

        public ToDoItem build() {
            ToDoItem item = new ToDoItem();
            item.title = this.title;
            item.description = this.description;
            item.remindAt = this.remindAt;

            return item;
        }
    }
}
