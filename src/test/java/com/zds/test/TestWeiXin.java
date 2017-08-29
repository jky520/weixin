package com.zds.test;

import java.io.File;

import org.junit.Test;

import com.zds.weixin.test.MediaKit;

/**
 * @author @DT人 2017年8月3日 下午6:12:53
 *
 */
public class TestWeiXin {

	@Test
	public void testGetMedia() {
		MediaKit.getMedia("JoRUI3nR_6Iq3u9bLVVD1H6VEOMeRWyCmgbkMRwvJKqYpSqKceSbyovWbFe-NSF8", new File("d://aaa.jpg"));
	}

}
