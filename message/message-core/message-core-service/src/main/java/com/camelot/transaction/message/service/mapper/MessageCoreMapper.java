package com.camelot.transaction.message.service.mapper;

import com.camelot.transaction.message.api.dto.MessageInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageCoreMapper {

  //插入预备消息.
  int insertPrepareMessage(MessageInfoDto dto);

  //更新预备消息.
  int updateMessagStatus(Long msgId);

  //删除预备消息.
  int deleteMessageById(Long msgId);

  //查询未被确认的预备消息.
  List<MessageInfoDto> searchPrepareMessage();

}