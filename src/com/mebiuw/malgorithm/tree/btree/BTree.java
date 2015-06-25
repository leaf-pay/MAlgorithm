package com.mebiuw.malgorithm.tree.btree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BTree {
	private int K;
	private TreeNode root;

	public BTree(int k) {
		this.K = k;
		this.root = TreeNode.constructRootNode(this.K * 2 - 1);
	}

	public long query(TreeNode node, long value) {
		if (node.isLeaf == true) {
			int index = 0;
			while (index < node.pointer - 1 && node.keys[index] < value)
				index++;
			if (node.keys[index] == value)
				return value;
			else
				return -1;
		} else {
			int index = 0;
			while (index < node.pointer && node.keys[index] < value)
				index++;
			if (index < node.pointer && node.keys[index] == value)
				return value;
			return this.query(node.children[index], value);
		}
	}

	/**
	 * split original(parent,key[0..2*k-1 -1],children[0..2*k -1] node into two
	 * part orginal change to :(parent,key[0..index],chidren[0..k-1]) new
	 * node:(parent,key[index+1,..2*k-1-1],chidren[k..2*k-1] and insert
	 * key[index] to parent node and refresh
	 * 
	 * @param node
	 * @param index
	 */
	public void split(TreeNode node, int index) {
		TreeNode newNode = TreeNode.constructLeafNode(2 * this.K - 1);
		TreeNode oldNode = node.children[index];
		newNode.isLeaf = oldNode.isLeaf;
		newNode.pointer = oldNode.pointer = this.K - 1;
		int i, j;
		for (i = K; i < 2 * K - 1; i++) {
			newNode.keys[i - K] = oldNode.keys[i];
			oldNode.keys[i] = -1;
		}
		if (!oldNode.isLeaf) {
			for (i = K; i < 2 * K; i++) {
				newNode.children[i - K] = oldNode.children[i];
				oldNode.children[i] = null;
			}
		}
		for (i = 2 * K - 1; i >= index + 2; i--)
			node.children[i] = node.children[i - 1];
		for (i = 2 * K - 1 - 1; i >= index + 1; i--)
			node.keys[i] = node.keys[i - 1];
		//如果分裂的是最后一个，那么index需要减1 自动到最后
		if(index==node.keys.length)
			index--;
		node.keys[index] = oldNode.keys[K - 1];
		node.children[index + 1] = newNode;
		node.pointer++;
		if(node.pointer>2*K-1){
			int id=1/0;
		}

	}

	public void insert(TreeNode startNode, long value) {
		if (root.pointer == 2 * K - 1) {
			TreeNode newRoot = TreeNode.constructRootNode(2 * K - 1);
			newRoot.pointer = 0;
			newRoot.children[0] = root;
			newRoot.isLeaf = false;
			this.root = newRoot;
			this.split(root, 0);
		}
		// insert;
		TreeNode node = root;
		while (!node.isLeaf) {

			int index = 0;
			while (index < node.pointer && node.keys[index] < value) {
				/** 不能这样 这样会造成插入的时候当前节点性质不满足
				if (node.children[index].pointer == 2 * K - 1)
					this.split(node, index);
					*/
				index++;
			}
			if (node.children[index].pointer == 2 * K - 1) {
				this.split(node, index);
				if (index < node.pointer && node.keys[index] < value)
					index++;
			}
			node = node.children[index];
		}
		// leaf;
		int index = 0;
		while (index < node.pointer && node.keys[index] < value) {
			index++;
		}
		for (int i = 2 * K - 1 - 1; i >= index + 1; i--)
			node.keys[i] = node.keys[i - 1];
		node.keys[index] = value;
		node.pointer++;
	}

	public long findPreviousOne(TreeNode node) {
		if (node.isLeaf)
			return node.keys[node.pointer - 1];
		else
			return this.findPreviousOne(node.children[node.pointer]);
	}

	public long findNextOne(TreeNode node) {
		if (node.isLeaf)
			return node.keys[0];
		else
			return this.findNextOne(node.children[0]);
	}

	public boolean delete(TreeNode node, long value) {

		int index = 0;
		while (index < node.pointer && node.keys[index] < value) {
			index++;
		}
		if (node.isLeaf) {
			if (index < node.pointer && node.keys[index] == value) {
				for (int i = index + 1; i < node.pointer; i++)
					node.keys[i - 1] = node.keys[i];
				node.pointer--;
				return true;
			}
			else{
				return false;
			}
		} else {
			// not leaf node

			if (index < node.pointer && node.keys[index] == value) {
				// 包含了数据的内部节点，找前驱和后继借用节点】
				TreeNode previousChild = node.children[index];
				if (previousChild.pointer >= K) {
					long preValue = this.findPreviousOne(previousChild);
					this.delete(previousChild, preValue);
					node.keys[index] = preValue;
					return true;

				} else {
					TreeNode behindChild = node.children[index + 1];
					if (behindChild.pointer >= K) {
						long nextValue = this.findNextOne(behindChild);
						this.delete(behindChild, nextValue);
						node.keys[index] = nextValue;
						return true;
					} else {
						// 删除当前节点 合并他的两个子节点
						for (int i = index; i < node.pointer; i++)
							node.keys[i] = node.keys[i + 1];
						for (int i = index; i <= node.pointer; i++)
							node.children[i] = node.children[i + 1];
						node.keys[node.pointer-1]=-1;
						node.children[node.pointer]=null;
						node.pointer--;
						previousChild.keys[previousChild.pointer++] = value;
						for (int i = 0; i < K - 1; i++) {
							previousChild.keys[previousChild.pointer] = behindChild.keys[i];
							behindChild.keys[i]=-1;
							previousChild.children[previousChild.pointer++] = behindChild.children[i];
							behindChild.children[i]=null;
						}
						previousChild.children[previousChild.pointer] = behindChild.children[K - 1];
						 behindChild.children[K - 1]=null;
						return this.delete(node, value);

					}
				}
			} else {
				// value isnot contained in this node
				TreeNode nextNode = node.children[index];
				if (nextNode.pointer < K) {
					TreeNode previousChild = index-1>0?node.children[index-1]:null;
					TreeNode behindChild = index+1<K?node.children[index + 1]:null;
					if (previousChild!=null && previousChild.pointer >= K) {
						for (int i = K - 1; i > 0; i--)
							nextNode.keys[i] = nextNode.keys[i - 1];
						for (int i = K; i > 0; i--)
							nextNode.children[i] = nextNode.children[i - 1];
						nextNode.pointer++;
						nextNode.keys[0] = node.keys[index];
						nextNode.children[0] = previousChild.children[K];
						node.keys[index] = previousChild.keys[K - 1];
						previousChild.pointer--;

					} else if (behindChild!=null && behindChild.pointer >= K) {
						nextNode.keys[K - 1] = node.keys[index];
						nextNode.children[K] = behindChild.children[0];
						nextNode.pointer++;
						node.keys[index] = behindChild.keys[0];
						for (int i = 0; i < behindChild.pointer - 1; i++)
							behindChild.keys[i] = behindChild.keys[i + 1];
						for (int i = 0; i < behindChild.pointer; i++)
							behindChild.children[i] = behindChild.children[i + 1];
						behindChild.pointer--;
					} else {
						// merge 需要对节点进行合并 保证大小
						int mergeIndex = index;
						//mergeIndex 为合并后保留的 fiend不保留
						TreeNode mergeChild = null;
						TreeNode friendChild;
						//需要处理的当前节点的位置
						int nodeIndex=index;
						if (index > 0) {
							mergeIndex--;
							nodeIndex--;
							mergeChild = node.children[mergeIndex];
							friendChild= node.children[index];
						} else {
							mergeIndex++;
							mergeChild = node.children[index];
							friendChild= node.children[mergeIndex];
						}

						mergeChild.keys[K - 1] = node.keys[nodeIndex];
						for (int i = 0; i < K - 1; i++){
							mergeChild.keys[K + i] = friendChild.keys[i];
							friendChild.keys[i]=-1;
						}
						for (int i = 0; i <= K - 1; i++){
							mergeChild.children[K + i] = friendChild.children[i];
							 friendChild.children[i]=null;
						}
						mergeChild.pointer = 2 * K - 1;
						for (int i = nodeIndex; i < node.pointer - 1; i++)
							node.keys[i] = node.keys[i + 1];
						
						for (int i = nodeIndex+1; i <= node.pointer - 1; i++)
							node.children[i] = node.children[i + 1];
						node.keys[node.pointer-1]=-1;
						node.children[node.pointer]=null;
						node.children[nodeIndex]=mergeChild;
						node.pointer--;
						if (node == root && node.pointer==0)
							root = mergeChild;
						nextNode=mergeChild;
					}

				}
				return this.delete(nextNode, value);
			}
		}

	}

	public static void main(String ars[]) {
		Random ran=new Random();
		List<Integer> list=new ArrayList<Integer>();
		
		BTree bt = new BTree(3);
		for (int i = 0; i < 100; i++) {
			int t=ran.nextInt(65536);
			//if(bt.query(bt.root, t)==-1){
				bt.insert(bt.root, t);
			//	list.add(t);
			//}
		}

		System.out.println("GO");
	}

	public TreeNode getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

}
