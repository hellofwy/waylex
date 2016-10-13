package me.hellofwy.v2ex.domain.repository;

import java.io.IOException;
import java.util.List;

import me.hellofwy.v2ex.domain.model.MemberModel;
import me.hellofwy.v2ex.domain.model.NodeModel;
import me.hellofwy.v2ex.domain.model.TopicModel;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface TopicRepository {

    List<TopicModel> getLatestTopics() throws IOException;

    List<TopicModel> getHotTopics() throws IOException;

    NodeModel getNodeInfo(String nodeName) throws IOException;

    MemberModel getMemberInfoById(String memberId) throws IOException;

    MemberModel getMemberInfoByName(String memberName) throws IOException;
}
