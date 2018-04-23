package com.camelot.transaction.message;

import com.camelot.transaction.common.idworker.IdWorker;
import com.camelot.transaction.message.api.dto.MessageInfoDto;
import com.camelot.transaction.message.service.mapper.MessageCoreMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MapperTest {

  @Autowired
  private MessageCoreMapper messageCoreMapper;

  @Test
  public void testInsertAllColumn() {
    MessageInfoDto dto = new MessageInfoDto();
    dto.setId(IdWorker.getId());
    dto.setSource("test");
    dto.setTopic("topic");
    dto.setTag("tag");
    dto.setContent("test");
    Assert.assertEquals(1, messageCoreMapper.insertPrepareMessage(dto));
  }

  @Test
  public void testDeleteMsg() {
    Long newId = IdWorker.getId();
    MessageInfoDto dto = new MessageInfoDto();
    dto.setId(newId);
    dto.setSource("test");
    dto.setTopic("topic");
    dto.setTag("tag");
    dto.setContent("test");
    Assert.assertEquals(1, messageCoreMapper.insertPrepareMessage(dto));
    Assert.assertEquals(1, messageCoreMapper.deleteMessageById(newId));
  }

  @Test
  public void testUpdMsg() {
    Long newId = IdWorker.getId();
    MessageInfoDto dto = new MessageInfoDto();
    dto.setId(newId);
    dto.setSource("test");
    dto.setTopic("topic");
    dto.setTag("tag");
    dto.setContent("test2");
    Assert.assertEquals(1, messageCoreMapper.insertPrepareMessage(dto));
    Assert.assertEquals(1, messageCoreMapper.updateMessagStatus(newId));
  }

  @Test
  public void searchMsg() {
    List<MessageInfoDto> list = messageCoreMapper.searchPrepareMessage();
    Assert.assertEquals(1, list.size());
    list.forEach(messageInfoDto -> {
      log.info("messageInfo:{}", messageInfoDto);
    });
  }
}
