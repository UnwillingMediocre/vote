package com.stodger.web;

import com.google.gson.Gson;
import com.stodger.common.Const;
import com.stodger.common.ServerResponse;
import com.stodger.domain.VoteSubject;
import com.stodger.domain.VoteUser;
import com.stodger.service.VoteService;
import com.stodger.service.VoteUserService;
import com.stodger.service.impl.VoteServiceImpl;
import com.stodger.service.impl.VoteUserServiceImpl;
import com.stodger.vo.PageInfoVo;
import com.stodger.vo.SubjectAndOptionVo;
import com.stodger.vo.VoteInfoVo;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 */
@WebServlet("/vote")
public class VoteServlet extends HttpServlet {
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }


    public void createVote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("name");
        String description = request.getParameter("description");
        String[] options = request.getParameterValues("options");
        String typeStr = request.getParameter("type");
        Integer type = Integer.valueOf(typeStr);
        String endTime = request.getParameter("endtime");
        String userId = request.getParameter("userId");
        VoteService voteService = new VoteServiceImpl();
        voteService.saveVote(title, description, options, type, endTime, userId);
    }

    public void showVoteList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String pageNum = request.getParameter("pageNum");
        PageInfoVo pageInfoVo = new PageInfoVo();
        VoteService voteService = new VoteServiceImpl();
        pageInfoVo = voteService.findVoteSubjectByUserId(userId,Integer.parseInt(pageNum),10);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfoVo);
        response.getWriter().print(json);
    }

    public void getVoteInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("vote_id");
        VoteService voteService = new VoteServiceImpl();
        VoteInfoVo voteInfoVo = voteService.findVoteInfoBySubjectId(Integer.parseInt(subjectIdStr));
        request.setAttribute("vote", voteInfoVo);
        request.getRequestDispatcher("voteInfo.jsp").forward(request, response);
    }

    public void deleteVote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("vote_id");
        VoteService voteService = new VoteServiceImpl();
        boolean result = voteService.deleteVoteBySubjectId(Integer.parseInt(subjectIdStr));
        if (!result) {
            response.getWriter().print(result);
        }
    }

    public void editVote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("vote_id");
        VoteService voteService = new VoteServiceImpl();
        SubjectAndOptionVo subjectAndOptionVo = voteService.findVoteBySubjectId(Integer.parseInt(subjectIdStr));
        request.setAttribute("vote", subjectAndOptionVo);
        Date endTime = subjectAndOptionVo.getVoteSubject().getEndTime();
        request.setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        request.getRequestDispatcher("editVote.jsp").forward(request, response);
    }

    public void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectIdStr = request.getParameter("vote_id");
        VoteService voteService = new VoteServiceImpl();
        boolean result = voteService.findItemBySubjectId(Integer.parseInt(subjectIdStr));
        if (!result) {
            response.getWriter().print(result + "");
        }
    }

    public void updateVote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("name");
        String description = request.getParameter("description");
        String[] optionIds = request.getParameterValues("optionIds");
        String[] options = request.getParameterValues("options");
        String typeStr = request.getParameter("type");
        Integer type = Integer.valueOf(typeStr);
        String endTime = request.getParameter("endtime");
        String subjectId = request.getParameter("id");

        VoteService voteService = new VoteServiceImpl();
        voteService.updateVote(subjectId, title, description, optionIds, options, type, endTime);
    }

    public void getVoteAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumStr = request.getParameter("pageNum");
        String keyWord = request.getParameter("keyWord");
        System.out.println(keyWord);
        int pageNum = Integer.parseInt(pageNumStr);
        VoteService voteService = new VoteServiceImpl();
        PageInfoVo pageInfoVo = null;
        if (keyWord == null || "".equals(keyWord)) {
            pageInfoVo = voteService.findVoteAll(pageNum, 6);
        } else {
            pageInfoVo = voteService.searchVote(pageNum, keyWord, 6);
        }
        Gson gson = new Gson();
        String json = gson.toJson(pageInfoVo);
        response.getWriter().print(json);
    }

    public void showDoVoteView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        VoteUser voteUser = (VoteUser) request.getSession().getAttribute("user");
        if(voteUser == null){
            request.getRequestDispatcher("loginAndRegister.jsp").forward(request,response);
        }
        String userId = voteUser.getUserId();
        VoteService voteService = new VoteServiceImpl();
        ServerResponse<VoteInfoVo> serverResponse = voteService.findVoteInfoBySubjectIdAndUserId(Integer.parseInt(id), userId);
        VoteInfoVo voteInfoVo = serverResponse.getData();
        request.setAttribute("voteInfoVo", voteInfoVo);
        if (serverResponse.getCode() == Const.VoteUserEnum.VOTE_NO.getCode()) {
            request.getRequestDispatcher("voteResult.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("doVote.jsp").forward(request, response);
        }
    }

    public void showVoteResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getParameter("vote_id");
        VoteUser voteUser = (VoteUser) request.getSession().getAttribute("user");
        String userId = voteUser.getUserId();
        VoteService voteService = new VoteServiceImpl();
        ServerResponse<VoteInfoVo> serverResponse = voteService.findVoteInfoBySubjectIdAndUserId(Integer.parseInt(subjectId), userId);
        VoteInfoVo voteInfoVo = serverResponse.getData();
        request.setAttribute("voteInfoVo", voteInfoVo);
        request.getRequestDispatcher("voteResult.jsp").forward(request, response);
    }

    public void doVote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] options = request.getParameterValues("options");
        String subjectId = request.getParameter("vote_id");
        VoteUser voteUser = (VoteUser) request.getSession().getAttribute("user");
        VoteService voteService = new VoteServiceImpl();
        voteService.doVote(options, subjectId, voteUser.getUserId());
    }

    public void voteAnalysis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vote_id = request.getParameter("vote_id");
        VoteService voteService = new VoteServiceImpl();
        VoteInfoVo voteInfoVo = voteService.findVoteInfoBySubjectId(Integer.parseInt(vote_id));
        Gson gson = new Gson();
        String json = gson.toJson(voteInfoVo);
        response.getWriter().print(json);
    }

    public void myVote(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String pageNumStr = request.getParameter("pageNum");
        Integer pageNum = Integer.parseInt(pageNumStr);
        String userId = request.getParameter("userId");
        VoteService voteService = new VoteServiceImpl();
        PageInfoVo pageInfoVo = new PageInfoVo();
        pageInfoVo = voteService.findMyVoteByUserId(pageNum, userId,6);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfoVo);
        response.getWriter().print(json);
    }

    public void showAll(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String pageNum = request.getParameter("pageNum");
        VoteService voteService = new VoteServiceImpl();
        PageInfoVo pageInfoVo = new PageInfoVo();
        pageInfoVo = voteService.findAll(Integer.parseInt(pageNum),10);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfoVo);
        response.getWriter().print(json);
    }
}
