package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.entity.User;
import cn.whu.wy.osgov.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务逻辑层
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 18:12
 */
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        int id = userRepository.add(user);
        User find = userRepository.findById(id);

        log.info("注册成功，后面可以执行一些其他的业务逻辑，比如调用其他服务，给注册用户发激活短信之类的。这也是service层的意义");

        return find;
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Integer count() {
        return userRepository.count();
    }

    public List<User> get(int pageSize, int page) {
        return userRepository.get(pageSize, page);
    }

    public int delete(int id) {
        return userRepository.delete(id);
    }

    public User update(User user) {
        return userRepository.update(user);
    }


}
