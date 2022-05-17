package com.alix.amypets.controller;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.service.ZoneService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping
    public JsonResult pullZone(Integer uid){
        return new JsonResult(JsonResult.STATE_SUCCESS,zoneService.getZoneByUid(uid));
    }

    @PutMapping
    public JsonResult changeInfo(Zone zone, HttpServletRequest request) {
        User user = UserUtil.getUserByRequest(request);
        return new JsonResult(JsonResult.STATE_SUCCESS,zoneService.updateZone(zone, user.getId()));
    }

}
