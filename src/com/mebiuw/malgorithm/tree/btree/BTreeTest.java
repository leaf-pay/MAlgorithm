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
	HashSet<Long> set=new HashSet<Long>();
	BTree btree;
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
		 btree=new BTree(3);
		//顺序插入后查询
		for(int i=0;i<1000;i++)
			btree.insert(btree.getRoot(), i);
		for(int i=0;i<1000;i++)
		assertNotEquals(btree.query(btree.getRoot(), i),-1);
		//逆序插入后执行
		btree=new BTree(3);
		for(int i=0;i<1000;i++)
			btree.insert(btree.getRoot(), 1000-i);
		for(int i=0;i<1000;i++)
		assertNotEquals(btree.query(btree.getRoot(), 1000-i),-1);
		//随机插入后执行
		btree=new BTree(3);
		Random ran=new Random(System.currentTimeMillis());
		
		for(int i=0;i<50000;i++){
			long t=ran.nextLong();
			btree.insert(btree.getRoot(), t);
			set.add(t);
		}
		for(int i=0;i<set.size();i++){
		assertNotEquals(btree.query(btree.getRoot(),(long) set.toArray()[i]),-1);
		}
		
	
	}

	@Test
	public void test002Query() {
		Iterator<Long> it = set.iterator();
		while(it.hasNext()){
			long tmp=it.next();
			assertEquals(tmp,btree.query(btree.getRoot(), tmp));
		}
	}
	@Test
	public void test004ReQuery() {
		Iterator<Long> it = set.iterator();
		while(it.hasNext()){
			long tmp=it.next();
			assertEquals(tmp,btree.query(btree.getRoot(), tmp));
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
		Iterator<Long> it = set.iterator();
		int count=0;
		while(it.hasNext() && count++<3000){
			long tmp=it.next();
			assertEquals(true,btree.delete(btree.getRoot(), tmp));
		}
	}

}
