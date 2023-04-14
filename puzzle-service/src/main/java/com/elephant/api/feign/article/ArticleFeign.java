package com.elephant.api.feign.article;

import com.elephant.api.api.article.ArticleApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * ArticleFeign
 *
 * @author cunw generator
 * date 2023-04-14
 * 湖南新云网科技有限公司版权所有.
 */
@FeignClient(value = "cunw-article-server", path = "/articles")
public interface ArticleFeign extends ArticleApi {

}
