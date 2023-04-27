package com.geostar.geoonline.entityserverhtml.servicepublish.controller;

import com.geostar.geoonline.entityserverhtml.servicepublish.entity.MinioConfig;
import com.geostar.geoonline.entityserverhtml.servicepublish.util.ElasticsearchClient;
import com.geostar.geoonline.entityserverhtml.servicepublish.util.MinioUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/serviceDataSet"})
public class PublishController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(PublishController.class);

    @RequestMapping({"/queryESforDLST"})
    @ResponseBody
    public void queryESforDLST(@RequestParam("esUrl") String esUrl, @RequestParam("esUser") String esUser, @RequestParam("esPwd") String esPwd) {
        RestHighLevelClient client = (new ElasticsearchClient()).getClient(esUrl, esUser, esPwd);
        try {
            SearchRequest searchRequest = new SearchRequest(new String[]{"stdbmsprotablename"});
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            searchSourceBuilder.query((QueryBuilder) boolQueryBuilder);
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(200);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            long totalCount = (hits.getTotalHits()).value;
            List<Map> list = new ArrayList<>();
            if (totalCount > 0L) {
                for (SearchHit searchHit : searchHits)
                    list.add(searchHit.getSourceAsMap());
                printJSON(true, list);
            } else {
                printJSON(false, new Object());
            }
        } catch (IOException e) {
            log.error("查询ES索引列表失败", e);
            printJSON(false, e.getMessage());
        } finally {
            if (null != client)
                try {
                    client.close();
                } catch (IOException e) {
                    log.error("关闭ES连接失败", e);
                }
        }
    }

    @RequestMapping({"/checkMinio"})
    @ResponseBody
    public void checkMinio(MinioConfig minioConfig) {
        MinioUtils minioUtils = MinioUtils.init(minioConfig);
        try {
            if (minioUtils.bucketExists(minioConfig.getBucketName())) {
                printJSON(true, "桶存在，minio链接正常");
            } else {
                printJSON(false, "桶不存在，minio链接正常");
            }
        } catch (UnknownHostException unknownHostException) {
            log.error("minio链接异常",unknownHostException);
            printJSON(false, "minio链接异常，ip地址异常");
        } catch (Exception e) {
            log.error("minio链接异常", e);
            printJSON(false, e.getMessage());
        }
    }
}
