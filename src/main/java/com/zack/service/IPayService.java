package com.zack.service;

import com.zack.common.ServerResponse;
import org.springframework.stereotype.Service;

public interface IPayService {
    ServerResponse aliPay(Long orderNo);
}
