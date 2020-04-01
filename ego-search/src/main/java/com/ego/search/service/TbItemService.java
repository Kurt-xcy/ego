package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

public interface TbItemService {
	/**
	 * 初始化solr内容
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void init() throws SolrServerException, IOException;
	
	/**
	 * 查询功能
	 * @param Query
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	Map<String, Object> selByQuery(String query,int page,int rows) throws SolrServerException, IOException;
	
	/**
	 * 新增
	 * @param item
	 * @return
	 */
	int add(Map<String,Object> map,String desc) throws SolrServerException, IOException;
}
