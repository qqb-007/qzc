package info.batcloud.wxc.admin.domain;

import java.util.List;

public class PermissionTreeNode {
    private String id;
    private String name;
    private boolean checked;
    private boolean open = true;
    private List<PermissionTreeNode> children;

    public PermissionTreeNode(String id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<PermissionTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTreeNode> children) {
        this.children = children;
    }
}
