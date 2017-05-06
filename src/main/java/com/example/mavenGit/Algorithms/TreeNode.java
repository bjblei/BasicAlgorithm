package com.example.mavenGit.Algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeNode {
	public static int addChildrenOpTimes = 0;// 调用addChildren方法的次数
	private static AtomicInteger id = new AtomicInteger();
	//
	private Integer nodeId;
	private TreeNode parent;
	private Object value;
	private List<TreeNode> children;

	public TreeNode(TreeNode par, Object v, List<TreeNode> node) {
		this.nodeId = id.getAndIncrement();
		this.parent = par;
		this.value = v;
		this.children = node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TreeNode[id = ").append(nodeId).append(",parent=").append(null == parent ? 0 : parent.nodeId)
				.append("]");
		return sb.toString();
	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	public static int maxTreeWidth(TreeNode root) {
		if (null == root || null == root.value) {
			return 0;
		}
		if (root.children.isEmpty()) {
			return 1;
		}
		//
		Queue<Object> que = new LinkedList<>();
		Object tail = new Object();// 分隔符==》将不同层次的节点在队列中分割开
		que.add(root);
		que.add(tail);
		int maxWidth = 0;// 最大宽度,最终要返回的结果
		int widthOfThisLevel = 0;
		while (!que.isEmpty()) {
			Object node = que.poll();
			if (null == node) {
				break;
			}
			if (node instanceof TreeNode) {
				widthOfThisLevel++;
				TreeNode nd = (TreeNode) node;
				System.out.print(nd.toString());
				if (null != nd && null != nd.children && !nd.children.isEmpty()) {
					for (TreeNode cd : nd.children) { // 将当前节点所有子节点加入队列
						que.add(cd);
					}
				}
			} else {// 本层遍历结束
				System.out.print("|\n");
				maxWidth = Math.max(maxWidth, widthOfThisLevel);
				if (widthOfThisLevel > 0) {
					que.add(node);
				}
				widthOfThisLevel = 0;
			}
		}
		return maxWidth;
	}

	public static TreeNode addRandomChildren(TreeNode root, int maxOpTimes) {
		if (null == root) {
			return null;
		}
		if (addChildrenOpTimes >= maxOpTimes) {
			return root;
		}
		if (null == root.children || root.children.isEmpty()) {
			Random rd = new Random();
			int childrenNum = rd.nextInt(5) % 5;// 子节点数目：随机0~4
			if (childrenNum == 0) {
				return root;
			}
			List<TreeNode> children = new LinkedList<>();
			for (int j = 0; j < childrenNum; j++) {
				children.add(new TreeNode(root, new Object(), null));
			}
			root.children = children;// 设置children
			addChildrenOpTimes++;
			System.out.println("add time=" + addChildrenOpTimes + ",childrenNum=" + childrenNum);
			return root;// 已达最多操作次数
		}
		// 子节点列表非空，递归调用
		List<TreeNode> currentChildren = root.children;
		for (int i = 0; i < currentChildren.size(); i++) {
			TreeNode node = currentChildren.get(i);
			TreeNode nd = addRandomChildren(node, maxOpTimes);
			if (null != nd) {
				currentChildren.set(i, nd);// 新子树替换
			}
		}
		return root;
	}

	public static void main(String[] args) {
		int maxLevel = 10;
		TreeNode root = new TreeNode(null, new Object(), null);
		addChildrenOpTimes = 0;
		TreeNode tree = TreeNode.addRandomChildren(root, maxLevel);
		while (addChildrenOpTimes < maxLevel) {
			tree = TreeNode.addRandomChildren(tree, maxLevel);
		}
		int maxWidth = TreeNode.maxTreeWidth(tree);

		System.out.print("maxWIdth = " + maxWidth);
	}
}
