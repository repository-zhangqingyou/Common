package com.zqy.mange.zhuan.mvc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zqy.mange.zhuan.utils.LogUtil;
import com.zqy.mange.zhuan.utils.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/2 1:15
 * 描述:
 */
@Service
public abstract class BaseController<M, B extends BaseMapper<M>, S extends IService<M>> {
    @Autowired
    public LogUtil logs;
    @Autowired
    public JsonUtils jsonUtils;

    public abstract S getService();

    public abstract B getBaseMapper();


}
