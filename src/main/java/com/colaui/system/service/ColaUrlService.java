package com.colaui.system.service;

import com.colaui.example.model.ColaUrl;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
public interface ColaUrlService {

    List<ColaUrl> getUrls(Map<String, Object> params);

    void saveUrl(ColaUrl url);

    void deleteUrl(String id);
}
