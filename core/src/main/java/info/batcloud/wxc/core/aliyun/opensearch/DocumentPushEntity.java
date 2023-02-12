package info.batcloud.wxc.core.aliyun.opensearch;

public class DocumentPushEntity<T> {

    private T fields;
    private Cmd cmd;

    public T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }

    public Cmd getCmd() {
        return cmd;
    }

    public void setCmd(Cmd cmd) {
        this.cmd = cmd;
    }
}
