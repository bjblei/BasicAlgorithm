package com.example.mavenGit;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.0
 * @auth leibao
 * @date 2019/12/18
 */
public class LockExample {

	private int counter = 1;

	ReentrantLock lock = new ReentrantLock();//用锁来保护临界资源

	public void printChar(char ch) {
		while (counter <= 10) {
			lock.lock();
			if (counter > 10) {
				break;//多一次判定：因为while循环判定的地方没有锁
			}
			try {
				if (counter % 2 == 1 && ('a' == ch || 'A' == ch)) {
					System.out.print("A\t");
					counter++;
				} else if (counter % 2 == 0 && ('b' == ch || 'B' == ch)) {
					System.out.print("B\t");
					counter++;
				} else {
					System.out.println("\n char and counter not matched:will sleep for next try char=" + ch + ",counter=" + counter);
				}
			} catch (Exception ex) {

			} finally {
				lock.unlock();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		LockExample  lockExample = new LockExample();
		//
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				lockExample.printChar('A');
			}
		});

		Thread threadB = new Thread(new Runnable() {
			@Override
			public void run() {
				lockExample.printChar('B');
			}
		});

		threadA.start();
		threadB.start();
	}
}
