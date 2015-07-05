package com.mebiuw.malgorithm.tree.btree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BTreeTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test001Insert() {

		BTree<Integer, Integer> btree;
		btree = new BTree<Integer, Integer>(3);
		HashSet<Integer> set = new HashSet<Integer>();
		// 顺序插入后查询
		for (int i = 0; i < 1000; i++)
			btree.insert(i, i);
		for (int i = 0; i < 1000; i++)
			assertNotEquals(btree.query(i), null);
		for (int i = 1000; i < 2000; i++)
			assertEquals(btree.query(i), null);
		// 逆序插入后执行
		btree = new BTree<Integer, Integer>(3);
		for (int i = 0; i < 1000; i++)
			btree.insert(1000 - i, 1000 - i);
		for (int i = 0; i < 1000; i++)
			assertNotEquals(btree.query(1000 - i), null);
		// 随机插入后执行
		btree = new BTree<Integer, Integer>(3);
		Random ran = new Random(System.currentTimeMillis());
		for (int i = 0; i < 5000; i++) {
			int t = ran.nextInt(1024);
			btree.insert(t, t);
		}
		for (int i = 0; i < set.size(); i++) {
			assertNotEquals(btree.query((int) set.toArray()[i]), null);
		}

	}

	@Test
	public void test002Query() {

		BTree<Integer, Integer> btree;
		btree = new BTree<Integer, Integer>(3);
		Random ran = new Random(System.currentTimeMillis());
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 5000; i++) {
			int t = ran.nextInt(50000);
			btree.insert(t, t);
			set.add(t);
		}
		for (int i = 0; i < set.size(); i++) {
			assertEquals(btree.query((int) set.toArray()[i]), set.toArray()[i]);
			assertEquals(btree.query((int) set.toArray()[i] * -1-1), null);
			assertEquals(btree.query((int) set.toArray()[i] +50001), null);
		}
	}

	@Ignore
	@Test
	public void testSplit() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testFindPreviousOne() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testFindNextOne() {
		fail("Not yet implemented");
	}

	@Test
	public void test003Delete() {
		BTree<Integer, Integer> btree;
		btree = new BTree<Integer, Integer>(3);
		Random ran = new Random(System.currentTimeMillis());
		List<Integer> set = new ArrayList<Integer>();
		int tmp=0;
		for (int i = 0; i < 7000; i++) {
			int t = ran.nextInt(5);
			btree.insert(tmp, tmp);
			set.add(tmp);
			tmp+=t+1;
		}
		int times=set.size()/2;
		while(times-->0){
			int i=ran.nextInt(set.size());
			assertEquals(btree.delete(set.get(i)), true);
			assertEquals(btree.delete(set.get(i)*-1-1), false);
			assertEquals(btree.delete(set.get(i)+tmp), false);
			set.remove(i);
		}
		for (int i = 0; i < set.size(); i++) {
			assertNotEquals(btree.query((int) set.get(i)), null);
			assertEquals(btree.query((int) set.get(i) * -1-1), null);
			assertEquals(btree.query((int) set.get(i) +tmp), null);
		}
	}

}
