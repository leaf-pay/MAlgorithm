package com.mebiuw.malgorithm.tree.btree;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
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
	HashSet<Integer> set=new HashSet<Integer>();
	BTree<Integer> btree;
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
		 btree=new BTree<Integer>(3);
		//顺序插入后查询
		for(int i=0;i<1000;i++)
			btree.insert( i);
		for(int i=0;i<1000;i++)
		assertNotEquals(btree.query( i),null);
		//逆序插入后执行
		btree=new BTree(3);
		for(int i=0;i<1000;i++)
			btree.insert( 1000-i);
		for(int i=0;i<1000;i++)
		assertNotEquals(btree.query( 1000-i),null);
		//随机插入后执行
		btree=new BTree(3);
		Random ran=new Random(System.currentTimeMillis());
		
		for(int i=0;i<5000;i++){
			int t=ran.nextInt();
			btree.insert( t);
			set.add(t);
		}
		for(int i=0;i<set.size();i++){
		assertNotEquals(btree.query((int)set.toArray()[i]),null);
		}
		
	
	}

	@Test
	public void test002Query() {
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			Integer tmp=it.next();
			assertEquals(tmp,btree.query( tmp));
		}
	}
	@Test
	public void test004ReQuery() {
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			Integer tmp=it.next();
			assertEquals(tmp,btree.query(tmp));
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
		Iterator<Integer> it = set.iterator();
		int count=0;
		while(it.hasNext() && count++<3000){
			Integer tmp=it.next();
			assertEquals(true,btree.delete( tmp));
		}
	}

}
