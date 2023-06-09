package com.sxia.springbootcache.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxia.mapper.UserMapper;
import com.sxia.model.User;
import com.sxia.model.UserExample;
import io.github.ms100.cacheasmulti.cache.annotation.CacheAsMulti;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Caching(
            evict = {@CacheEvict(
                    condition = "#user!=null",
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true)
            },
            put = @CachePut(key = "#user.id",
                            condition = "#user!=null",
                            unless = "#result==null",
                            cacheNames = "user")
    )
    public User add (User user) {
        userMapper.insert(user);
        return userMapper.selectByPrimaryKey(user.getId());
    }

    @Caching(
            evict = {@CacheEvict(
                    condition = "#user!=null",
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true),

                    @CacheEvict(key = "#user.id",
                            condition = "#user!=null",
                            cacheNames = "user")
            }
    )
    public void deleteById (User user) {
        userMapper.deleteByPrimaryKey(user.getId());
    }


    /**
     * @CachePut 一般更新用
     */
//    @CachePut(cacheNames = "user", key = "#user.id")

    @Caching(
            evict = {@CacheEvict(
                            condition = "#user!=null",
                            cacheResolver = "dynamicPageCacheNames",
                            allEntries = true),

            },
            put = @CachePut(key = "#user.id",
                            condition = "#user!=null",
                            unless = "#result==null",
                            cacheNames = "user")
    )
    public User updateById(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(user.getId());
    }

    @Caching(
            evict = {@CacheEvict(
                    cacheResolver = "dynamicPageCacheNames",
                    allEntries = true),

            },
            put = @CachePut(key = "#userIds",
                    cacheNames = "user")
    )
    public List<User> updateByCondition(@CacheAsMulti @RequestBody List<Integer> userIds) {
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(userIds);
        User user = new User();
        user.setStatus(1);
        userMapper.updateByExampleSelective(user, example);
        return userMapper.selectByExample(example);
    }

    @Cacheable(cacheNames = "user", key = "#id")
    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Cacheable(
//            key = "#user.name+':'+#pageable.pageSize+'_'+#pageable.pageNumber",
            cacheResolver = "dynamicPageCacheNames",
            condition = "#user!=null && #pageable!=null",
            unless = "#result==null",
            cacheNames = "user"
    )
    public PageInfo<User> selectByPage(User user, Pageable pageable){
        UserExample example = new UserExample();
        example.createCriteria().andNameLike(user.getName());
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<User> list = userMapper.selectByExample(example);
        PageInfo<User> result = new PageInfo<>(list,5);
        return result;
    }

//    //    @CacheEvict 注解的方法在被调用时，
////    会从缓存中移除已存储的数据。
////    @CacheEvict 注解一般用于删除缓存数据，相当于缓存使用的是写模式中的失效模式。
//    @CacheEvict(value = "myCache", key = "#id")
//    public void deleteEntityById(Integer id) {
//        userMapper.deleteByPrimaryKey(id);
//    }
//    /**
//     * @Caching 注解用于在一个方法或者类上，同时指定多个 Spring Cache 相关的注解。
//      */
//    @Caching(evict = {
//            @CacheEvict(value = "myCache", key = "#entity.id"),
//            @CacheEvict(value = "otherCache", key = "#entity.id")
//    })
//    public void saveEntity(User entity) {
//        userMapper.insert(entity);
//    }
//
//    /**例子3：当调用saveData方法时，Spring会根据@CacheEvict注解先从otherCache缓存中移除数据。
//    然后，Spring 将执行该方法并将结果保存到数据库或外部 API。
//    方法执行后，Spring 会根据@CachePut注解将结果添加到 myCache、myOtherCache 和 myThirdCache 缓存中。
//    Spring 还将根据@Cacheable注解检查结果是否已缓存在 myFourthCache 和 myFifthCache 缓存中。
//    如果结果尚未缓存，Spring 会将结果缓存在适当的缓存中。如果结果已经被缓存，Spring 将返回缓存的结果，而不是再次执行该方法。*/
//    @Caching(
//            put = {
//                    @CachePut(value = "myCache", key = "#result.id"),
//                    @CachePut(value = "myOtherCache", key = "#result.id"),
//                    @CachePut(value = "myThirdCache", key = "#result.name")
//            },
//            evict = {
//                    @CacheEvict(value = "otherCache", key = "#id")
//            },
//            cacheable = {
//                    @Cacheable(value = "myFourthCache", key = "#id"),
//                    @Cacheable(value = "myFifthCache", key = "#result.id")
//            }
//    )
//    public User saveData(Integer id, String name) {
//        // Code to save data to a database or external API
//        User entity = new User(id, name);
//        userMapper.insert(entity);

//        return entity;

//    }
}