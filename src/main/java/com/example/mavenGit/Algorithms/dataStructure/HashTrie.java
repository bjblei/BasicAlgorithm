package com.example.mavenGit.Algorithms.dataStructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class HashTrie {
	/**
	 * 字典树一般有两种用处：
	 * 1:不需要count字段进行统计，指定固定字典元素集合（无重复元素），用于判定
	 * 1.a 某个单词word是否在给定字典中
	 * 1.b 找出给定prefix对应单词（如搜索框自动补齐功能）
	 * 1.c 给定字符串text中能拆出来的单词（按字典）
	 * 结论：已知固定字典（可提前指定，并且不变），用于判定String是否符合该字典==》字典做为评判标准
	 * 对字典的增、删是构建字典的预操作，目的是用这个构建后的字典比对字符串（查字典才是用户想要的操作）
	 * 2:需要count字段进行统计，一般给定一段文字（words可以重复）按单词逐个插入字典：
	 * 2.a 用于统计不同单词频率，进行排序等操作
	 * 3.b 简化已知文字或者字符串组合到字典中
	 * 结论：已知单词，字典可为空（开始不需要字典作为评判标准），动态将多个单词放入字典中==>字典作为处理字符串容器
	 * 对字典的增、删操作就是处理字符串或者单词的过程、手段，统计字段count才是
	 * 处理完字符串（或单词)后想要的结果
	 * @author leibao
	 *
	 */
	static class HashTriNode {
		private char value = '/';// 默认root字符
		private int count = 0;// ??包含该字符的单词个数(用于统计整篇文章)
		private boolean isLeaf;
		private HashMap<Character, HashTriNode> children;

		public HashTriNode() {
			children = new HashMap<Character, HashTriNode>();
		}

		public HashTriNode(char val) {
			this();
			value = val;
		}

		public HashTriNode(char val, boolean leaf) {
			this(val);
			this.isLeaf = leaf;
		}

		/**
		 * 
		 * @param node
		 * @return
		 */
		public static HashSet<String> getWordsInSubTree(HashTriNode node) {
			// 只返回subTree里面的字符串，不包含自身节点的char
			if (null == node || node.children.isEmpty()) {
				return null;
			}
			HashSet<String> words = new HashSet<>();
			// 有子节点，递归调用
			for (char ch : node.children.keySet()) {
				HashTriNode nd = node.children.get(ch);
				HashSet<String> set = getWordsInSubTree(nd);
				if (null == set || set.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					sb.append(ch);
					words.add(sb.toString());
					continue;
				}
				for (String wd : set) {
					StringBuilder sb = new StringBuilder();
					sb.append(ch).append(wd);
					words.add(sb.toString());
				}
			}
			return words;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}
	}

	private HashTriNode root;

	public HashTrie() {
		root = new HashTriNode();
	}

	/**
	 * 
	 * @param word
	 * @return
	 */
	public boolean containsInDic(String word) {
		if (null == root || null == word || word.trim().equals("")) {
			return false;
		}
		HashTriNode node = searchNode(word);
		return null != node && node.isLeaf;
	}

	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public boolean startsWith(String prefix) {
		if (null == root || null == prefix || prefix.trim().equals("")) {
			return false;
		}
		HashTriNode node = searchNode(prefix);
		return null != node;
	}

	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public HashSet<String> getDicWordsBeginWith(String prefix) {
		HashSet<String> list = new HashSet<>();
		HashTriNode node = searchNode(prefix);
		if (null == node) {
			return list;
		}
		if (node.isLeaf) {
			list.add(prefix);// prefix本身也是单词
		}
		if (node.children.isEmpty()) {
			return list;
		}
		// String set from subTree
		HashSet<String> set = HashTriNode.getWordsInSubTree(node);
		for (String st : set) {
			list.add(prefix + st);
		}
		return list;
	}

	/**
	 * 插入新字符串
	 * 
	 * @param word
	 */
	public void insert(String word) {
		if (null == root || null == word || word.trim().equals("")) {
			return;
		}
		HashMap<Character, HashTriNode> children = root.children;
		// 逐个字符插入
		HashTriNode node = null;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (children.containsKey(ch)) {
				node = children.get(ch);
			} else {
				node = new HashTriNode(ch);
				children.put(ch, node);
			}
			children = node.children;
			if (i == word.length() - 1) {
				node.isLeaf = true;
				node.count += 1;// 单词统计
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String delete(String word) {
		HashTriNode node = searchNode(word);
		if (null == node || !node.isLeaf) {
			return null;// 该单词不存在
		}
		node.count -= 1;// 删除之前必然>=1
		if (node.count > 0) {
			return word;
		}
		// word末端节点没有挂载其他节点
		if (word.length() == 1) {// parent 为root

			HashTriNode deleted = root.children.remove(node.value);
			if (null != deleted) {
				return word;
			}
		}
		// word.length>1
		int deleteCount = 0;
		for (int i = word.length() - 1; i > 0; i--) {
			if (!node.children.isEmpty()) {// node.isLeaf ||
				if (deleteCount == 0) {
					node.isLeaf = false;// 标记改变替代真正删除(还有子节点，不可真正删除)
				}
				return word;
			}
			String subWord = word.substring(0, i);// 从word.length()-1开始，少查询一次
			HashTriNode parent = searchNode(subWord);
			if (parent.children.containsKey(node.value)) {
				parent.children.remove(node.value);// 删除最后的节点
				deleteCount++;
			}
			node = parent;// 末端节点上移
		}
		return word;
	}

	public void outPut() {
		Queue<Object> queue = new LinkedList<Object>();
		queue.offer(root);
		Object tail = new Object();
		queue.offer(tail);
		while (!queue.isEmpty()) {
			Object obj = queue.poll();
			if (!(obj instanceof HashTriNode)) {
				if (!queue.isEmpty()) {
					queue.offer(tail);
				}
				System.out.print("\n");
				continue;
			}
			HashTriNode node = (HashTriNode) obj;
			System.out.print(node.toString());
			if (!queue.isEmpty() && queue.peek() instanceof HashTriNode) {
				System.out.print("--");
			}
			if (null != node.children && !node.children.isEmpty()) {
				for (HashTriNode ch : node.children.values()) {
					queue.offer(ch);
				}
			}
		}
	}

	/**
	 * 返回包含整个字符串的末端节点(如果正好是叶子节点，则word是树中完整字符串)
	 * 
	 * @param word
	 * @return
	 */
	private HashTriNode searchNode(String word) {
		HashMap<Character, HashTriNode> children = root.children;
		HashTriNode result = null;
		if (null == children || children.isEmpty()) {
			return result;
		}
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (!children.containsKey(ch)) {
				return null;// 字典树中不包含ch及之后的字符
			}
			result = children.get(ch);// 获取ch对应子树
			children = result.children;// children迭代
		}
		return result;
	}

	/**
	 * 给定一段文字，找出在字典树中定义过的所有单词集合
	 * 
	 * @param text
	 * @return
	 */
	public HashSet<String> searchWordsInText(String text) {
		HashSet<String> list = new HashSet<>();
		if (null == text || text.trim().equals("")) {
			return list;
		}
		for (int i = 0; i < text.length(); i++) {
			for (int j = i + 1; j <= text.length(); j++) {
				String word = text.substring(i, j);
				if (containsInDic(word)) {
					list.add(word);
				}
			}
		}
		return list;
	}

	// public HashSet<String> findWordsStartsWith(String text) {
	// HashSet<String> list = new HashSet<>();
	// if (null == text || text.trim().equals("")) {
	// return list;
	// }
	// for (int i = 0; i < text.length(); i++) {
	// for (int j = i + 1; j <= text.length(); j++) {
	// String word = text.substring(i, j);
	// if (startsWith(word)) {
	// list.add(word);
	// }
	// }
	// }
	// return list;
	// }

	public static void main(String[] args) {
		HashTrie tree = new HashTrie();
		tree.insert("abc");
		tree.insert("bda");
		tree.insert("caef");
		tree.insert("ahkl");
		tree.outPut();
		String src = "abcdegjbdcaefaoikahklp";
		HashSet<String> result = tree.searchWordsInText(src);
		System.out.println("all words in text:");
		for (String st : result) {
			System.out.println(st);
		}
		// HashSet<String> starts = tree.findAllStartsWith(src);
		// System.out.println("all starts with:");
		// for (String st : starts) {
		// System.out.println(st);
		// }
		// tree.delete("abc");
		// System.out.println("after delete:");
		// tree.outPut();
		System.out.println("dic words start with ab:");
		HashSet<String> subStr = tree.getDicWordsBeginWith("a");
		for (String st : subStr) {
			System.out.println(st);
		}
	}
}
