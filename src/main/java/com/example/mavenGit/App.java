package com.example.mavenGit;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 */
public class App {
    private ReentrantLock lock = new ReentrantLock();
    private volatile Integer num = new Integer(0);
    private Condition odd = lock.newCondition();
    private Condition even = lock.newCondition();

    public App() {
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        App app = new App();
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    app.getWithInnerLock();
                }
            }.start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread() {

                @Override
                public void run() {
                    app.putWithInnerLock();
                }
            }.start();
        }
        new Thread() {
            @Override
            public void run() {
                while (app.num < 10) {
                    app.printEven();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                while (app.num < 10) {
                    app.printOdd();
                }
            }
        }.start();
    }

    /**
     * 打印奇数
     */
    public void printOdd() {
        try {
            lock.lock();
            if (num % 2 == 1) {// 奇数
                System.out.println(Thread.currentThread().getName() + ":" + num);
                num++;
                even.signal();
            } else {// 偶数
                try {
                    odd.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印偶数
     */
    public void printEven() {
        try {
            lock.lock();
            if (num % 2 == 0) {// 偶数
                System.out.println(Thread.currentThread().getName() + ":" + num);
                num++;
                odd.signal();
            } else {
                try {
                    even.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } finally {
            lock.unlock();
        }

    }

    public void get() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ":get begin");
            Thread.sleep(4000L);
            System.out.println(Thread.currentThread().getName() + ":get end");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void getWithInnerLock() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ":get begin");
            Thread.sleep(4000L);
            System.out.println(Thread.currentThread().getName() + ":get end");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void put() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ":put begin");
            Thread.sleep(4000L);
            System.out.println(Thread.currentThread().getName() + ":put end");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void putWithInnerLock() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ":put begin");
            Thread.sleep(4000L);
            System.out.println(Thread.currentThread().getName() + ":put end");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
