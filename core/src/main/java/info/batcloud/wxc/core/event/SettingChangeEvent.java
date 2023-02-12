package info.batcloud.wxc.core.event;

import org.springframework.context.ApplicationEvent;

public class SettingChangeEvent extends ApplicationEvent {

    private Type type;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SettingChangeEvent(Object source) {
        super(source);
    }

    public SettingChangeEvent(Object source, Type type) {
        super(source);
        this.type = type;
    }

    public static enum Type {
        SAVE, ACTIVE
    }
}
