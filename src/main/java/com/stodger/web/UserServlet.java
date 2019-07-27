package com.stodger.web;

import com.google.gson.Gson;
import com.stodger.common.Const;
import com.stodger.common.ServerResponse;
import com.stodger.domain.VoteApply;
import com.stodger.domain.VoteUser;
import com.stodger.service.VoteUserService;
import com.stodger.service.impl.VoteUserServiceImpl;
import com.stodger.util.CodeUtil;
import com.stodger.util.MailUtil;
import com.stodger.vo.PageInfoVo;

import javax.mail.MessagingException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stroger
 * @version V1.0
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html; charset=UTF-8");

        String methodName = request.getParameter("method");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String randomCode = (String) request.getSession().getAttribute("randomCode");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        ServerResponse serverResponse = voteUserService.login(username, password, code, randomCode);
        if (serverResponse.getCode() == Const.VoteUserEnum.USER_SUCCESS.getCode()) {
            VoteUser voteUser = (VoteUser) serverResponse.getData();
            request.getSession().setAttribute("user", voteUser);
            if (voteUser.getPermission() == 2) {
                response.getWriter().print(Const.VoteUserEnum.ORDINARY.getCode());
            }
        } else {
            response.getWriter().println(serverResponse.getMsg());
        }
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("loginAndRegister.jsp");
    }


    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        System.out.println(phone);
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.register(userName, password, phone, email);
        if (!result) {
            response.getWriter().println(Const.VoteUserEnum.REGISTER_ERROR.getMsg());
        }
    }

    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CodeUtil codeUtil = new CodeUtil();
        BufferedImage image = codeUtil.getImage();
        String text = codeUtil.getText();
        request.getSession().setAttribute("randomCode", text);
        codeUtil.output(image, response.getOutputStream());
    }

    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.findByUsername(username);
        if (result) {
            response.getWriter().println(Const.VoteUserEnum.USERNAME_ERROR.getMsg());
        }
    }

    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String userId = request.getParameter("id");
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.updatePassword(userId, oldPwd, newPwd);
        if (result) {
            response.getWriter().print(Const.VoteUserEnum.PASSWORD_SUCCESS.getMsg());
        }
    }

    public void userAll(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String pageNum = request.getParameter("pageNum");
        PageInfoVo pageInfoVo = new PageInfoVo();
        VoteUserService voteUserService = new VoteUserServiceImpl();
        pageInfoVo = voteUserService.findUserAll(Integer.parseInt(pageNum), 10);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfoVo);
        response.getWriter().print(json);
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String userId = request.getParameter("user_id");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.updateUserStatus(userId, 2);
        if (!result) {
            response.getWriter().print(result);
        }
    }

    public void revertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String userId = request.getParameter("user_id");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.updateUserStatus(userId, 1);
        if (!result) {
            response.getWriter().print(result);
        }
    }

    public void initialPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException {
        String userId = request.getParameter("user_id");
        String email = request.getParameter("email");
        ServerResponse serverResponse = new ServerResponse();
        VoteUserService voteUserService = new VoteUserServiceImpl();
        serverResponse = voteUserService.updatePasswordByUserId(userId);
        if (serverResponse.getCode() == Const.VoteUserEnum.INIT_ERROR.getCode()) {
            response.getWriter().print(serverResponse.getMsg());
        } else {
            MailUtil.sendMail(email, serverResponse.getMsg());
        }
    }

    public void setPermission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String permission = request.getParameter("permission");
        System.out.println(permission +"=====>");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        boolean result = voteUserService.updateUserPermission(userId,Integer.parseInt(permission));
        if (!result) {
            response.getWriter().print(result);
        }
    }

    public void applyMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String email = request.getParameter("email");
        String userId = request.getParameter("userId");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        String msg = voteUserService.saveApply(userId, name, email, description);
        if (msg != null) {
            MailUtil.sendMail(email, msg);
        }
        response.sendRedirect("VoteIndex.jsp");
    }

    public void agreeApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException {
        String email = request.getParameter("email");
        String sponsor = request.getParameter("sponsor");
        String id = request.getParameter("id");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        voteUserService.updateApplyStatus(id);
        String msg = voteUserService.updateApplyByUsername(sponsor);
        if (msg != null) {
            MailUtil.sendMail(email, msg);
            response.getWriter().print("");
        } else {
            response.getWriter().print("false");
        }
    }

    public void notAgreeApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MessagingException {
        String email = request.getParameter("email");
        String sponsor = request.getParameter("sponsor");
        String id = request.getParameter("id");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        voteUserService.updateApplyStatus(id);
        MailUtil.sendMail(email, "用户为：" + sponsor + ",本网站(Micro vote)拒绝您申请会员,如有疑问请联系本网站客服(qq:204273796)");
        response.getWriter().print("");
    }

    public void applyAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VoteUserService voteUserService = new VoteUserServiceImpl();
        List<VoteApply> voteApplyList = new ArrayList<>();
        voteApplyList = voteUserService.findApplyAll();
        Gson gson = new Gson();
        String json = gson.toJson(voteApplyList);
        response.getWriter().print(json);
    }

    public void myInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        VoteUser voteUser = new VoteUser();
        VoteUserService voteUserService = new VoteUserServiceImpl();
        voteUser = voteUserService.findMyInfoByUserId(userId);
        request.setAttribute("voteInfo", voteUser);
        request.getRequestDispatcher("myInfo.jsp").forward(request, response);
    }

    public void editInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String personality = request.getParameter("personality");
        VoteUserService voteUserService = new VoteUserServiceImpl();
        voteUserService.updateMyInfo(userId, sex, birthday, email, personality);
        request.getRequestDispatcher("VoteIndex.jsp").forward(request,response);
    }
    public void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        VoteUser voteUser = new VoteUser();
        VoteUserService voteUserService = new VoteUserServiceImpl();
        voteUser = voteUserService.findMyInfoByUserId(userId);
        request.setAttribute("voteInfo", voteUser);
        request.getRequestDispatcher("userInfo.jsp").forward(request, response);
    }
}
