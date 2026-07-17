package de.holarse.web.controller.commands;

import java.time.LocalDateTime;

public class ToastCommand {
    
    private String topic;
    private LocalDateTime time;
    private String message;

    public String getTopic() {
        return topic;
    }
    public void setTopic(final String topic) {
        this.topic = topic;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(final LocalDateTime time) {
        this.time = time;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }

    public static ToastCommand createNow(final String topic, final String message) {
        final ToastCommand toast = new ToastCommand();
        toast.setTopic(topic);
        toast.setMessage(message);
        toast.setTime(LocalDateTime.now());
        return toast;
    }

}
