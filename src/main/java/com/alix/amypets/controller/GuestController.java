package com.alix.amypets.controller;

import com.alix.amypets.bean.Condition;
import com.alix.amypets.bean.zone.Guest;
import com.alix.amypets.service.GuestService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public JsonResult listByGid(Condition condition, HttpServletRequest request) {
        List<Guest> guests = guestService.listByGid(condition, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,guests);
    }

    @PostMapping
    public JsonResult insert(Guest guest,HttpServletRequest request) {
        guestService.insert(guest, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteById(@PathVariable Integer id,HttpServletRequest request) {
        guestService.deleteById(id, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }
}
