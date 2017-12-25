package com.zack.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("iFileService")
public interface IFileService {

    String upload(MultipartFile file, String path);
}
