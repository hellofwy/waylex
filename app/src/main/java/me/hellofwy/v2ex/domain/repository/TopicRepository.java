package me.hellofwy.v2ex.domain.repository;

import java.util.List;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface TopicRepository {

    List<TopicModel> getLatestTopics();

    List<TopicModel> getHotTopics();

    NodeModel getNodeInfo(String nodeName);

    MemberModel getMemberInfoById(String memberId);

    MemberModel getMemberInfoByName(String memberName);
}
