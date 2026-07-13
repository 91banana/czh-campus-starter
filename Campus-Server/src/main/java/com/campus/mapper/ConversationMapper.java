package com.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.entity.ChatConversation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ConversationMapper extends BaseMapper<ChatConversation> {

    @Select("SELECT cc.*, " +
            "CASE WHEN cc.user1_id = #{userId} THEN u2.name ELSE u1.name END as other_user_name, " +
            "(SELECT COUNT(*) FROM chat_messages cm WHERE cm.conversation_id = cc.id AND cm.sender_id != #{userId} AND cm.is_read = 0) as unread_count " +
            "FROM chat_conversations cc " +
            "LEFT JOIN users u1 ON cc.user1_id = u1.id " +
            "LEFT JOIN users u2 ON cc.user2_id = u2.id " +
            "WHERE cc.user1_id = #{userId} OR cc.user2_id = #{userId} " +
            "ORDER BY cc.last_time DESC")
    List<ChatConversation> selectByUserId(Long userId);
}
