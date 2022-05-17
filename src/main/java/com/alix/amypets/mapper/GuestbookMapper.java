package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Guestbook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestbookMapper extends BaseMapper<Guestbook> {

    /**
     * 统计留言总数 通过留言板id
     * @param gid 留言板id
     * @return 该留言板留言总数
     */
    Integer countByGid(Integer gid);

}
