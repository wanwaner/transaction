package com.camelot.transaction.redpackage.api.service;

import com.camelot.transaction.redpackage.api.dto.RedPackageDto;

public interface RedPackageService {

  void record(String msgId, RedPackageDto dto);

}
