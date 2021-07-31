package com.why.transportsecurity_finally.controller;

import com.why.transportsecurity_finally.entity.*;
import com.why.transportsecurity_finally.service.AccidentServiceImpl;
import com.why.transportsecurity_finally.service.AdminServiceImpl;
import com.why.transportsecurity_finally.service.VehicleServiceImpl;
import com.why.transportsecurity_finally.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO 事故控制
 * @Author why
 * @Date 2021/7/21 15:54
 * Version 1.0
 **/
@Controller
@Log
@RequestMapping("/ts")
@Api(tags = "事故管理后台")
public class AccidentController {

    /******************************************事故信息信息****************************/

    @Autowired
    AccidentServiceImpl accidentService;

    @ApiOperation("事故列表")
    @RequestMapping("/accident/warning")
    public String warning(Model model){
        List<TotalInfo> totalInfos = accidentService.totalInfo();
        if (totalInfos.size() != 0){
            if (!totalInfos.get(0).isStatus()){
                model.addAttribute("flag",true);
            }else {
                model.addAttribute("flag",false);
            }
        }
        model.addAttribute("infos",totalInfos);
        return "accidentPage/warning";
    }

    @ApiOperation("事故数据展示，地址追踪")
    @RequestMapping("/accident/info")
    public String detailsInfo(HttpServletRequest request,Model model){
        String aIds = request.getParameter("aId");
        String vIds = request.getParameter("vId");
        int aId = Integer.valueOf(aIds);
        int vId = Integer.valueOf(vIds);
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);

        DetailInfo detailInfo = accidentService.detailInfo(vId,aId);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        model.addAttribute("detailInfo",detailInfo);
        model.addAttribute("lng",detailInfo.getLng());
        model.addAttribute("lat",detailInfo.getLat());
        model.addAttribute("address",detailInfo.getAddress());
        return "accidentPage/detailsPage";
    }

    /**
     * 右侧信息展示
     * @param model
     * @param vId
     * @param aId
     * @return
     */
    @RequestMapping(value = "/accident/infoRight",method = RequestMethod.GET)
    public String showTask(Model model, @RequestParam("vId") Integer vId, @RequestParam("aId") Integer aId,HttpServletRequest request){
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        model.addAttribute("detailInfo",detailInfo);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        List<Acceleration> axs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            axs.add(new Acceleration(ax.get(i),ay.get(i)));
        }
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        model.addAttribute("acceleration",axs);
        return "accidentPage/infoRight";
    }

    /**
     * 左侧信息展示
     * @param model
     * @param vId
     * @param aId
     * @return
     */
    @RequestMapping(value = "/accident/infoLeft",method = RequestMethod.GET)
    public String showTaskLeft(Model model, @RequestParam("vId") Integer vId, @RequestParam("aId") Integer aId,HttpServletRequest request){
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        model.addAttribute("detailInfo",detailInfo);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        List<Acceleration> axs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            axs.add(new Acceleration(ax.get(i),ay.get(i)));
        }
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        model.addAttribute("acceleration",axs);
        return "accidentPage/infoLeft";
    }

    /**
     * 数据下载
     * @param response
     * @param vId
     * @param aId
     */
    @ApiOperation("碰撞数据下载")
    @RequestMapping("/accident/download")
    public void show(HttpServletResponse response, @RequestParam Integer vId, @RequestParam Integer aId){
        //String vName, String vNumber, String vType, String degreeOfDamage, String date,
        // String time, double lng, double lat, String address, String collisionDirection,
        // String isBounce, double adRAcceleration, double adCAcceleration
        List<InfoExcelModel> list = new ArrayList<>();
        InfoExcelModel infoExcelModel = new InfoExcelModel();

        /**
         * 查出车辆基本信息
         */
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        infoExcelModel.setVName(vehicleInfo.getVName());
        infoExcelModel.setVNumber(vehicleInfo.getVNum());
        infoExcelModel.setVType(vehicleInfo.getVType());

        /**
         * 根据aId查询加速度信息
         */
        List<Double> ay = accidentService.ay(aId);
        List<Double> ax = accidentService.ax(aId);
        infoExcelModel.setAdRAcceleration(ax);
        infoExcelModel.setAdCAcceleration(ay);

        /**
         * 封装车辆事故基本信息
         */
        DetailInfo detailInfo = accidentService.detailInfo(vId, aId);
        //String vName, String vNumber, String vType, String degreeOfDamage, String date,
        // String time, double lng, double lat, String address, String collisionDirection,
        // String isBounce, double adRAcceleration, double adCAcceleration
        infoExcelModel.setDriverDegreeOfDamage(detailInfo.getDriverDegreeOfDamage());
        infoExcelModel.setPassengerDegreeOfDamage(detailInfo.getPassengerDegreeOfDamage());

        infoExcelModel.setDate(detailInfo.getDate());
        infoExcelModel.setTime(detailInfo.getTime());
        infoExcelModel.setLng(detailInfo.getLng());
        infoExcelModel.setLat(detailInfo.getLat());
        infoExcelModel.setAddress(detailInfo.getAddress());
        infoExcelModel.setCollisionDirection(detailInfo.getDirection());
        infoExcelModel.setIsBounce(detailInfo.getIsBounce());

        list.add(infoExcelModel);

        OutputFileUtils.outputFile(list,response);
    }

    /**
     * 分装加速度信息 备注：需要修改
     * 将所有数据封装
     * @param model
     * @param aId
     * @return
     */
    @ApiOperation("数据分析，图表展示")
    @RequestMapping(value = "/accident/tu",method = RequestMethod.GET)
    public String axAndAy(Model model,@RequestParam("aId") Integer aId){
        List<Double> ax = accidentService.ax(aId);
        List<Double> ay = accidentService.ay(aId);
        model.addAttribute("ax",ax);
        model.addAttribute("ay",ay);
        return "accidentPage/tu";
    }

    @ApiOperation("处理事故")
    @RequestMapping(value = "/accident/solve")
    public String solveAccident(HttpServletRequest request,Model model){
        String aIds = request.getParameter("aId");
        String vIds = request.getParameter("vId");
        int aId = Integer.valueOf(aIds);
        int vId = Integer.valueOf(vIds);
        model.addAttribute("aId",aId);
        model.addAttribute("vId",vId);
        accidentService.solveAccident(Integer.valueOf(aId));
        DetailInfo detailInfo = accidentService.detailInfo(vId,aId);
        VehicleInfo vehicleInfo = accidentService.vehicleInfo(vId);
        model.addAttribute("vehicle",vehicleInfo);
        model.addAttribute("detailInfo",detailInfo);
        model.addAttribute("lng",detailInfo.getLng());
        model.addAttribute("lat",detailInfo.getLat());
        model.addAttribute("address",detailInfo.getAddress());
        System.out.println(detailInfo.isState());
        return "accidentPage/detailsPage";
    }

    /**
     * 搜寻事故信息
     * @param request
     * @param model
     * @return
     */
    @ApiOperation("搜索事故信息")
    @RequestMapping("/accident/totalSearch")
    public String search(HttpServletRequest request,Model model){
        String search = request.getParameter("search");
        if (search == null || search == ""){
            log.warning("搜索输入为空");
            return "redirect:/ts/accident/warning";
        }
        List<TotalInfo> totalInfos = accidentService.search(search);
        if (totalInfos.size() != 0){
            if (!totalInfos.get(0).isStatus()){
                model.addAttribute("flag",true);
            }else {
                model.addAttribute("flag",false);
            }
        }
        model.addAttribute("infos",totalInfos);
        return "accidentPage/warning";
    }


    /******************************************管理员信息****************************/

    @Autowired
    AdminServiceImpl adminService;

    @RequestMapping("/accident/showLogin")
    public String showLogin(){
        return "adminPage/signIn";
    }

    @RequestMapping("/accident/showRegister")
    public String showRegister(){
        return "adminPage/register";
    }

    @ApiOperation("登录")
    @RequestMapping("/accident/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response){
        String uId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        boolean login = adminService.login(uId, pwd);
        if (!login){
            model.addAttribute("loginError","用户名或密码输入错误");
            return "adminPage/signIn";
        }
        CookieSessionUtils.cookieAndSession(request,response,uId);
        HttpSession session = request.getSession();
        session.setAttribute("login",adminService.loginName(uId));
        session.setAttribute("loginAdmin",uId);
        return "redirect:/ts/accident/warning";
    }

    @ApiOperation("注册")
    @RequestMapping("/accident/register")
    public String register(Model model,HttpServletRequest request){
        Admin admin = new Admin();
        String uId = request.getParameter("uId");
        String uName = request.getParameter("uName");
        String uPhone = request.getParameter("uPhone");
        String pwd = request.getParameter("pwd");

        admin.setUId(uId);
        admin.setUName(uName);
        admin.setUPwd(pwd);
        int register = adminService.register(admin);
        if (register == 1){
            model.addAttribute("registerError","输入不能为空");
            return "adminPage/register";
        }
        if (register == 2){
            model.addAttribute("registerError","用户名已存在");
            return "adminPage/register";
        }
        Admin admin1 = adminService.getAdmin(uId);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setUId(admin1.getId());
        adminInfo.setUPhone(uPhone);
        adminService.insertAdminInfo(adminInfo);
        return "redirect:/ts/accident/showLogin";
    }

    @ApiOperation("注销账号")
    @RequestMapping("/accident/logOut")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        log.info("正在登出...");
        CookieSessionUtils.deleteCookiesAndSession(request,response);
        return "redirect:/ts/accident/showLogin";
    }

    /**
     *个人中心
     * @return
     */
    @ApiOperation("个人中心")
    @RequestMapping("/accident/personal")
    public String showPerson(HttpServletRequest request,Model model){
        String uIds = (String) request.getSession().getAttribute("loginAdmin");
        Admin admin = adminService.getAdmin(uIds);
        if (admin == null){
            log.warning("未登录，请重新登录！");
            return "redirect:/ts/accident/showLogin";
        }
        AdminAll adminInfo = adminService.getAdminInfo(admin.getId());
        model.addAttribute("adminAll",adminInfo);
        return "adminPage/personalCenter";
    }

    /**
     * 基本信息修改
     * @param request
     * @return
     */
    @ApiOperation("管理员基本信息修改")
    @RequestMapping("/accident/personalUpdate")
    public String updateInfo(HttpServletRequest request){
        String uName = request.getParameter("uName");
        String uIds = request.getParameter("uIds");
        String uPhone = request.getParameter("uPhone");
        String uEmail = request.getParameter("uEmail");
        String uAddress = request.getParameter("uAddress");
        String uBirth = request.getParameter("uBirth");
        String uDate = request.getParameter("uDate");
        String id = request.getParameter("id");
        String uId = request.getParameter("uId");
        AdminAll adminAll = new AdminAll(uIds, uName, Integer.valueOf(id), uPhone, uEmail, uAddress, uBirth, uDate, Integer.valueOf(uId));
        boolean b = adminService.updateAdminInfo(adminAll);
        if (!b){
            return "redirect:/ts/accident/personal";
        }
        return "redirect:/ts/accident/personal";
    }

    @ApiOperation("管理员设置新密码")
    @RequestMapping("/accident/newPwd")
    public String updatePwd(HttpServletRequest request){
        String uId = request.getParameter("uId");
        String oldPwd = request.getParameter("uOldPwd");
        String pwdNew = request.getParameter("uNewPwdPre");
        boolean b = adminService.updatePwd(Integer.valueOf(uId), pwdNew, oldPwd);
        if (!b){
            return "redirect:/ts/accident/personal";
        }
        return "redirect:/ts/accident/personal";
    }

    @RequestMapping("/accident/showFindPre")
    public String findPwdPre(){
        return "adminPage/findPwd";
    }

    /**
     * 发送验证码
     * @param phone
     * @param uId
     */
    @ApiOperation("密码找回发送验证码")
    @RequestMapping(value = "/accident/sendPhone",method = RequestMethod.GET)
    @ResponseBody
    public void add(@PathParam("phone") String phone, @PathParam("uId") String uId){
        if (phone == null){
            log.warning("电话号码输入为空！");
            return;
        }
        if (uId == null) {
            log.warning("uId输入为空");
            return;
        }
        //查询是否存在用户
        Admin admin = adminService.getAdmin(uId);
        if (admin == null){
            log.warning("密码找回，用户不存在");
            return;
        }
        AdminAll adminInfo = adminService.getAdminInfo(admin.getId());
        if (!adminInfo.getUPhone().equals(phone)){
            log.warning("输入电话未绑定");
            return;
        }
        //存在用户，修改密码，发送短信
        String newPwd = RandomUtils.getRandom();
        boolean b = adminService.updatePwdFind(uId, newPwd);
        if (!b){
            log.warning("找回密码，修改密码失败");
            return;
        }
        String[] phoneNum = {phone};
        String[] params = {newPwd};
        PhoneUtils.sendPhoneMail(phoneNum,params);
    }

    /**
     * 根据验证码登录
     * @param request
     * @param model
     * @return
     */
    @ApiOperation("验证码登录")
    @RequestMapping("/accident/findPwd")
    public String findPwd(HttpServletRequest request,Model model,HttpServletResponse response){
        String uId = request.getParameter("uId");
        String toAddress = request.getParameter("toPwd");

        boolean login = adminService.login(uId, toAddress);
        if (!login){
            log.warning("验证码错误");
            model.addAttribute("msg","验证码错误");
            return "adminPage/findPwd";
        }
        CookieSessionUtils.cookieAndSession(request,response,uId);
        HttpSession session = request.getSession();
        session.setAttribute("login",adminService.loginName(uId));
        session.setAttribute("loginAdmin",uId);
        return "redirect:/ts/accident/warning";
    }

    /******************************************车辆信息****************************/

    @Autowired
    VehicleServiceImpl vehicleService;

    /**
     * 展示所有车辆信息
     * @param model
     * @return
     */
    @ApiOperation("车辆信息统计")
    @RequestMapping("/accident/vehicles")
    public String vehicles(Model model,@PathParam("pageNum") Integer pageNum){
        Map<Integer, List<VehicleInfo>> serviceAll = vehicleService.getAll(pageNum);
        Integer count = null;
        List<VehicleInfo> vehicles = null;
        for (Map.Entry entry : serviceAll.entrySet()) {
            count = (Integer) entry.getKey();
            vehicles = (List<VehicleInfo>) entry.getValue();
        }
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("pageNum",pageNum);
        if ((count % 10) == 0){
            count = count / 10;
        }else {
            count = count / 10 +1;
        }
        if (count >= pageNum + 1){
            model.addAttribute("next1",true);
        }
        if (count >= pageNum + 2){
            model.addAttribute("next2",true);
        }
        return "vehiclePage/vehicleInfo";
    }

    /**
     * 修改车辆信息
     * @param request
     * @return
     */
    @ApiOperation("修改车辆信息")
    @RequestMapping("/accident/updateVehicle")
    public String updateVehicle(HttpServletRequest request){
        String id = request.getParameter("id");
        String vNum = request.getParameter("vNum");
        String vName = request.getParameter("vName");
        String vType = request.getParameter("vType");
        VehicleInfo vehicleInfo = new VehicleInfo(Integer.valueOf(id), vName, vNum, vType);
        boolean b = vehicleService.updateVehicle(vehicleInfo);
        if (!b){
            log.warning(vNum + "信息更新失败");
            return "redirect:/ts/accident/vehicles?pageNum=1";
        }
        log.info(vNum + "信息更新成功");
        return "redirect:/ts/accident/vehicles?pageNum=1";
    }

    /**
     * 删除车辆信息
     * @param request
     * @return
     */
    @ApiOperation("删除车辆信息")
    @RequestMapping("/accident/deleteVehicle")
    public String deleteVehicle(HttpServletRequest request){
        String id = request.getParameter("id");
        String vNum = request.getParameter("vNum");
        boolean b = vehicleService.deleteVehicle(Integer.valueOf(id));
        if (!b){
            log.warning(vNum + "车辆信息删除失败");
            return "redirect:/ts/accident/vehicles";
        }
        log.warning(vNum + "车辆信息删除成功");
        return "redirect:/ts/accident/vehicles?pageNum=1";
    }

    @ApiOperation("添加车辆信息")
    @RequestMapping("/accident/insertVehicle")
    public String insertVehicle(HttpServletRequest request){
        String vName = request.getParameter("vName");
        String vNum = request.getParameter("vNum");
        String vType = request.getParameter("vType");
        boolean b = vehicleService.insertVehicle(new VehicleInfo(1, vName, vNum, vType));
        if (!b){
            log.warning(vNum + "车辆信息添加失败");
            return "redirect:/ts/accident/vehicles";
        }
        log.warning(vNum + "车辆信息添加成功");
        return "redirect:/ts/accident/vehicles?pageNum=1";
    }

    @ApiOperation("搜索车辆信息")
    @RequestMapping("/accident/searchVehicle")
    public String searchvehicle(HttpServletRequest request,Model model){
        String search = request.getParameter("search");
        if (search == null || search == ""){
            return "redirect:/ts/accident/vehicles";
        }
        List<VehicleInfo> vehicles = vehicleService.search(search);
        if (vehicles.size() <= 0){
            log.warning("搜索车辆信息为空");
            return "redirect:/ts/accident/vehicles";
        }
        model.addAttribute("vehicles",vehicles);
        return "vehiclePage/vehicleInfo";
    }
}
