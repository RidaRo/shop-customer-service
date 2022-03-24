package com.example.training.shop.customer.repositories;

import com.example.training.shop.customer.models.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<Item, String> {
}
