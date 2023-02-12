package info.batcloud.wxc.admin.controller.form;

import com.aliyun.oss.model.ListObjectsRequest;

public class OssListForm extends ListObjectsRequest {

    private boolean onlyDirectory;

    public boolean isOnlyDirectory() {
        return onlyDirectory;
    }

    public void setOnlyDirectory(boolean onlyDirectory) {
        this.onlyDirectory = onlyDirectory;
    }
}
