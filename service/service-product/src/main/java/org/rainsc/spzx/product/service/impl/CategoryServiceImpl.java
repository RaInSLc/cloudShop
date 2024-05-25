package org.rainsc.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import org.rainsc.spzx.model.entity.product.Category;
import org.rainsc.spzx.product.mapper.CategoryMapper;
import org.rainsc.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * @return
     */
    @Override
    public List<Category> findOneCategory() {
        // 查询Redis，检查是否存在一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:firstC");
        // 如果Redis中存在一级分类，则直接返回
        if (StringUtils.hasText(categoryOneJson)) {
            // 将Redis中的JSON转换为List<Category>并返回
            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existCategoryList;
        }

        // 如果Redis中不存在一级分类，则从数据库查询，并将结果缓存到Redis中
        List<Category> categoryList = categoryMapper.findOneCategory();
        // 将查询结果转换为JSON字符串
        String jsonString = JSON.toJSONString(categoryList);
        // 将结果存入Redis，设置缓存有效期为7天
        redisTemplate.opsForValue().set("category:firstC", jsonString, 7, TimeUnit.DAYS);

//        return categoryMapper.findOneCategory();
        // 返回redis缓存中的内容
        return categoryList;
    }

    /**
     * @return 树形结构 构造分类的层级 按层级返回分类
     */
    @Cacheable(value = "category", key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        // 查询所有分类
        // 获取所有分类信息
        List<Category> allCategoryList = categoryMapper.findAll();

        // 遍历总分类List 过滤出一级分类列表
        List<Category> firstCategoryList = allCategoryList.stream()
                // 过滤出parentId为0的分类，即一级分类
                .filter(item -> item.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        // 遍历一级分类，为每个一级分类添加二级分类
        firstCategoryList.forEach(firstCategory -> {
            List<Category> secondCategoryList = allCategoryList.stream()
                    // 过滤出parentId等于当前一级分类id的分类，即二级分类
                    .filter(item -> item.getParentId() == firstCategory.getId())
                    .collect(Collectors.toList());
            // 将二级分类列表封装到一级分类中
            firstCategory.setChildren(secondCategoryList);

            // 遍历二级分类，为每个二级分类添加三级分类
            secondCategoryList.forEach(secondCategory -> {
                List<Category> thirdCategoryList = allCategoryList.stream()
                        // 过滤出parentId等于当前二级分类id的分类，即三级分类
                        .filter(item -> item.getParentId() == secondCategory.getId())
                        .collect(Collectors.toList());
                // 将三级分类列表封装到二级分类中
                secondCategory.setChildren(thirdCategoryList);
            });
        });
        // 返回包含完整分类树的一级分类列表
        return firstCategoryList;
    }
}
