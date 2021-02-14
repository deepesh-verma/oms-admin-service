package org.navya.oms.omsadminservices.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.navya.oms.omsadminservices.entity.UserEntity;
import org.navya.oms.omsadminservices.exception.ServiceException;
import org.navya.oms.omsadminservices.model.UserModel;
import org.navya.oms.omsadminservices.repository.UserRepository;
import org.navya.oms.omsadminservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserModel addNewUser(UserModel userModel) {
    log.info("UserServiceImpl.addNewUSer called with userModel: {}", userModel);

    UUID userId = userModel.getUserId();
    if (null != userId) {
      throw new ServiceException("Add new user called with existing userId: " + userId);
    }

    userModel.setCreateTimestamp(LocalDateTime.now());

    UserEntity newUserEntity = userRepository.save(new UserEntity(userModel));
    return new UserModel(newUserEntity);
  }

  @Override
  public List<UserModel> getAllUsers() {
    log.info("UserServiceImpl.getAllUsers called");

    return userRepository.findAll().stream().map(UserModel::new).collect(Collectors.toList());
  }

  @Override
  public UserModel findByUserId(String userId) {
    log.info("UserServiceImpl.findByUserId called for userId: {}", userId);

    return userRepository.findById(UUID.fromString(userId)).map(UserModel::new).orElse(null);
  }

  @Override
  public UserModel updateUser(UserModel userModel) {
    log.info("UserServiceImpl.updateUser called with userModel: {}", userModel);

    return new UserModel(userRepository.save(new UserEntity(userModel)));
  }

  @Override
  public void deleteById(String userId) {
    log.info("UserServiceImpl.deleteById called with userId: {}", userId);

    userRepository.deleteById(UUID.fromString(userId));
    log.info("UserServiceImpl.deleteById exit after soft deleting user with userId: {}", userId);
  }
}
