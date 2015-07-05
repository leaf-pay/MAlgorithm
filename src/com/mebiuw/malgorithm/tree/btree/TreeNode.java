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
public class TreeNode<K extends Comparable<K>,V> {
	protected TreeNode<K,V> parent;
	protected Pair<K,V> keys[];
	protected TreeNode<K,V> children[];
	protected boolean isLeaf;
	protected int maxKeys;
	protected int pointer;
	protected V value;

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
	protected TreeNode(int Ks, boolean isleaf) {
		this.parent = null;
		this.maxKeys = Ks;
		this.isLeaf = isleaf;
		this.keys = new Pair[Ks];
		this.children = new TreeNode[Ks + 1];
		pointer = 0;
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
	public TreeNode(TreeNode parent, int Ks, Pair<K,V> [] keys,
			TreeNode[] children, boolean isLeaf, int pointer) {
		super();
		this.parent = parent;
		this.keys = keys;
		this.children = children;
		this.isLeaf = isLeaf;
		this.maxKeys = Ks;
		this.pointer = pointer;
	}
	/**
	 * if you use as a Key-Value node,you should use this function to get values
	 * 如果需要以Key-Value的方式存储数据，则需要在得到TreeNode对象后调用这个方式
	 * @return
	 */
	public V getValue(){
		return this.value;
	}

	/**
	 * if you use as a Key-Value node,you should use this function to set value 
	 * 如果要以Key-Value的方式存储数据，则需要专门设置这个value
	 * @param value
	 */
	public void setValue(V value){
		this.value=value;
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
