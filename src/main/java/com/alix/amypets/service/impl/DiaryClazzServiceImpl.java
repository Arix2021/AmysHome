package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.mapper.DiaryClazzMapper;
import com.alix.amypets.service.DiaryClazzService;
import com.alix.amypets.service.ex.base.InsertException;
import com.alix.amypets.service.ex.base.UpdateException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.service.ex.user.UserIdentityException;
import com.alix.amypets.uitls.MpUtil;
import com.alix.amypets.uitls.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryClazzServiceImpl implements DiaryClazzService {

    @Autowired
    private DiaryClazzMapper diaryClazzMapper;

    private final MpUtil<DiaryClazz> mpUtil = new MpUtil<>();

    @Override
    public List<DiaryClazz> getAll(Integer uid, User user) {
        UserUtil.idCheck(uid,user.getId());
        return diaryClazzMapper.selectList(mpUtil.queryByUid(uid));
    }

    @Override
    public DiaryClazz getByCid(Integer cid) {
        return diaryClazzMapper.selectById(cid);
    }

    @Override
    public List<DiaryClazz> getClazzAndNum(Integer uid, User user) {
        UserUtil.idCheck(uid,user.getId());
        List<DiaryClazz> res = null;
        if ((res = diaryClazzMapper.getClazzAndNum(uid)) == null)
            throw new ZoneException("获取分类统计失败");
        return res;
    }

    @Override
    public Page<DiaryClazz> getPage(Pageable pageable) {
//        diaryClazzMapper.selectPage()
        return null;
    }

    @Override
    public void insert(DiaryClazz clazz,Integer uid) {
        UserUtil.idCheck(clazz.getUid(),uid); // 用户身份校验
        if (!clazz.getUid().equals(uid))
            throw new UserIdentityException("用户id与请求不一致");
        if (diaryClazzMapper.selectOne(mpUtil.queryWrapper("clazz",clazz.getClazz())) != null)
            throw new InsertException("重复添加相同分类");
        if (diaryClazzMapper.insert(clazz) < 1)
            throw new InsertException("添加文章分类时出现异常");
    }

    @Override
    public void update(DiaryClazz clazz,Integer uid) {
        UserUtil.idCheck(clazz.getUid(),uid); // 用户身份校验
        DiaryClazz res = null;
        if ((res = diaryClazzMapper.selectById(clazz.getCid())) == null)
            throw new UpdateException("更新文章分类时核查出错");
        if (res.getClazz().equals(clazz.getClazz()))
            throw new UpdateException("未对分类作出修改");
        if (diaryClazzMapper.updateById(clazz) < 1)
            throw new UpdateException("更新文章分类出错");
    }

    @Override
    public void delete(Integer cid,Integer uid) {
        System.out.println("DELETE:cid=" + cid + ",uid=" + uid);
        UserUtil.idCheck(diaryClazzMapper.selectById(cid).getUid(),uid);
        if (diaryClazzMapper.deleteById(cid) < 1)
            throw new UpdateException("删除文章分类出错");
    }
}
