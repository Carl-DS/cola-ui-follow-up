package com.colaui.example.service;

import com.colaui.example.model.ColaAuth;

import java.util.List;

/**
 * Created by Carl on 2017/4/9.
 */
public interface ColaAuthService {

    void save(ColaAuth auth);

    List<ColaAuth> getAuths(String user);
}
