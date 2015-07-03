package com.mebiuw.malgorithm.tree.btree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * An implementation of b tree according to the introduction to algorithm
 * <p>This implementation works with the Class implemented the Comparable</p>
 * <p>一个根据算法导论基本思想实现的b树，可以配合不同的类（基于Comparable接口）一起使用</p>
 * 
 * @author MebiuW
 *
 * @param <T>
 */
public class BTree<T extends Comparable> {
	private int K;
	private TreeNode root;
	/**
	 * <p>The B-tree contains 2K-1 keywords(K-1 at least the node except the root), at most,2K children
	 * </p><p>
	 * 针对一个B树，他最多允许2K-1个关键字 2K个子节点，所以请注意这里的K的性质</p>
	 * @param k
	 */
	public BTree(int k) {
		this.K = k;
		this.root = TreeNode.constructUnleafNode(this.K * 2 - 1);
	}
	/**
	 * <p>
	 * Insert the given value into the tree,starting from root node only.
	 * </p><p>
	 * 插入数据，且只能从树根开始插入数据
	 * </p>
	 * @param value 需要插入的数据
	 */
	public void insert( T value) {
		if (root.pointer == 2 * K - 1) {
			TreeNode newRoot = TreeNode.constructUnleafNode(2 * K - 1);
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
			while (index < node.pointer && node.keys[index].compareTo(value) <0) {
				/** 不能这样 这样会造成插入的时候当前节点性质不满足
				if (node.children[index].pointer == 2 * K - 1)
					this.split(node, index);
					*/
				index++;
			}
			if (node.children[index].pointer == 2 * K - 1) {
				this.split(node, index);
				if (index < node.pointer && node.keys[index].compareTo(value) <0)
					index++;
			}
			node = node.children[index];
		}
		// leaf;
		int index = 0;
		while (index < node.pointer && node.keys[index].compareTo(value) <0) {
			index++;
		}
		for (int i = 2 * K - 1 - 1; i >= index + 1; i--)
			node.keys[i] = node.keys[i - 1];
		node.keys[index] = value;
		node.pointer++;
	}
	
	/**
	 * <p>
	 * delete the given 
	 * </p><p>
	 * 删除数据 调用另外一个方法
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public boolean delete(T value){
		return this.delete(root,value);
	}
	
	/**
	 * <p>
	 * delete the given 
	 * </p><p>
	 * 删除数据 调用另外一个方法
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public T query(T value){
		return this.query(root,value);
	}
	/**
	 * <p>Starting from the given node,query whether the given value is exist in given node or it's children
	 * if return null ,it means not exist ,else it return the corresponding TreeNode object
	 * </p><p>
	 * 针对某个节点开始，进行查询数据value是否存在，存在则返回对应的TreeNode
	 * </p>
	 * @param node
	 * @param value
	 * @return null:not exist 
	 */
	public T query(TreeNode node, T value) {
		if (node.isLeaf == true) {
			int index = 0;
			while (index < node.pointer - 1 && node.keys[index].compareTo(value) <0)
				index++;
			if (node.keys[index] .equals( value))
				return value;
			else
				return null;
		} else {
			int index = 0;
			while (index < node.pointer && node.keys[index].compareTo(value) <0)
				index++;
			if (index < node.pointer && node.keys[index].equals(value))
				return value;
			return this.query(node.children[index], value);
		}
	}
	/**
	 * <p>
	 * delete the given value ,starting from given node
	 * </p><p>
	 * 从节点的某个位置开始删除
	 * </p>
	 * @param node position 给定位置
	 * @param value
	 * @return
	 */
	public boolean delete(TreeNode node, T value) {
	
		int index = 0;
		while (index < node.pointer && node.keys[index].compareTo(value) <0) {
			index++;
		}
		if (node.isLeaf) {
			if (index < node.pointer && node.keys[index].equals(value)) {
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
	
			if (index < node.pointer && node.keys[index].equals(value)) {
				// 包含了数据的内部节点，找前驱和后继借用节点】
				TreeNode previousChild = node.children[index];
				if (previousChild.pointer >= K) {
					T preValue = this.findPrecursorOne(previousChild);
					this.delete(previousChild, preValue);
					node.keys[index] = preValue;
					return true;
	
				} else {
					TreeNode behindChild = node.children[index + 1];
					if (behindChild.pointer >= K) {
						T nextValue = this.findSuccessorOne(behindChild);
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
	/**
	 * <p>
	 * Split the tree-node  'node[index]',which has the length of 2K-1
	 * <br/>
	 * the result is that node[index] will divided into 2 part of K-1 keywords in length
	 * and the middle one of original node[index] will insert into the node
	 * </p><p>
	 * 针对节点进行分裂的方法，将给定node的第index个节点进行分裂，分裂成两个新的K-1长度的节点，同时中间位置插入到node中
	 * </p>
	 * @param node
	 * @param index
	 */
	protected void split(TreeNode node, int index) {
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

	/**
	 * <p>
	 * find the precursor one
	 * </p><p>
	 * 寻找前驱 作为辅助分裂删除
	 * </p>
	 * @param node
	 * @return
	 */
	public T findPrecursorOne(TreeNode node) {
		if (node.isLeaf)
			return (T)node.keys[node.pointer - 1];
		else
			return this.findPrecursorOne(node.children[node.pointer]);
	}
	/**
	 * <p>
	 * find the successor one
	 * </p><p>
	 * 寻找后继作为辅助分裂删除
	 * </p>
	 * @param node
	 * @return
	 */
	public T findSuccessorOne(TreeNode node) {
		if (node.isLeaf)
			return (T)node.keys[0];
		else
			return this.findSuccessorOne(node.children[0]);
	}


}
