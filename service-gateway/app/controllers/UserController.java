package controllers;



import com.encentral.ems.user.api.IUser;
import com.encentral.ems.user.model.User;
import com.encentral.scaffold.commons.util.MyObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import javax.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Optional;


@Transactional
@Api(value="User")
public class UserController extends Controller {

    @Inject
    IUser iUser;

    @Inject
    FormFactory formFactory;

    @Inject
    MyObjectMapper objectMapper;

    @ApiOperation("Create new user")
    @ApiResponses(
            value={
                    @ApiResponse(code=200,response = String.class, message ="User added successfully")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name="body",
                    value="User Details",
                    paramType="body",
                    required=true,
                    dataType = "com.encentral.ems.user.model.User"
            )
    })
    public Result addUser() throws JsonProcessingException {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            return badRequest(userForm.errorsAsJson());
        }

        Optional<String> userPassword = iUser.addUser(userForm.get());

       if (userPassword.isEmpty()){
            return badRequest("An account with that email address already exists");
        }

        return ok(objectMapper.writeValueAsString(userPassword));
    }
    @ApiOperation("Retrieve all users")
    @ApiResponses(
            value={
                    @ApiResponse(code=200,response = User.class, message ="Users successfully retrieved")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
            )
    })
    public Result getAllUsers(String token) throws JsonProcessingException{
        if (iUser.isAdmin(token)){
            List<User> userList = iUser.getAllUsers();
            return ok(objectMapper.toJSONString(userList));
        }
        else{
            return badRequest("That operation is not allowed");
        }
    }

    @ApiOperation("Update User")
    @ApiResponses(
            value={
                    @ApiResponse(code=201,response = Boolean.class, message ="User successfully updated")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name="body",
                    value="User Details",
                    paramType="body",
                    required=true,
                    dataType = "com.encentral.ems.user.model.User"
            )
    })
    public Result updateUser() throws JsonProcessingException{
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            return badRequest(userForm.errorsAsJson());
        }
        User u =userForm.get();
        try {
            boolean ans = iUser.updateUser(u.getUserToken(),u);
            return ok(objectMapper.writeValueAsString(ans));
        }
        catch(Exception e) {
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Get User by Id")
    @ApiResponses(
            value={
                    @ApiResponse(code=200,response = User.class, message ="User successfully retrieved")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
            )
    })
    public Result getUserById(String userId) throws JsonProcessingException {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            return badRequest(userForm.errorsAsJson());
        }

        return ok(objectMapper.writeValueAsString(iUser.getUser(userId)));
    }

    @ApiOperation("Delete User")
    @ApiResponses(
            value={
                    @ApiResponse(code=201,response = Boolean.class, message ="User Deleted")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
            )
    })
    public Result deleteUser(String userToken, String userId) throws JsonProcessingException {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if (!iUser.isAdmin(userToken)){
            return badRequest("Permission not granted");
        }

        if(userForm.hasErrors()){
            return badRequest(userForm.errorsAsJson());
        }

        User u =userForm.get();
        try {
            boolean ans = iUser.deleteUser(u.getUserId());
            return ok(objectMapper.writeValueAsString(ans));
        }
        catch(Exception e) {
            return badRequest(e.getMessage());
        }

    }

    @ApiOperation("Sign user in")
    @ApiResponses(
            value={
                    @ApiResponse(code=200,response = String.class, message ="User logged in successfully")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name="body",
                    value="User Details",
                    paramType="body",
                    required=true,
                    dataType = "com.encentral.ems.user.model.User"
            )
    })
    public Result signUserIn() throws JsonProcessingException {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            return badRequest(userForm.errorsAsJson());
        }

        Optional<String> userToken = iUser.signUserIn(userForm.get());

        if (userToken.isEmpty()){
            return badRequest("Invalid login details");
        }

        return ok(objectMapper.writeValueAsString(userToken));
    }


}
