package info.batcloud.wxc.core.action.domain;

public class UserActivationAction extends Action {

    private Long superUserId;

    public Long getSuperUserId() {
        return superUserId;
    }

    public void setSuperUserId(Long superUserId) {
        this.superUserId = superUserId;
    }
}
