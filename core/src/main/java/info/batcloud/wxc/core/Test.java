package info.batcloud.wxc.core;

import org.apache.commons.io.IOUtils;

import java.net.URL;

public class Test {

    public static class Animal {
        public void say() {
            System.out.println("animal");
        }

        public void doSay() {
            say();
        }
    }

    public static class Dog extends Animal {
        @Override
        public void say() {
            System.out.println("wangwang");
        }
    }

    public static void main(String[] args) {
        String url = "https://api.open.21ds.cn/apiv1/getitemgyurl";
        try {
            IOUtils.toString(new URL(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
