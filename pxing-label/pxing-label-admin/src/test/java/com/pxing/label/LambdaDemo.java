package com.pxing.label;

public class LambdaDemo {
    public static void main(String[] args) {
        // 用匿名内部类的方式来创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("公众号：Java3y---关注我！");
            }
        }).start();

        // 使用Lambda来创建线程
        new Thread(() -> System.out.println("公众号：Java3y---关注我！")).start();
    }

}
