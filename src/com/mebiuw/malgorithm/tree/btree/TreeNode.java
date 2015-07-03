package com.mebiuw.malgorithm.tree.btree;

/**
 * <p>
 * structure of Btree-Node
 * </p>
 * <p>
 * B树节点的定义
 * </p>
 * 
 * @author MebiuW
 *
 */
public class TreeNode {
	protected TreeNode parent;
	protected Comparable keys[];
	protected TreeNode children[];
	protected boolean isLeaf;
	protected int maxKeys;
	protected int pointer;

	/**
	 * <p>
	 * Construct a TreeNode
	 * </p>
	 * <p>
	 * 构造一个树节点
	 * </p>
	 * 
	 * @param K
	 *            ： 关键字数量，和B树里的定义不是一个 the max size of keywords
	 * @param isleaf
	 *            是否是树节点 is leaf or not
	 */
	private TreeNode(int K, boolean isleaf) {
		this.parent = null;
		this.maxKeys = K;
		this.isLeaf = isleaf;
		this.keys = new Comparable[K];
		this.children = new TreeNode[K + 1];
		pointer = 0;
	}

	/**
	 * <p>
	 * Construct a un-leaf TreeNode
	 * </p>
	 * <p>
	 * 构造一个树节点
	 * </p>
	 * 
	 * @param K
	 * @return
	 */
	public static TreeNode constructUnleafNode(int K) {
		return new TreeNode(K, true);
	}

	/**
	 * <p>
	 * Construct a Leaf TreeNode
	 * </p>
	 * <p>
	 * 构造一个树节点
	 * </p>
	 * 
	 * @param K
	 *            ： 关键字数量，和B树里的定义不是一个 the max size of keywords
	 * @return
	 */
	public static TreeNode constructLeafNode(int K) {
		return new TreeNode(K, true);
	}
	/**
	 * <p>
	 * Construct a TreeNode
	 * </p>
	 * <p>
	 * 构造一个树节点
	 * </p>
	 * 
	 * @param K
	 *            ： 关键字数量，和B树里的定义不是一个 the max size of keywords
	 * @param isleaf
	 *            是否是树节点 is leaf or not
	 */
	public TreeNode(TreeNode parent, int K, Comparable[] keys,
			TreeNode[] children, boolean isLeaf, int pointer) {
		super();
		this.parent = parent;
		this.keys = keys;
		this.children = children;
		this.isLeaf = isLeaf;
		this.maxKeys = K;
		this.pointer = pointer;
	}

	/**
	 * 仅供测试
	 * just for test
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("isLeaf:" + isLeaf + "   Pointer:" + pointer + "\n");
		sb.append("Keys:");
		for (int i = 0; i < pointer; i++)
			sb.append(keys[i] + " ");
		sb.append("\nChildren:");
		for (int i = 0; i <= pointer; i++)
			sb.append(" ");
		return sb.toString();
	}

}
