package info.batcloud.wxc.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/enum")
public class EnumController {

    @GetMapping("/valuesMap/{type}")
    @ResponseBody
    public Map valuesMap(@PathVariable String type, @RequestParam(required = false) String titleField,
                         @RequestParam(required = false) String igNames) throws ClassNotFoundException, NoSuchFieldException {
        Class<? extends Enum> clazz = (Class<? extends Enum>) Class.forName("info.batcloud.wxc." + type);
        Enum[] enums = clazz.getEnumConstants();
        Map<String, Object> map = new HashMap<>();
        if(titleField == null){
            titleField = "title";
        }
        List<String> igNameList = null;
        if(StringUtils.isNotEmpty(igNames)){
            igNameList = Arrays.asList(igNames.split(","));
        }
        for (Enum anEnum : enums) {
            String name = anEnum.name();
            if(igNameList != null){
                if(igNameList.contains(name)){
                    continue;
                }
            }
            try {
                Method getTitleMethod = anEnum.getClass().getMethod("getTitle", null);
                try {
                    map.put(name, getTitleMethod.invoke(anEnum, null));
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            map.put(name, ReflectionUtils.getField(anEnum.getClass().getField(titleField), anEnum));
        }

        return map;
    }

}
