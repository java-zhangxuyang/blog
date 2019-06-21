package com.zhangxy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhangxy.model.Content;

@Service
public class SolrService {

	//solr服务器所在的地址，core0为自己创建的文档库目录
	private final static String SOLR_URL = "http://www.youngzhang.cn:8983/solr/blog";

	/**
	 * 获取客户端的连接
	 * 
	 * @return
	 */
	public HttpSolrClient createSolrServer() {
	    HttpSolrClient solr = null;
	    solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000).withSocketTimeout(60000).build();
	    return solr;
	}

	/**
	 * 往索引库添加文档
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void addDoc(Content con) throws SolrServerException, IOException {
	    SolrInputDocument document = new SolrInputDocument();
	    document.addField("id", con.getId());
	    document.addField("title", con.getTitle());
	    document.addField("time", con.getTime());
	    document.addField("content", con.getContent());
	    document.addField("author", con.getAuthor());
	    document.addField("top", con.getTop());
	    document.addField("look", con.getLook()==null?0:con.getLook());
	    document.addField("nid", con.getNid());
	    HttpSolrClient solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000)
	            .withSocketTimeout(60000).build();
	    solr.add(document);
	    solr.commit();
	    solr.close();
	    System.out.println("添加成功");
	}

	/**
	 * 根据ID从索引库删除文档
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void deleteDocumentById(Integer id) throws SolrServerException, IOException {
	    HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
	                .withConnectionTimeout(10000)
	                .withSocketTimeout(60000).build();

	    server.deleteById(id.toString());
	    server.commit();
	    server.close();
	}

	/**
	 * 根据设定的查询条件进行文档字段的查询
	 * @throws Exception
	 */
	public List<Content> querySolr(String likeName) throws Exception {

	    HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
	                .withConnectionTimeout(10000)
	                .withSocketTimeout(60000).build();
	    SolrQuery query = new SolrQuery();

	    //下面设置solr查询参数

	    //query.set("q", "*:*");// 参数q  查询所有 
	    query.setQuery("title:"+likeName+" OR content:"+likeName);
	    /*fq :
         * （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，
         */
	    //query.setFilterQueries("title:"+likeName+" OR content:"+likeName);

	    //参数fq, 给query增加过滤查询条件 
	    //query.addFacetQuery("id:[0 TO 9]");
	    //query.addFilterQuery("description:一个逗比的码农"); 
	    query.setFields("id,title,content,author,time,top,look,nid");

	    //参数sort,设置返回结果的排序规则
	    query.setSort("id",SolrQuery.ORDER.desc);

	    //设置分页参数
//	    query.setStart(0);
//	    query.setRows(10);

	    //设置高亮显示以及结果的样式
	    query.setHighlight(true);
	    query.addHighlightField("title,content");  
	    //设置高亮前缀和后缀
        query.setHighlightSimplePre("<span style=\"color:red\">");
        query.setHighlightSimplePost("</span>");


	    //执行查询
	    QueryResponse response = server.query(query);

	    //获取返回结果
	    SolrDocumentList resultList = response.getResults();
	    
	    //从响应中获得高亮信息
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

	    List<Content> list = new ArrayList<Content>();
	    for(SolrDocument document: resultList){
	    	Content con = new Content();
	    	con.setId(Integer.parseInt(document.get("id").toString()));
	    	String title = document.get("title").toString();
	    	con.setTitle(title.substring(1, title.length()-1));
	    	String content = document.get("content").toString();
	    	con.setContent(content.substring(1, content.length()-1));
	    	con.setTop(Boolean.parseBoolean(document.get("top").toString()));
	    	String author = document.get("author").toString();
	    	con.setAuthor(author.substring(1, author.length()-1));
	    	String look = document.get("look").toString();
	    	con.setLook(Integer.parseInt(look.substring(1, look.length()-1)));
	    	String nid = document.get("nid").toString();
	    	con.setNid(Integer.parseInt(nid.substring(1, nid.length()-1)));
	    	String time = document.get("time").toString();
	    	con.setTime(new Date(time.substring(1, time.length()-1)));
	    	list.add(con);
	    	//获得高亮的信息
            if(highlighting!=null){
                //根据主键获取高亮信息
                Map<String, List<String>> map = highlighting.get(document.get("id"));
                if(map!=null){
                    List<String> list1 = map.get("title");
                    if(list1!=null){
                        con.setTitle(list1.get(0));
                    }
                    List<String> list2 = map.get("content");
                    if(list2!=null){
                    	con.setContent(list2.get(0));
                    }
                }
            }
	    }
	    return list;

	}
	/**
	 * 根据设定的查询条件进行文档字段的查询
	 * @throws Exception
	 */
	public Content querySolrById(Integer id ,String likeName) throws Exception {
		
		HttpSolrClient server = new HttpSolrClient.Builder(SOLR_URL)
				.withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();
		SolrQuery query = new SolrQuery();
		
		//下面设置solr查询参数
		
		//query.set("q", "*:*");// 参数q  查询所有 
		query.setQuery("id:" + id +" AND title:"+likeName+" OR content:"+likeName);
		/*fq :
		 * （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，
		 */
		//query.setFilterQueries("title:"+likeName+" OR content:"+likeName);
		
		//参数fq, 给query增加过滤查询条件 
		//query.addFacetQuery("id:[0 TO 9]");
		//query.addFilterQuery("description:一个逗比的码农"); 
		query.setFields("id,title,content,author,time,top,look,nid");
		
		//参数sort,设置返回结果的排序规则
		query.setSort("id",SolrQuery.ORDER.desc);
		
		//设置分页参数
//	    query.setStart(0);
//	    query.setRows(10);
		
		//设置高亮显示以及结果的样式
		query.setHighlight(true);
		query.addHighlightField("title,content");  
		//设置高亮前缀和后缀
		query.setHighlightSimplePre("<span style=\"color:red\">");
		query.setHighlightSimplePost("</span>");
		query.setHighlightFragsize(1000000);
		
		
		//执行查询
		QueryResponse response = server.query(query);
		
		//获取返回结果
		SolrDocumentList resultList = response.getResults();
		
		//从响应中获得高亮信息
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		List<Content> list = new ArrayList<Content>();
		for(SolrDocument document: resultList){
			Content con = new Content();
			con.setId(Integer.parseInt(document.get("id").toString()));
			String title = document.get("title").toString();
			con.setTitle(title.substring(1, title.length()-1));
			String content = document.get("content").toString();
			con.setContent(content.substring(1, content.length()-1));
			con.setTop(Boolean.parseBoolean(document.get("top").toString()));
			String author = document.get("author").toString();
			con.setAuthor(author.substring(1, author.length()-1));
			String look = document.get("look").toString();
			con.setLook(Integer.parseInt(look.substring(1, look.length()-1)));
			String nid = document.get("nid").toString();
			con.setNid(Integer.parseInt(nid.substring(1, nid.length()-1)));
			String time = document.get("time").toString();
			con.setTime(new Date(time.substring(1, time.length()-1)));
			list.add(con);
			//获得高亮的信息
			if(highlighting!=null){
				//根据主键获取高亮信息
				Map<String, List<String>> map = highlighting.get(document.get("id"));
				if(map!=null){
					List<String> list1 = map.get("title");
					if(list1!=null){
						con.setTitle(list1.get(0));
					}
					List<String> list2 = map.get("content");
					if(list2!=null){
						con.setContent(list2.get(0));
					}
				}
			}
		}
		if(list != null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
		
	}
	
	

}
