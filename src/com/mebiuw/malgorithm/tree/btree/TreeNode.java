package com.mebiuw.malgorithm.tree.btree;

public class TreeNode {
	protected TreeNode parent;
	protected long keys[];
	protected TreeNode children[];
	protected boolean isLeaf;
	protected int K;
	protected int pointer;
	
	private TreeNode(int K,boolean isleaf){
		this.parent=null;
		this.K=K;
		this.isLeaf=isleaf;
		this.keys=new long[K];
		this.children=new TreeNode[K+1];
		pointer=0;
	}
	public static TreeNode constructRootNode(int K){
		return new TreeNode(K,true);
	}
	public static TreeNode constructLeafNode(int K){
		return new TreeNode(K,true);
	}
	public TreeNode(TreeNode parent,int K, long[] keys, TreeNode[] children,
			boolean isLeaf,int pointer) {
		super();
		this.parent = parent;
		this.keys = keys;
		this.children = children;
		this.isLeaf = isLeaf;
		this.K=K;
		this.pointer=pointer;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("isLeaf:"+isLeaf+"   Pointer:"+pointer+"\n");
		sb.append("Keys:");
		for(int i=0;i<pointer;i++)
			sb.append(keys[i]+" ");
		sb.append("\nChildren:");
		for(int i=0;i<=pointer;i++)
			sb.append(" ");
		return sb.toString();
	}
	
	
	
	
	


}
