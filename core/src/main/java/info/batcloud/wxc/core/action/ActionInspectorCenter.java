package info.batcloud.wxc.core.action;

import info.batcloud.wxc.core.action.domain.Action;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionInspectorCenter implements ActionInspector {

    private Map<Class<? extends Action>, ActionInspector> missionInspectorMap = new HashMap<>();

    public <T extends Action> void registerInspector(Class<T> clazz, ActionInspector<T> actionInspector) {
        missionInspectorMap.put(clazz, actionInspector);
    }


    @Override
    public void inspect(Action action) {
        ActionInspector inspector = missionInspectorMap.get(action.getClass());
        if (inspector != null) {
            inspector.inspect(action);
        }
    }

}
