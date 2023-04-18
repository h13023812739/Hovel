package org.hyq.hovel.util;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    /**
     * List 转 Page 对象
     * @param list
     * @param pageable 分页参数
     * @param <T>
     * @return
     */
    public static <T> Page<T> convertList2PageVO(final List<T> list,final Pageable pageable) {
        if (CollectionUtils.isEmpty(list)) {
            return new PageImpl<>(new ArrayList<>(0), pageable, 0);
        }
        final List<T> ingredientVOS = list;
        final List<List<T>> partition = Lists.partition(list, pageable.getPageSize());
        List<T> pageContent = partition.get(pageable.getPageNumber());
        return new PageImpl<>(pageContent, pageable, ingredientVOS.size());
    }
}
