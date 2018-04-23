package com.camelot.transaction.redpackage.service.mapper;

import com.camelot.transaction.redpackage.api.dto.RedPackageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RedPackageMapper {

  int record(RedPackageDto dto);

  int queryLogCount(String msgId);

  int logMsg(@Param("msgId")String msgId);
}