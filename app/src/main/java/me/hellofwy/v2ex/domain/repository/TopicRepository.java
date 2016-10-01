package me.hellofwy.v2ex.domain.repository;

import java.io.UnsupportedEncodingException;
import java.util.List;

import me.hellofwy.v2ex.domain.model.TopicModel;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface TopicRepository {

    List<TopicModel> getLatestTopics();
}
