package org.navya.oms.omsadminservices.service;

import java.util.List;
import org.navya.oms.omsadminservices.model.UserModel;

public interface UserService {

  UserModel addNewUser(UserModel userModel);

  List<UserModel> getAllUsers();

  UserModel findByUserId(String userId);

  UserModel updateUser(UserModel userModel);

  void deleteById(String userId);

  UserModel getUserById(String userId);
}
