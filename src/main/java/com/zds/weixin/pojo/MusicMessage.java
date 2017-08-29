package com.zds.weixin.pojo;
/**
 * 音乐外层消息实体
 * @author @DT人 2017年7月28日 下午4:50:43
 *
 */
public class MusicMessage extends BaseMessage {

	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		this.Music = music;
	}
}
