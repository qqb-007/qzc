package info.batcloud.wxc.core.action;

import info.batcloud.wxc.core.action.domain.Action;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class AbstractActionInspector<T extends Action> implements ActionInspector<T> {

    @Inject
    private ActionInspectorCenter missionInspectorCenter;

    @PostConstruct
    public void init() {
        missionInspectorCenter.registerInspector(getMissionClass(), this);
    }

    public abstract Class<T> getMissionClass();

}
