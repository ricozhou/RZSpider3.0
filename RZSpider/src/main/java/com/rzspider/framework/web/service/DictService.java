package com.rzspider.framework.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rzspider.project.system.dict.domain.DictData;
import com.rzspider.project.system.dict.service.IDictDataService;

/**
 * RZSpider首创 html调用 thymeleaf 实现字典读取
 * 
 * @author ricozhou
 */
@Component
public class DictService
{
    @Autowired
    private IDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     * 
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<DictData> selectDictData(String dictType)
    {
        return dictDataService.selectDictDataByType(dictType);
    }
}
