package com.hyl.utils.io;

import java.io.File;

import org.hyl.utils.FileUtils;
import org.junit.Test;

public class FileHelperTest {

	@Test
	public void testDeleteFolder() {
		String path="E:"+File.separator+"ddd";
		FileUtils.deleteFolder(path);
	}
	
	@Test
	public void testDeleteAllFiles(){
		String path="E:"+File.separator+"ddd";
		System.out.println(FileUtils.deleteAllFiles(path));
	}

	@Test
	public void testGetExtName(){
		System.out.println(FileUtils.getExtName("data.txt",false));
		System.out.println(FileUtils.getExtName("data.txt",true));
		System.out.println(FileUtils.getExtName("data",true));
		System.out.println(FileUtils.getExtName("",true));
		System.out.println(FileUtils.getExtName(null,true));
	}
}
