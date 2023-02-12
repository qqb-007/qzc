package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.service.TmpFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TmpFileServiceImpl implements TmpFileService {

    @Value("${file.tmp.dir}")
    private String tmpDir;

    @Override
    public File createFile(String path) {
        File folder = new File(tmpDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return new File(folder, path);
    }
}
