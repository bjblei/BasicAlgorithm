package com.example.mavenGit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程交替打印ABAB...（共10次）
 * 分析：====》实际上是多线程同步问题 + 共享变量（总打印次数）
 * 线程同步的可能实现方式
 * 1)共享变量
 * 2)线程间通信==》一般用于多个进程（Java是典型的一个进程，多个线程模型，此处使用较少）
 * <p>
 * <p>
 * 共享变量：本题有个10次限制决定了基本上要使用共享变量的方式（次数）
 * <p>
 * 1.1)将共享变量放入两个线程竞争的对象中===》竞争加锁
 * 1.2)AtomicInteger 对象竞争
 * 1.3)锁Lock保护临界资源：共享变量
 * <p>
 * 2.1)线程池调度：多个线程放入线程池（single线程池逐个执行排队的多个线程）
 * 2.2)线程池：定时调度线程池（AB分别调度5次，顺序上交替）===》顺序保证？？？？
 * <p>
 * 3)生产者-消费者模型：BlockingQueue
 * <p>
 * 将当前打印对象（A或B）和次数封装成为一个对象，放入大小为1的BlockingQueue，两个线程AB分别读写，根据读写不同操作来打印不同字符
 * ===>本质上还是共享变量
 * <p>
 * 4）终于用到线程通信：如果是不同进程==》RPC （进程开销比较重）
 * 在进程间通信（MSG）告知对方线程当前次数不是一个好的设计
 * ===》基本不采用这种方式
 *
 * @version 1.0
 * @auth leibao
 * @date 2019/12/12
 */
public class MultiThreadSolution {

	private Integer counter = 1;//1~10

	public synchronized void printChar(char ch) {
		if (counter % 2 == 1 && ('a' == ch || 'A' == ch)) {
			System.out.print("A\t");
			counter++;
		} else if (counter % 2 == 0 && ('b' == ch || 'B' == ch)) {
			System.out.print("B\t");
			counter++;
		}

	}

	public static void main(String[] args) {

		/**
		 * 1 共享变量方式
		 */
//		MultiThreadSolution solution = new MultiThreadSolution();
//
//		Thread threadA = new Thread() {
//			@Override
//			public void run() {
//				while (solution.counter <= 10) {
//					try {
//						solution.printChar('a');
//						Thread.sleep(1000);
//					} catch (Exception ex) {
//						System.out.print("exception at thread-A:ex=" + ex.getMessage());
//					}
//				}
//			}
//		};
//
//		Thread threadB = new Thread() {
//			@Override
//			public void run() {
//				while (solution.counter <= 10) {
//					try {
//						solution.printChar('b');
//						Thread.sleep(1000);
//					} catch (Exception ex) {
//						System.out.print("exception at thread-B:ex=" + ex.getMessage());
//					}
//				}
//			}
//		};

//		threadA.start();
//		threadB.start();
		/**
		 *
		 */
		AtomicInteger printCounter = new AtomicInteger(1);

		Thread threadC = new Thread() {
			@Override
			public void run() {
				int current = 1;
				while ((current= printCounter.get()) <= 10) {
					try {
						if (current % 2 == 1) {
							System.out.print("A\t");
							printCounter.compareAndSet(current, current + 1);
						}
						Thread.sleep(1000);
					} catch (Exception ex) {
						System.out.print("exception at thread-C:ex=" + ex.getMessage());
					}
				}
			}
		};

		Thread threadD = new Thread() {
			@Override
			public void run() {
				int current = 1;
				while ((current= printCounter.get()) <= 10) {
					try {
						if (current % 2 == 0) {
							System.out.print("B\t");
							printCounter.compareAndSet(current, current + 1);
						}
						Thread.sleep(1000);
					} catch (Exception ex) {
						System.out.print("exception at thread-D:ex=" + ex.getMessage());
					}
				}
			}
		};

		threadC.start();
		threadD.start();
	}


}
