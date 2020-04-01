package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.JedisCluster;

@Repository
public class JedisDaoImpl implements JedisDao{
	@Resource
	private JedisCluster jedisclients;
	@Override
	public Boolean exists(String key) {
		return jedisclients.exists(key);
	}

	@Override
	public Long del(String key) {
		return jedisclients.del(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisclients.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisclients.get(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisclients.expire(key, seconds);
	}
	
}
