package org.navya.oms.omsadminservices.controller;

import java.util.Collections;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.navya.oms.omsadminservices.model.ApiResponseModel;
import org.navya.oms.omsadminservices.model.UserModel;
import org.navya.oms.omsadminservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping
  public ApiResponseModel<UserModel> getAllUsers() {
    log.info("UserController.getAllUsers enter");
    return ApiResponseModel.of(userService.getAllUsers(), null);
  }

  @PostMapping
  public ApiResponseModel<UserModel> addNewUser(@RequestBody @NotNull @Valid UserModel userModel) {
    log.info("UserController.addNewUser called with userModel: {}", userModel);
    UserModel newUser = userService.addNewUser(userModel);
    log.info("UserController.addNewUser returning with new userModel: {}", newUser);
    return ApiResponseModel.of(Collections.singletonList(newUser), null);
  }

  @PutMapping
  public ApiResponseModel<UserModel> updateUser(@RequestBody @NotNull @Valid UserModel userModel) {
    log.info("UserController.updateUser called with userModel: {}", userModel);
    UserModel newUser = userService.updateUser(userModel);
    log.info("UserController.updateUser returning with new userModel: {}", newUser);
    return ApiResponseModel.of(Collections.singletonList(newUser), null);
  }

  @GetMapping(value = "/{userId}")
  public ApiResponseModel<UserModel> getUserById(@PathVariable String userId) {
    log.info("UserController.getUserById enter");
    return ApiResponseModel.of(Collections.singletonList(userService.getUserById(userId)), null);
  }

  @DeleteMapping(value = "/{userId}")
  public ApiResponseModel<?> deleteUser(@PathVariable String userId) {
    log.info("UserController.deleteUser called with userId: {}", userId);
    userService.deleteById(userId);
    log.info(
        "UserController.deleteUser returning after soft deleting user with userId: {}", userId);
    return ApiResponseModel.of(null, null);
  }
}
