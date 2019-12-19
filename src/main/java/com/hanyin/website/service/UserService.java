package com.hanyin.website.service;

import com.hanyin.website.entity.Session;
import com.hanyin.website.entity.UserTable;
import com.hanyin.website.repository.SessionRepository;
import com.hanyin.website.repository.UserTableRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * 用户服务
 */
@Service
public class UserService {

    private UserTableRepository userTableRepository;
    private SessionRepository sessionRepository;

    public UserService(UserTableRepository userTableRepository, SessionRepository sessionRepository) {
        this.userTableRepository = userTableRepository;
        this.sessionRepository = sessionRepository;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 登陆结果及会话id
     */
    public String login(String username, String password) {
        Response<Session> sessionResponse = new Response<>();
        if (username == null || password == null) sessionResponse.setErrorMessage("请补全登录信息！");
        else {
            UserTable userTable = userTableRepository.findByUsernameAndPassword(username, password);
            if (userTable == null) sessionResponse.setErrorMessage("用户名或密码错误！");
            else {
                sessionResponse.setStatus(true);
                Session session = new Session();
                session.setUserId(userTable.getId());
                session.setId(UUID.randomUUID().toString());
                session = sessionRepository.save(session);
                sessionResponse.setData(session);
            }
        }
        return sessionResponse.response();
    }

    /**
     * 登出
     * @param sessionId 会话id
     * @return 登出结果
     */
    public String logout(String sessionId) {
        Response<Session> sessionResponse = getSession(sessionId);
        if (!sessionResponse.isStatus()) return sessionResponse.response();

        Session session = sessionResponse.getData();
        sessionRepository.delete(session);
        Response<String> response = new Response<>();
        response.setStatus(true);
        response.setData("登出成功！");
        return response.response();
    }

    private Response<Session> getSession(String sessionId) {
        Response<Session> response = new Response<>();
        if (sessionId == null) response.setErrorMessage("session id is null!");
        else {
            Optional<Session> optionalSession = sessionRepository.findById(sessionId);
            if (optionalSession.isEmpty()) response.setErrorMessage("session not found!");
            else response.setData(optionalSession.get());
        }
        return response;
    }

    /**
     * 登录检查
     * @param sessionId 会话id
     * @return 当前用户id
     */
    public Integer loginCheck(String sessionId) {
        Response<Session> sessionResponse = getSession(sessionId);
        Session session = sessionResponse.getData();
        return session == null ? null : session.getUserId();
    }
}
