package info.batcloud.wxc.core.action;

import info.batcloud.wxc.core.action.domain.Action;

public interface ActionInspector<T extends Action> {

    void inspect(T mission);

}
